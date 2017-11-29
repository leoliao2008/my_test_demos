package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HandlerTest extends AppCompatActivity {

    @BindView(R.id.btn_ten_handler)
    Button mBtnTenHandler;
    @BindView(R.id.btn_five_handler)
    Button mBtnFiveHandler;
    @BindView(R.id.btn_one_handler)
    Button mBtnOneHandler;
    @BindView(R.id.btn_no_handler)
    Button mBtnNoHandler;
    @BindView(R.id.tv_duration)
    TextView mTvDuration;
    private long timeStart;
    private long timeStop;
    private Runnable mRunnableCount=new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mResult = 0;
                    for (int i=0;i<1000000;i++){
                        mResult +=i;
                        i++;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long duration = System.currentTimeMillis() - timeStart;
                            mTvDuration.setText("计算结果："+mResult+"总耗时："+duration+" 毫秒。");
                        }
                    });
                }
            }).start();
        }
    };
    private int mResult;
    private Runnable mRunnablePost=new Runnable() {
        @Override
        public void run() {
            if(mPostCount>0){
                mPostCount--;
                mHandler.post(this);
            }else {
                mHandler.post(mRunnableCount);
            }

        }
    };
    private Handler mHandler;
    private int mPostCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);
        ButterKnife.bind(this);
        initData();
        initListeners();
    }

    private boolean isContinue;
    private Runnable mRunnableAutoCycle=new Runnable() {
        @Override
        public void run() {
            ToastUtil.toast(HandlerTest.this,"This is an auto cycling message.");
            if(isContinue){
                mHandler.postDelayed(this,50);
            }
        }
    };
    private void initData() {
        mHandler=new Handler();
        isContinue=true;
        mHandler.postDelayed(mRunnableAutoCycle,50);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isContinue=false;
        mHandler.removeCallbacks(mRunnableAutoCycle);
        ToastUtil.shutUp();
    }

    private void initListeners() {
        mBtnTenHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStart=System.currentTimeMillis();
                mPostCount=10;
                mHandler.post(mRunnablePost);
            }
        });

        mBtnFiveHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStart=System.currentTimeMillis();
                mPostCount=5;
                mHandler.post(mRunnablePost);
            }
        });

        mBtnOneHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStart=System.currentTimeMillis();
                mPostCount=1;
                mHandler.post(mRunnablePost);
            }
        });

        mBtnNoHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeStart=System.currentTimeMillis();
                mRunnableCount.run();
            }
        });


    }
}
