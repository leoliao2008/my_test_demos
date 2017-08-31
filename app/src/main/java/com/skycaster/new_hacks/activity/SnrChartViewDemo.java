package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.customized.SNRChartView;

import java.util.ArrayList;
import java.util.Random;

public class SnrChartViewDemo extends AppCompatActivity {
    private SNRChartView mChartView;
    private Handler mHandler;
    private ArrayList<Thread> mThreads=new ArrayList<>();
    private Runnable mRunnableUpdateChartView =new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mThreads.add(Thread.currentThread());
                    float snr = mRandom.nextFloat() * 50;
                    if(!mRandom.nextBoolean()){
                        snr=-1*snr;
                    }
                    mChartView.updateChartView(snr);
                    if(Thread.currentThread().isInterrupted()){
                        showLog("thread isInterrupted");
                       return;
                    }
                    mHandler.postDelayed(mRunnableUpdateChartView,100);
                }
            }).start();
        }
    };
    private Random mRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snr_chart_view_demo);
        mHandler=new Handler();
        mRandom=new Random();
        mChartView= (SNRChartView) findViewById(R.id.snr_demo_char_view);
    }

    public void startFlow(View view) {
        mHandler.post(mRunnableUpdateChartView);
    }

    public void stopFlow(View view) {
        mHandler.removeCallbacks(mRunnableUpdateChartView);
        for(int i=0,size=mThreads.size();i<size;i++){
            mThreads.get(i).interrupt();
        }
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
