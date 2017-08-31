package com.skycaster.new_hacks.bean;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.widget.BaseAdapter;

import com.skycaster.new_hacks.data.StaticData;
import com.skycaster.new_hacks.intf.DownLoadStateListener;
import com.skycaster.new_hacks.manager.DownLoadTasksManager;

/**
 * Created by 廖华凯 on 2017/8/31.
 */

public class DownLoadTask implements Runnable,DownLoadStateListener{
    private DownLoadBean mDownLoadBean;
    private Thread mThread;
    private BaseAdapter mAdapter;
    private Handler mHandler;

    public DownLoadTask(DownLoadBean downLoadBean) {
        mDownLoadBean = downLoadBean;
        mHandler=new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        mThread=Thread.currentThread();
        int progress = mDownLoadBean.getProgress();
        while (progress<100&&!mThread.isInterrupted()){
            progress++;
            onProgressUpdate(progress);
            SystemClock.sleep(1000);
        }
        if(mThread.isInterrupted()){
            onPause();
        }else {
            onComplete();
        }
    }

    public Thread getThread() {
        return mThread;
    }


    public void handleActionByPreState(int preState){
        switch (preState){
            case StaticData.DOWNLOAD_STATE_DOWNLOADING:
                if(mThread==null){
                    showLog("thread is null");
                }else {
                    showLog("thread id to be terminated: "+mThread.getId());
                }
                DownLoadTasksManager.getInstance().dump(this);
                break;
            case StaticData.DOWNLOAD_STATE_PENDING:
                DownLoadTasksManager.getInstance().dump(this);
                onCancel();
                break;
            case StaticData.DOWNLOAD_STATE_FINISHED:
                //do nothing.
                break;
            case StaticData.DOWNLOAD_STATE_FAILED:
            case StaticData.DOWNLOAD_STATE_PAUSE:
            case StaticData.DOWNLOAD_STATE_DEFAULT:
            default:
                onPending();
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

    @Override
    public void onProgressUpdate(int progress) {
        mDownLoadBean.setProgress(progress);
        mDownLoadBean.setState(StaticData.DOWNLOAD_STATE_DOWNLOADING);
        updateAdapter();
    }

    private void updateAdapter() {
        if(mAdapter!=null){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void onPause() {
        mDownLoadBean.setState(StaticData.DOWNLOAD_STATE_PAUSE);
        updateAdapter();

    }

    @Override
    public void onComplete() {
        mDownLoadBean.setState(StaticData.DOWNLOAD_STATE_FINISHED);
        updateAdapter();

    }

    @Override
    public void onFailed() {
        mDownLoadBean.setState(StaticData.DOWNLOAD_STATE_FAILED);
        updateAdapter();

    }

    @Override
    public void onPending() {
        mDownLoadBean.setState(StaticData.DOWNLOAD_STATE_PENDING);
        updateAdapter();
    }

    @Override
    public void onCancel() {
        mDownLoadBean.setState(StaticData.DOWNLOAD_STATE_DEFAULT);
        updateAdapter();
    }

    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(BaseAdapter adapter) {
        mAdapter = adapter;
    }
}
