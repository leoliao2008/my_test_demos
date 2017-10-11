package com.skycaster.new_hacks.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.activity.PendingIntentActivity;
import com.skycaster.new_hacks.data.StaticData;
import com.skycaster.new_hacks.util.ToastUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import static com.skycaster.new_hacks.data.StaticData.MESSAGE_REQUEST_REGISTER_CLIENT;
import static com.skycaster.new_hacks.data.StaticData.MESSAGE_REQUEST_UNREGISTER_CLIENT;
import static com.skycaster.new_hacks.data.StaticData.MESSAGE_SHOW_NOTIFICATION;
import static com.skycaster.new_hacks.data.StaticData.MESSAGE_START_DATA_TRANSMISSION;
import static com.skycaster.new_hacks.data.StaticData.MESSAGE_STOP_DATA_TRANSMISSION;
import static com.skycaster.new_hacks.data.StaticData.MESSAGE_WHAT_SAY_GOODBYE;
import static com.skycaster.new_hacks.data.StaticData.MESSAGE_WHAT_SAY_HELLO;
import static com.skycaster.new_hacks.data.StaticData.MESSAGE_WHAT_SAY_HI;
import static com.skycaster.new_hacks.data.StaticData.SIMULATION_DATA;

/**
 * Created by 廖华凯 on 2017/8/23.
 */

public class IpcServer extends Service {
    private IncomingMessageHandler mIncomingMessageHandler =new IncomingMessageHandler();
    private Messenger mMessenger=new Messenger(mIncomingMessageHandler);
    private HashMap<Integer,Messenger> mClientList=new HashMap<>();
    private Random mRandom;

    private Runnable mRunnable_sendDate=new Runnable() {
        @Override
        public void run() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (IpcServer.this){
                        if(mClientList.size()>0){
                            Iterator<Messenger> iterator = mClientList.values().iterator();
                            while (iterator.hasNext()){
                                Messenger messenger = iterator.next();
                                send(messenger,StaticData.MESSAGE_DATA,generateBundle());
                            }
                            mIncomingMessageHandler.postDelayed(mRunnable_sendDate,2000);
                        }
                    }
                }
            }).start();
        }
    };

    private Runnable mRunnable_sendNotification=new Runnable() {
        @Override
        public void run() {
            Intent intent=new Intent(IpcServer.this,PendingIntentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent=PendingIntent.getActivity(
                    IpcServer.this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

//            TaskStackBuilder stb= TaskStackBuilder.create(IpcServer.this);
//            stb.addParentStack(PendingIntentActivity.class);
//            stb.addNextIntent(intent);
//            PendingIntent pendingIntent=stb.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder builder=new NotificationCompat.Builder(IpcServer.this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round));
            builder.setContentTitle("This is a notification.");
            builder.setContentText("Click to start a new activity.");
            builder.setContentIntent(pendingIntent);
//            builder.setChannel("My_Channel_ID_01");
            builder.setAutoCancel(true);
            builder.setWhen(System.currentTimeMillis());
            builder.setOnlyAlertOnce(true);
            NotificationCompat.InboxStyle inboxStyle=new NotificationCompat.InboxStyle(builder);
            inboxStyle.setBigContentTitle("Event tracker details:");
            String[] events=new String[6];
            for(int i=0;i<events.length;i++){
                inboxStyle.addLine("This is event "+i);
            }
            builder.setStyle(inboxStyle);
            for(int i=1;i<3;i++){
                builder.setContentText("My Current Content Text "+i).setNumber(i);
                NotificationManagerCompat.from(IpcServer.this).notify(123,builder.build());
                SystemClock.sleep(500);
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        mRandom=new Random();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        showLog("onBind");
        return mMessenger.getBinder();
    }


    private class IncomingMessageHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_WHAT_SAY_HELLO:
                    ToastUtil.showCustomToast(IpcServer.this,"Hello World!");
                    break;
                case MESSAGE_WHAT_SAY_GOODBYE:
                    Toast.makeText(IpcServer.this,"Good Bye!",Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_WHAT_SAY_HI:
                    Toast.makeText(IpcServer.this,"Hi Man!",Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_REQUEST_REGISTER_CLIENT:
                    showLog("MESSAGE_REQUEST_REGISTER_CLIENT");
                    mClientList.put(msg.replyTo.hashCode(),msg.replyTo);
                    send(msg.replyTo,StaticData.MESSAGE_REGISTER_CLIENT_SUCCESS,null);
                    break;
                case MESSAGE_REQUEST_UNREGISTER_CLIENT:
                    showLog("MESSAGE_REQUEST_UNREGISTER_CLIENT");
                    mClientList.remove(msg.replyTo.hashCode());
                    send(msg.replyTo,StaticData.MESSAGE_UNREGISTER_CLIENT_SUCCESS,null);
                    break;
                case MESSAGE_START_DATA_TRANSMISSION:
                    showLog("MESSAGE_START_DATA_TRANSMISSION");
                    mIncomingMessageHandler.postDelayed(mRunnable_sendDate,2000);
                    break;
                case MESSAGE_STOP_DATA_TRANSMISSION:
                    showLog("MESSAGE_STOP_DATA_TRANSMISSION");
                    mIncomingMessageHandler.removeCallbacks(mRunnable_sendDate);
                    break;
                case MESSAGE_SHOW_NOTIFICATION:
                    showLog("MESSAGE_SHOW_NOTIFICATION");
                    removeCallbacks(mRunnable_sendNotification);
                    post(mRunnable_sendNotification);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

    }



    private void send(Messenger byWhich, int what, Bundle data){
        showLog("byWhich = "+byWhich.hashCode());
        Message message = Message.obtain(null, what);
        message.setData(data);
        try {
            byWhich.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }

    @Override
    public void onDestroy() {
        showLog("onDestroy");
        super.onDestroy();
        mIncomingMessageHandler.removeCallbacks(mRunnable_sendDate);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        showLog("onUnbind");
        return super.onUnbind(intent);
    }

    private Bundle generateBundle(){
        Bundle bundle=new Bundle();
        String data = SIMULATION_DATA[mRandom.nextInt(SIMULATION_DATA.length)];
        bundle.putString(StaticData.EXTRA_DATA, data);
        return bundle;
    }
}
