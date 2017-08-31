package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.presenter.IpcTestPresenter;

import java.util.concurrent.atomic.AtomicBoolean;

public class IpcTestActivity extends AppCompatActivity {

    private AtomicBoolean isConnected=new AtomicBoolean(false);
    private IpcTestPresenter mIpcTestPresenter;
    private TextView tv_console;
    private TextSwitcher tsw_textSwitcher;
    private ToggleButton mToggleButton_register;
    private ToggleButton mToggleButton_startDataTransmission;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showLog("onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_test);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        tv_console= (TextView) findViewById(R.id.main_tv_console);
        tsw_textSwitcher= (TextSwitcher) findViewById(R.id.main_text_switcher);
        mToggleButton_register= (ToggleButton) findViewById(R.id.main_toggle_register);
        mToggleButton_startDataTransmission= (ToggleButton) findViewById(R.id.main_toggle_start_transmission);

    }

    private void initData() {
        mIpcTestPresenter=new IpcTestPresenter(this);
        mIpcTestPresenter.initData();

    }


    private void initListener() {

        mToggleButton_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = mToggleButton_register.isChecked();
                if(isChecked){
                    mIpcTestPresenter.register();
                }else {
                    mIpcTestPresenter.unRegister();
                }
            }
        });


        mToggleButton_startDataTransmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = mToggleButton_startDataTransmission.isChecked();
                if(isChecked){
                    mIpcTestPresenter.startDataTransmission();
                }else {
                    mIpcTestPresenter.stopDataTransmission();
                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mIpcTestPresenter.onStart();
        showLog("onStart isConnected: "+isConnected.get());
    }

    @Override
    protected void onRestart() {
        showLog("onRestart");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        showLog("onStop");
        super.onStop();
        mIpcTestPresenter.onStop();
    }

    @Override
    protected void onResume() {
        showLog("onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        showLog("onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        showLog("onSaveInstanceState");
        super.onSaveInstanceState(outState);
        mIpcTestPresenter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        showLog("onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        mIpcTestPresenter.onRestoreInstanceState(savedInstanceState);
    }

    public void sayHelloWorld(View view) {
        mIpcTestPresenter.sayHelloWorld();
    }

    public void sayGoodBye(View view) {
        mIpcTestPresenter.sayGoodBye();
    }

    public void sayMan(View view) {
        mIpcTestPresenter.sayHeyMan();
    }

    public ToggleButton getToggleButton_register() {
        return mToggleButton_register;
    }

    public ToggleButton getToggleButton_startDataTransmission() {
        return mToggleButton_startDataTransmission;
    }

    public AtomicBoolean getIsConnected() {
        return isConnected;
    }

    public TextView getTv_console() {
        return tv_console;
    }

    public TextSwitcher getTsw_textSwitcher() {
        return tsw_textSwitcher;
    }

    public void showNotification(View view) {
        mIpcTestPresenter.showNotification();
    }

    public void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
