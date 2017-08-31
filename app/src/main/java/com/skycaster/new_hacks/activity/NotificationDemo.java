package com.skycaster.new_hacks.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.util.ToastUtil;

public class NotificationDemo extends AppCompatActivity {
    private NotificationManagerCompat mNotificationManager;
    private Handler mForeGroundHandler;
    private int currentProgress;
    private int PROGRESS_NOTIFICATION_ID=321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
        initData();
        initListeners();
    }

    private void initView() {

    }

    private void initData() {
        mNotificationManager =NotificationManagerCompat.from(this);
        mForeGroundHandler=new Handler();

    }

    private void initListeners() {

    }

    public void showProgressDeterminate(View view) {
        final NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("This is a progress notification!")
                .setContentText("Downloading......");
        currentProgress=0;
        new Thread(new Runnable() {
            @Override
            public void run() {
               while (currentProgress<=100){
                   builder.setProgress(100,currentProgress,false);
                   mNotificationManager.notify(PROGRESS_NOTIFICATION_ID,builder.build());
                   currentProgress+=5;
                   try {
                       Thread.sleep(500);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
                builder.setContentText("DownLoad Complete!");
                builder.setProgress(0,0,false);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                mNotificationManager.notify(PROGRESS_NOTIFICATION_ID,notification);
                mForeGroundHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNotificationManager.cancel(PROGRESS_NOTIFICATION_ID);
                        ToastUtil.toast(NotificationDemo.this,"下载完成！");
                    }
                },2000);

            }
        }).start();

    }

    public void showProgressIndeterminate(View view) {
        final NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("This is a progress notification!")
                .setContentText("Downloading......");
        currentProgress=0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (currentProgress<=100){
                    builder.setProgress(0,0,true);
                    mNotificationManager.notify(PROGRESS_NOTIFICATION_ID,builder.build());
                    currentProgress+=5;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                builder.setContentText("DownLoad Complete!");
                builder.setProgress(0,0,false);
                builder.setAutoCancel(true);
                Notification notification = builder.build();
                mNotificationManager.notify(PROGRESS_NOTIFICATION_ID,notification);
                mForeGroundHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNotificationManager.cancel(PROGRESS_NOTIFICATION_ID);
                        ToastUtil.toast(NotificationDemo.this,"下载完成！");
                    }
                },2000);

            }
        }).start();
    }

    public void showHeadUp(View view) {
        Intent intent = new Intent(NotificationDemo.this, PendingIntentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon_type_1))
                .setContentTitle("This is a Head-up notification.")
                .setContentText("You got mail!")
                .setFullScreenIntent(pendingIntent,true)
                .setAutoCancel(true);


        mNotificationManager.notify(654,builder.build());

    }

    public void showCustomized(View view) {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.custom_remote_view);
        remoteViews.setTextViewText(R.id.remote_view_title,"This is a remote view!");
        remoteViews.setImageViewResource(R.id.remote_view_icon,R.drawable.g_f_icon_music);
        Intent intent = new Intent(NotificationDemo.this, PendingIntentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        remoteViews.setOnClickPendingIntent(R.id.remote_view_action_button,pendingIntent);
        builder.setContent(remoteViews);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        mNotificationManager.notify(987,builder.build());
    }

    public void showBigText(View view) {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        NotificationCompat.BigTextStyle style=new NotificationCompat.BigTextStyle();
        style.setBigContentTitle("This is big text title!").setSummaryText("This is summary").bigText("fddfdggfdfdsfefefd" +
                "dfdfdfadfadfadfdfadfadfdfdafadfadfadfdfadfadfaadfadfaddasf");
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("This is a big text style notification.")
                .setContentText("This is content text for notification.")
                .setStyle(style)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_add_circle_grey600_48dp));
        mNotificationManager.notify(654,builder.build());

    }

    public void showBigPic(View view) {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        NotificationCompat.BigPictureStyle style=new NotificationCompat.BigPictureStyle();
        style.setBigContentTitle("This is big pic style title! ").setSummaryText("This is summary").bigLargeIcon(
                BitmapFactory.decodeResource(getResources(),R.drawable.g_f_icon_music)
        ).bigPicture(
                BitmapFactory.decodeResource(getResources(),R.drawable.dialog_cku_banner_06)
        );
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("This is a big pic style notification.")
                .setContentText("This is content text for notification.")
                .setStyle(style)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_add_circle_grey600_48dp));
        mNotificationManager.notify(682,builder.build());

    }
}
