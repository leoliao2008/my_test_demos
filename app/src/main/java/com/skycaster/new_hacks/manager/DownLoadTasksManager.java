package com.skycaster.new_hacks.manager;

import android.util.Log;

import com.skycaster.new_hacks.bean.DownLoadTask;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 廖华凯 on 2017/8/31.
 */

public class DownLoadTasksManager {
    private static DownLoadTasksManager sDownLoadTasksManager;
    private ThreadPoolExecutor mDownLoadThreadPoolExecutor;
    private LinkedBlockingDeque<Runnable> mLinkedBlockingDeque=new LinkedBlockingDeque<>();
    private int coreNum;
    private ArrayList<DownLoadTask> mDownLoadTasks=new ArrayList<>();

    static {
        sDownLoadTasksManager =new DownLoadTasksManager();
    }
    private DownLoadTasksManager() {
        coreNum=Runtime.getRuntime().availableProcessors();
        showLog("coreNum = "+coreNum);
        mDownLoadThreadPoolExecutor=new ThreadPoolExecutor(
                coreNum,
                coreNum,
                1000,
                TimeUnit.MILLISECONDS,
                mLinkedBlockingDeque
        );
        mDownLoadThreadPoolExecutor.allowCoreThreadTimeOut(true);
    }
    public static DownLoadTasksManager getInstance(){
        return sDownLoadTasksManager;
    }

    public void execute(DownLoadTask task){
        if(mDownLoadTasks.contains(task)){
            return;
        }
        mDownLoadTasks.add(task);
        mDownLoadThreadPoolExecutor.execute(task);
    }

    public void cancelAll(){
        mDownLoadThreadPoolExecutor.getQueue().clear();
        for (int i=0,size=mDownLoadTasks.size();i<size;i++){
            Thread thread = mDownLoadTasks.get(i).getThread();
            if(thread!=null&&thread.isAlive()){
                thread.interrupt();
            }
        }
        mDownLoadTasks.clear();
    }


    public void dump(DownLoadTask task){
        mDownLoadThreadPoolExecutor.getQueue().remove(task);
        mDownLoadTasks.remove(task);
        Thread thread = task.getThread();
        if(thread!=null&&thread.isAlive()){
            thread.interrupt();
        }
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }


}
