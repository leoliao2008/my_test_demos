package com.skycaster.new_hacks.bean;

import com.skycaster.new_hacks.data.StaticData;

/**
 * Created by 廖华凯 on 2017/8/29.
 */

public class DownLoadBean {
    private String title;
    private int progress;
    private int state;

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
}
