package com.skycaster.new_hacks.bean;

import com.skycaster.new_hacks.adapter.DownLoadListAdapter;
import com.skycaster.new_hacks.data.StaticData;
import com.skycaster.new_hacks.intf.DownLoadStateListener;

/**
 * Created by 廖华凯 on 2017/8/29.
 */

public class DownLoadBean implements DownLoadStateListener{
    private String title;
    private int progress;
    private int state;
    private DownLoadListAdapter.CallBack mCallBack;
    private Thread mThread;

    public DownLoadBean(String title) {
        this.title = title;
        progress=0;
        state= StaticData.DOWNLOAD_STATE_DEFAULT;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public int getProgress() {
        return progress;
    }

    public int getState() {
        return state;
    }

    public Thread getThread() {
        return mThread;
    }

    public void setThread(Thread thread) {
        mThread = thread;
    }

    @Override
    public void onProgressUpdate(int progress) {
        if(mCallBack!=null){
            mCallBack.onProgressUpdate(progress);
        }
    }

    @Override
    public void onPause() {
        if(mCallBack!=null){
            mCallBack.onPause();
        }

    }

    @Override
    public void onComplete() {
        if(mCallBack!=null){
            mCallBack.onComplete();
        }

    }

    @Override
    public void onFailed() {
        if(mCallBack!=null){
            mCallBack.onFail();
        }

    }

    @Override
    public void onPending() {
        if(mCallBack!=null){
            mCallBack.onPending();
        }
    }

    public void setCallBack(DownLoadListAdapter.CallBack callBack){
        mCallBack=callBack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownLoadBean that = (DownLoadBean) o;

        return title != null ? title.equals(that.title) : that.title == null;

    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
