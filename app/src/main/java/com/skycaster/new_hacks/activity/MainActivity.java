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
}
