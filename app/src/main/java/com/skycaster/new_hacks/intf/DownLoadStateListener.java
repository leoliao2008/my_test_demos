package com.skycaster.new_hacks.intf;

/**
 * Created by 廖华凯 on 2017/8/31.
 */

public interface DownLoadStateListener {
    void onProgressUpdate(int progress);
    void onPause();
    void onComplete();
    void onFailed();
    void onPending();
}
