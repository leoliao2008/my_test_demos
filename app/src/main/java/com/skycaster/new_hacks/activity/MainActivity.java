package com.skycaster.new_hacks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.skycaster.new_hacks.R;


/**
 * Created by 廖华凯 on 2017/8/28.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void IpcDemo(View view) {
        startActivity(new Intent(this,IpcTestActivity.class));
    }

    public void NotificationDemo(View view) {
        startActivity(new Intent(this,NotificationDemo.class));
    }

    public void ThreadPoolDemo(View view) {
        startActivity(new Intent(this,ThreadPoolDemo.class));
    }

    public void SNRChartViewDemo(View view) {
        startActivity(new Intent(this,SnrChartViewDemo.class));
    }

    public void ConfigChangeDemo(View view) {
        startActivity(new Intent(this,ConfigChangeDemo.class));
    }

    public void SpannableString(View view) {
        startActivity(new Intent(this,SpannableTextDemo.class));
    }

    public void DynamicLayout(View view) {
        startActivity(new Intent(this,DynamicLayoutActivity.class));
    }

    public void VectorDemo(View view) {
        startActivity(new Intent(this,VectorDrawableDemo.class));
    }

    public void NewAnimation(View view) {
        startActivity(new Intent(this,NewAnimationDemo.class));
    }

    public void toZoomableImageView(View view) {
        ZmbleImageViewDemo.start(this);
    }

    public void toListViewDemo(View view) {
        ListViewTestDemo.start(this);
    }

    public void toMatrixTest(View view) {
        MatrixTestActivity.start(this);
    }

    public void VectorCompatDemo(View view) {
        VectorDrawableCompatDemo.start(this);
    }

    public void toStripper(View view) {
        StripperDemo.start(this);
    }

    public void toScratchViewDemo(View view) {
        ScratchViewDemo.start(this);
    }

    public void toColorMatrixDemo(View view) {
        ColorMatrixDemo_One.start(this);
    }

    public void toColorMatrixDemo_Two(View view) {
        ColorMatrixDemo_Two.start(this);
    }
}
