package com.skycaster.new_hacks.presenter;

import android.widget.ListView;

import com.skycaster.new_hacks.activity.ThreadPoolDemo;
import com.skycaster.new_hacks.adapter.DownLoadListAdapter;
import com.skycaster.new_hacks.bean.DownLoadBean;
import com.skycaster.new_hacks.manager.DownLoadTasksManager;

import java.util.ArrayList;

/**
 * Created by 廖华凯 on 2017/8/29.
 */

public class ThreadPoolDemoPresenter {
    private ThreadPoolDemo mActivity;
    private ArrayList<DownLoadBean> mList=new ArrayList<>();
    private ListView mListView;
    private DownLoadListAdapter mAdapter;

    public ThreadPoolDemoPresenter(ThreadPoolDemo activity) {
        mActivity = activity;
    }

    public void initData() {
        initListView();
    }

    private void initListView() {
        mListView=mActivity.getListView();
        for(int i=0;i<21;i++){
            mList.add(new DownLoadBean("SimulationItem_"+i));
        }
        mAdapter=new DownLoadListAdapter(mList,mActivity);
        mListView.setAdapter(mAdapter);
    }

    public void onStop(){
        if(mActivity.isFinishing()){
            DownLoadTasksManager.getInstance().cancelAll();
        }
    }
}
