package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.fragment.RetainFragment;

/**
 * Created by 廖华凯 on 2017/9/4.
 */

public class ConfigChangeDemo extends AppCompatActivity {
    private RetainFragment mFragment;
    private FragmentManager mManager;
    private static final String TAG_MY_RETAIN_FRAGMENT ="MY_RETAIN_FRAGMENT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_change_demo);
        mManager = getSupportFragmentManager();
        mFragment = (RetainFragment) mManager.findFragmentByTag(TAG_MY_RETAIN_FRAGMENT);
        if(mFragment==null){
            mFragment=new RetainFragment();
            mManager.beginTransaction()
                    .replace(R.id.activity_config_change_demo_root_view,mFragment,TAG_MY_RETAIN_FRAGMENT)
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isFinishing()){
            mManager.beginTransaction().remove(mFragment).commit();
            mFragment=null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
