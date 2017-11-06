package com.skycaster.new_hacks.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.adapter.SnapShotAdapter;
import com.skycaster.new_hacks.customized.SketchPadView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrawingCacheDemo extends AppCompatActivity {

    @BindView(R.id.activity_drawing_cache_demo_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_drawing_cache_demo_sketch_view)
    SketchPadView mSketchView;
    private SnapShotAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_cache_demo);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter=new SnapShotAdapter(this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void clear(View view) {
        mSketchView.clear();
    }

    public void snap(View view) {
        Bitmap snapShot = mSketchView.getSnapShot();
        mAdapter.addSnapShot(snapShot);
        mRecyclerView.smoothScrollToPosition(Integer.MAX_VALUE);
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
