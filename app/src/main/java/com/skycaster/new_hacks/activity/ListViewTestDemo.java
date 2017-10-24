package com.skycaster.new_hacks.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.adapter.SimpleAdapter;
import com.skycaster.new_hacks.customized.OverScrollListView;

import java.util.ArrayList;

public class ListViewTestDemo extends AppCompatActivity {
    private OverScrollListView mListView;
    private ArrayList<Integer> mList=new ArrayList<>();
    private SimpleAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, ListViewTestDemo.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_test_demo);
        mListView= (OverScrollListView) findViewById(R.id.activity_list_view_demo_list_view);
        for(int i=0;i<50;i++){
            mList.add(i+1);
        }
        mAdapter=new SimpleAdapter(mList,this);
        mListView.setAdapter(mAdapter);


    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
