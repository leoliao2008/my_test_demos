package com.skycaster.new_hacks.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.activity.IpcTestActivity;
import com.skycaster.new_hacks.data.StaticData;
import com.skycaster.new_hacks.service.IpcServer;

/**
 * Created by 廖华凯 on 2017/8/24.
 */

public class IpcTestPresenter {

    private IpcTestActivity mActivity;
    private ServiceConnection mServiceConnection;
    private Messenger mLocalMessenger;
    private Messenger mServiceMessenger;
    private TextSwitcher mTextSwitcher;
    private String IS_CONNECTED = "IS_CONNECTED";
    private String STATUS_DESCRIPTION = "STATUS_DESCRIPTION";
    private String IS_REGISTERED="IS_REGISTERED";
    private String IS_TRANSMITTING="IS_TRANSMITTING";
    private String TEXT_SWITCHER_CURRENT_MESSAGE = "TEXT_SWITCHER_CURRENT_MESSAGE";

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case StaticData.MESSAGE_REGISTER_CLIENT_SUCCESS:
                    showLog("MESSAGE_REGISTER_CLIENT_SUCCESS");
                    mActivity.getTv_console().setText("注册成功！");
                    break;
                case StaticData.MESSAGE_UNREGISTER_CLIENT_SUCCESS:
                    showLog("MESSAGE_UNREGISTER_CLIENT_SUCCESS");
                    mActivity.getTv_console().setText("已经取消注册！");
                    break;
                case StaticData.MESSAGE_DATA:
                    Bundle bundle = msg.getData();
                    String string = bundle.getString(StaticData.EXTRA_DATA, null);
                    if(TextUtils.isEmpty(string)){
                        string="null";
                    }
                    mTextSwitcher.setText(string);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    public IpcTestPresenter(IpcTestActivity activity) {
        mActivity = activity;
    }

    public void initData(){
        mTextSwitcher=mActivity.getTsw_textSwitcher();
        initTextSwitcher(mTextSwitcher);

        mLocalMessenger=new Messenger(mHandler);
        showLog("mLocalMessenger = "+mLocalMessenger.hashCode());

        mServiceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                showLog("onServiceConnected");
                mServiceMessenger=new Messenger(service);
                mActivity.getIsConnected().compareAndSet(false, true);

                //onConfigChange导致之前断开，这里必须自动重启服务
                if(mActivity.getToggleButton_register().isChecked()){
                    register();
                }
                if(mActivity.getToggleButton_startDataTransmission().isChecked()){
                    startDataTransmission();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mServiceMessenger=null;
                mActivity.getIsConnected().compareAndSet(true,false);

            }
        };

    }

    private void initTextSwitcher(final TextSwitcher textSwitcher) {
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView=new TextView(mActivity);
                textView.setLayoutParams(new TextSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setTextColor(Color.RED);
                textView.setTextSize(20);
                return textView;
            }
        });
        textSwitcher.setInAnimation(mActivity,R.anim.anim_text_switcher_in);
        textSwitcher.setOutAnimation(mActivity,R.anim.anim_text_switcher_out);
    }

    public void onStart(){
        Intent intent = mActivity.getIntent();
        mActivity.getIsConnected().set(intent.getBooleanExtra(IS_CONNECTED,false));
        mActivity.getToggleButton_register().setChecked(intent.getBooleanExtra(IS_REGISTERED,false));
        mActivity.getToggleButton_startDataTransmission().setChecked(intent.getBooleanExtra(IS_TRANSMITTING,false));
        String message = intent.getStringExtra(TEXT_SWITCHER_CURRENT_MESSAGE);
        if(!TextUtils.isEmpty(message)){
            mTextSwitcher.setText(message);
        }
        boolean isConnectionValid = mActivity.bindService(new Intent(mActivity, IpcServer.class), mServiceConnection, Context.BIND_AUTO_CREATE);
        if(!isConnectionValid){
            mActivity.unbindService(mServiceConnection);
        }
    }



    public void onStop(){
        if(mActivity.getIsConnected().get()){
            unRegister();
            mActivity.getIsConnected().set(false);
            mActivity.unbindService(mServiceConnection);
        }
    }

    public void onSaveInstanceState(Bundle bundle){
        bundle.putBoolean(IS_CONNECTED,mActivity.getIsConnected().get());
        bundle.putString(STATUS_DESCRIPTION,mActivity.getTv_console().getText().toString());
        Intent intent = mActivity.getIntent();
        TextView currentView= (TextView) mTextSwitcher.getChildAt(mTextSwitcher.getChildCount()-1);
        intent.putExtra(TEXT_SWITCHER_CURRENT_MESSAGE,currentView.getText().toString().trim());
        intent.putExtra(IS_CONNECTED,mActivity.getIsConnected().get());
        intent.putExtra(IS_REGISTERED,mActivity.getToggleButton_register().isChecked());
        intent.putExtra(IS_TRANSMITTING,mActivity.getToggleButton_startDataTransmission().isChecked());
        mActivity.setIntent(intent);
    }

    public void onRestoreInstanceState(Bundle bundle){
        boolean stateConnected = bundle.getBoolean(IS_CONNECTED, false);
        mActivity.getIsConnected().set(stateConnected);
        String stateDesc = bundle.getString(STATUS_DESCRIPTION, "null");
        mActivity.getTv_console().setText(stateDesc);
    }

    public void sayHelloWorld(){
        sendMessage(StaticData.MESSAGE_WHAT_SAY_HELLO,false);
    }

    public void sayGoodBye(){
        sendMessage(StaticData.MESSAGE_WHAT_SAY_GOODBYE,false);
    }

    public void sayHeyMan(){
        sendMessage(StaticData.MESSAGE_WHAT_SAY_HI,false);
    }

    public void register(){
        sendMessage(StaticData.MESSAGE_REQUEST_REGISTER_CLIENT,true);
    }

    public void unRegister(){
        sendMessage(StaticData.MESSAGE_REQUEST_UNREGISTER_CLIENT,true);
    }

    public void showNotification(){
        sendMessage(StaticData.MESSAGE_SHOW_NOTIFICATION,false);
    }

    public void startDataTransmission(){
        showLog("startDataTransmission");
        sendMessage(StaticData.MESSAGE_START_DATA_TRANSMISSION,false);
    }

    public void stopDataTransmission(){
        sendMessage(StaticData.MESSAGE_STOP_DATA_TRANSMISSION,false);
    }

    private void sendMessage(int what,boolean isNeedReply){
        if(mServiceMessenger!=null&&mActivity.getIsConnected().get()){
            Message message = Message.obtain(null, what);
            if(isNeedReply){
                message.replyTo=mLocalMessenger;
            }
            try {
                mServiceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
                mActivity.getIsConnected().set(false);
                mActivity.unbindService(mServiceConnection);
            }
        }
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
