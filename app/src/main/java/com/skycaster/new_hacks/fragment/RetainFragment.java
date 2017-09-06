package com.skycaster.new_hacks.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.skycaster.new_hacks.R;

import java.util.ArrayList;

/**
 * Created by 廖华凯 on 2017/9/4.
 */

public class RetainFragment extends Fragment {
    private View rootView;
    private ListView mListView;
    private ToggleButton mToggleButton;
    private ArrayList<String> mList=new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private Handler mHandler;
    private Runnable mRunnableSendData=new Runnable() {
        @Override
        public void run() {
            showLog("mRunnableSendData");
            mList.add("This is a message.");
            mAdapter.notifyDataSetChanged();
            mListView.smoothScrollToPosition(Integer.MAX_VALUE);
            mHandler.postDelayed(this,1000);
        }
    };

    @Override
    public void onAttach(Context context) {
        showLog("onAttach");
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        showLog("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        showLog("onCreate");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        showLog("onCreateView");
        if(rootView==null){
            rootView=inflater.inflate(R.layout.fragment_retain,container,false);

            mHandler=new Handler(Looper.getMainLooper());
            mToggleButton= rootView.findViewById(R.id.retain_fragment_toggle_button_send);
            mListView=rootView.findViewById(R.id.retain_fragment_list_view);
            mAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,mList);
            mListView.setAdapter(mAdapter);
            mToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mToggleButton.isChecked()){
                        mHandler.post(mRunnableSendData);
                    }else {
                        mHandler.removeCallbacks(mRunnableSendData);
                    }
                }
            });
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        showLog("onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        showLog("onStart");
        super.onStart();
        if(mToggleButton!=null&&mToggleButton.isChecked()){
            mHandler.post(mRunnableSendData);
        }

    }

    @Override
    public void onResume() {
        showLog("onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        showLog("onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        showLog("onStop");
        super.onStop();
        mHandler.removeCallbacks(mRunnableSendData);
    }

    @Override
    public void onDetach() {
        showLog("onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        showLog("onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        showLog("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyOptionsMenu() {
        showLog("onDestroyOptionsMenu");
        super.onDestroyOptionsMenu();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_type_1,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg="null";
        switch (item.getItemId()){
            case R.id.menu_item_1:
                msg="menu_item_1";
                break;
            case R.id.menu_item_2:
                msg="menu_item_2";
                break;
            case R.id.menu_item_3:
                msg="menu_item_3";
                break;
            case R.id.menu_item_4:
                msg="menu_item_4";
                break;
            case R.id.menu_item_5:
                msg="menu_item_5";
                break;
        }
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
