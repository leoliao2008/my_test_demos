package com.skycaster.new_hacks.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.presenter.ThreadPoolDemoPresenter;

public class ThreadPoolDemo extends AppCompatActivity {
    private ThreadPoolDemoPresenter mPresenter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_demo);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mListView= (ListView) findViewById(R.id.thread_pool_demo_list_view);

    }

    private void initData() {
        mPresenter=new ThreadPoolDemoPresenter(this);
        mPresenter.initData();

    }

    private void initListener() {

    }

    public ListView getListView() {
        return mListView;
    }
}
