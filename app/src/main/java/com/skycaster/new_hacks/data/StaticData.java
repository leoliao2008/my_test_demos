package com.skycaster.new_hacks.data;

/**
 * Created by 廖华凯 on 2017/8/23.
 */

public interface StaticData {
    int MESSAGE_WHAT_SAY_HELLO = 10;
    int MESSAGE_WHAT_SAY_GOODBYE = 11;
    int MESSAGE_WHAT_SAY_HI = 12;
    int MESSAGE_REQUEST_REGISTER_CLIENT =13;
    int MESSAGE_REGISTER_CLIENT_SUCCESS =14;
    int MESSAGE_START_DATA_TRANSMISSION=15;
    int MESSAGE_REQUEST_UNREGISTER_CLIENT =16;
    int MESSAGE_UNREGISTER_CLIENT_SUCCESS =17;
    int MESSAGE_DATA = 18;
    int MESSAGE_STOP_DATA_TRANSMISSION=19;
    int MESSAGE_SHOW_NOTIFICATION=20;
    String EXTRA_DATA ="EXTRA_DATA";
    String[] SIMULATION_DATA =new String[]{
            "HandlerThread将loop转到子线程中处理，说白了就是将分担MainLooper的工作量，降低了主线程的压力，使主界面更流畅。",
            "开启一个线程起到多个线程的作用。处理任务是串行执行，按消息发送顺序进行处理。HandlerThread本质是一个线程，在线程内部，代码是串行处理的。",
            "但是由于每一个任务都将以队列的方式逐个被执行到，一旦队列中有某个任务执行时间过长，那么就会导致后续的任务都会被延迟处理。",
            "HandlerThread拥有自己的消息队列，它不会干扰或阻塞UI线程。",
            "对于网络IO操作，HandlerThread并不适合，因为它只有一个线程，还得排队一个一个等着。",
            "无论是调用了quit方法还是quitSafely方法只会，Looper就不再接收新的消息。即在调用了Looper的quit或quitSafely方法之后，消息循环就终结了，这时候再通过Handler调用sendMessage或post等方法发送消息时均返回false，表示消息没有成功放入消息队列MessageQueue中，因为消息队列已经退出了。",
            "需要注意的是Looper的quit方法从API Level 1就存在了，但是Looper的quitSafely方法从API Level 18才添加进来。",
            "很明显的一点就是，我们要在子线程中调用Looper.prepare() 为一个线程开启一个消息循环，默认情况下Android中新诞生的线程是没有开启消息循环的。（主线程除外，主线程系统会自动为其创建Looper对象，开启消息循环。） Looper对象通过MessageQueue来存放消息和事件。一个线程只能有一个Looper，对应一个MessageQueue。 然后通过Looper.loop() 让Looper开始工作，从消息队列里取消息，处理消息。"};


    int DOWNLOAD_STATE_DEFAULT=0;
    int DOWNLOAD_STATE_DOWNLOADING=1;
    int DOWNLOAD_STATE_PAUSE=2;
    int DOWNLOAD_STATE_FINISHED=3;
    int DOWNLOAD_STATE_FAILED=4;

}
