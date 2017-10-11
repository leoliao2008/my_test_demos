package com.skycaster.new_hacks.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.util.ToastUtil;

import butterknife.ButterKnife;

public class VectorDrawableDemo extends AppCompatActivity {
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private RadioGroup mRadioGroup;
    private ImageView mImageView6;
    private int[] STATE_SET_NORMAL =new int[]{
            R.attr.net_status_normal,
            -R.attr.net_status_unstable,
            -R.attr.net_status_stopped,
            -R.attr.net_status_no_update,
            -R.attr.net_status_link_fail,
            -R.attr.net_status_initializing,
    };
    private int[] STATE_SET_UNSTATBLE =new int[]{
            -R.attr.net_status_normal,
            R.attr.net_status_unstable,
            -R.attr.net_status_stopped,
            -R.attr.net_status_no_update,
            -R.attr.net_status_link_fail,
            -R.attr.net_status_initializing,
    };
    private int[] STATE_SET_STOPPED =new int[]{
            -R.attr.net_status_normal,
            -R.attr.net_status_unstable,
            R.attr.net_status_stopped,
            -R.attr.net_status_no_update,
            -R.attr.net_status_link_fail,
            -R.attr.net_status_initializing,
    };
    private int[] STATE_SET_NO_UPDATE =new int[]{
            -R.attr.net_status_normal,
            -R.attr.net_status_unstable,
            -R.attr.net_status_stopped,
            R.attr.net_status_no_update,
            -R.attr.net_status_link_fail,
            -R.attr.net_status_initializing,
    };
    private int[] STATE_SET_LINKED_FAIL =new int[]{
            -R.attr.net_status_normal,
            -R.attr.net_status_unstable,
            -R.attr.net_status_stopped,
            -R.attr.net_status_no_update,
            R.attr.net_status_link_fail,
            -R.attr.net_status_initializing,
    };
    private int[] STATE_SET_INITIALIZING =new int[]{
            -R.attr.net_status_normal,
            -R.attr.net_status_unstable,
            -R.attr.net_status_stopped,
            -R.attr.net_status_no_update,
            -R.attr.net_status_link_fail,
            R.attr.net_status_initializing,
    };



    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private AnimatedVectorDrawable mAnimatedVectorDrawable;
    private AnimatedVectorDrawable mDrawable1;
    private AnimatedVectorDrawable mDrawable2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable_demo);
        ButterKnife.bind(this);
        mImageView1 = (ImageView) findViewById(R.id.vector_drawable_iv_1);
        Drawable drawable = mImageView1.getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }

        mImageView2= (ImageView) findViewById(R.id.vector_drawable_iv_2);
        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawable1==null){
                    mDrawable1 = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_smiling_face);
                    mImageView2.setImageDrawable(mDrawable1);
                }
                if(mDrawable1.isRunning()){
                    mDrawable1.stop();
                }else {
                    mDrawable1.start();
                }

            }
        });

        mImageView3= (ImageView) findViewById(R.id.vector_drawable_iv_3);
        mImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawable2==null){
                    mDrawable2 = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_rec_to_tri);
                    mImageView3.setImageDrawable(mDrawable2);
                }
                if(mDrawable2.isRunning()){
                    mDrawable2.stop();
                }else {
                    mDrawable2.start();
                }


            }
        });

        mImageView6= (ImageView) findViewById(R.id.vector_drawable_iv_6);
    }




    public void showToast(String msg){
        ToastUtil.toast(this,msg);
    }

    public void toStateNormal(View view) {
        mImageView6.setImageState(STATE_SET_NORMAL,true);
    }

    public void toStateUnstable(View view) {
        mImageView6.setImageState(STATE_SET_UNSTATBLE,true);
    }

    public void toStateStopped(View view) {
        mImageView6.setImageState(STATE_SET_STOPPED,true);
    }


    public void toStateNoUpdate(View view) {
        mImageView6.setImageState(STATE_SET_NO_UPDATE,true);
    }

    public void toStateLinkError(View view) {
        mImageView6.setImageState(STATE_SET_LINKED_FAIL,true);
    }

    public void toStateInitializing(View view) {
        mImageView6.setImageState(STATE_SET_INITIALIZING,true);
    }
}
