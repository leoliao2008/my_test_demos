package com.skycaster.new_hacks.bean;

import android.os.SystemClock;
import android.util.Log;

import com.skycaster.new_hacks.data.StaticData;
import com.skycaster.new_hacks.manager.DownLoadTasksManager;

/**
 * Created by 廖华凯 on 2017/8/31.
 */

public class DownLoadTask implements Runnable{
    private DownLoadBean mDownLoadBean;

    public DownLoadTask(DownLoadBean downLoadBean) {
        mDownLoadBean = downLoadBean;
    }

    @Override
    public void run() {
        mDownLoadBean.setThread(Thread.currentThread());
        int progress = mDownLoadBean.getProgress();
        while (progress<100&&!mDownLoadBean.getThread().isInterrupted()){
            progress++;
            showLog("thread id = "+mDownLoadBean.getThread().getId()+"; progress = "+progress);
            mDownLoadBean.setProgress(progress);
            mDownLoadBean.setState(StaticData.DOWNLOAD_STATE_DOWNLOADING);
            mDownLoadBean.onProgressUpdate(progress);
            SystemClock.sleep(500);
        }
        if(mDownLoadBean.getThread().isInterrupted()){
            mDownLoadBean.setState(StaticData.DOWNLOAD_STATE_PAUSE);
            mDownLoadBean.onPause();
        }else {
            mDownLoadBean.setState(StaticData.DOWNLOAD_STATE_FINISHED);
            mDownLoadBean.onComplete();
        }
    }

    public Thread getThread() {
        return mDownLoadBean.getThread();
    }


    public void handleActionByState(int state){
        switch (state){
            case StaticData.DOWNLOAD_STATE_DOWNLOADING:
                if(mDownLoadBean.getThread()==null){
                    showLog("thread is null");
                }else {
                    showLog("thread id to be terminated: "+mDownLoadBean.getThread().getId());
                }
                DownLoadTasksManager.getInstance().dump(this);
                 break;
            case StaticData.DONWLOAD_STATE_PENDING:
                DownLoadTasksManager.getInstance().dump(this);
                mDownLoadBean.setState(StaticData.DOWNLOAD_STATE_DEFAULT);
                mDownLoadBean.onFailed();
                break;
            case StaticData.DOWNLOAD_STATE_FINISHED:
                //do nothing.
                break;
            case StaticData.DOWNLOAD_STATE_FAILED:
            case StaticData.DOWNLOAD_STATE_PAUSE:
            case StaticData.DOWNLOAD_STATE_DEFAULT:
            default:
                mDownLoadBean.setState(StaticData.DONWLOAD_STATE_PENDING);
                mDownLoadBean.onPending();
                DownLoadTasksManager.getInstance().execute(this);
                break;
        }
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownLoadTask that = (DownLoadTask) o;

        return mDownLoadBean != null ? mDownLoadBean.equals(that.mDownLoadBean) : that.mDownLoadBean == null;

    }

    @Override
    public int hashCode() {
        return mDownLoadBean != null ? mDownLoadBean.hashCode() : 0;
    }
}
