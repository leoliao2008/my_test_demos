package com.skycaster.new_hacks.activity;

import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.skycaster.new_hacks.R;

public class DynamicLayoutActivity extends AppCompatActivity {
    private LinearLayout containerDefault;
    private LinearLayout containerCustom;
    private LayoutTransition mLayoutTransition;
    private ScrollView mScrollView;
    private Button btn_animatePropertyValueHolder;
    private ImageView iv_compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_layout);
        mScrollView= (ScrollView) findViewById(R.id.dynamic_root_view);
        containerDefault= (LinearLayout) findViewById(R.id.dynamic_container_default);
        containerCustom= (LinearLayout) findViewById(R.id.dynamic_container_custom);
        btn_animatePropertyValueHolder= (Button) findViewById(R.id.dynamic_btn_test_property_value_holder);
        iv_compass= (ImageView) findViewById(R.id.dynamic_iv_compass);
        mLayoutTransition=new LayoutTransition();
        containerCustom.setLayoutTransition(mLayoutTransition);
        initLayoutTransition();

    }

    private void initLayoutTransition() {
        mLayoutTransition.setAnimator(
                LayoutTransition.APPEARING,
                ObjectAnimator.ofFloat(null,"rotationY",90f,0f).setDuration(mLayoutTransition.getDuration(LayoutTransition.APPEARING))
        );

        mLayoutTransition.setAnimator(
                LayoutTransition.DISAPPEARING,
                ObjectAnimator.ofFloat(null,"rotationX",0f,90f).setDuration(mLayoutTransition.getDuration(LayoutTransition.DISAPPEARING))
        );

        mLayoutTransition.setStagger(LayoutTransition.CHANGE_APPEARING,30);
        mLayoutTransition.setStagger(LayoutTransition.CHANGE_DISAPPEARING,30);

//        PropertyValuesHolder pvhLeft=PropertyValuesHolder.ofInt("left",0,1);
//        PropertyValuesHolder pvhTop=PropertyValuesHolder.ofInt("top",0,1);
//        PropertyValuesHolder pvhRight=PropertyValuesHolder.ofInt("right",0,1);
//        PropertyValuesHolder pvhBottom=PropertyValuesHolder.ofInt("bottom",0,1);
//
//        PropertyValuesHolder pvhScaleX=PropertyValuesHolder.ofFloat("scaleX",1,2,1);
//        mLayoutTransition.setAnimator(
//                LayoutTransition.CHANGE_APPEARING,
//                ObjectAnimator.ofPropertyValuesHolder(
//                        pvhLeft,
//                        pvhTop,
//                        pvhRight,
//                        pvhBottom,
//                        pvhScaleX
//                ).setDuration(
//                        mLayoutTransition.getDuration(LayoutTransition.CHANGE_APPEARING)
//                )
//        );
//
//        PropertyValuesHolder pvhScaleY=PropertyValuesHolder.ofFloat("scaleY",1,2,1);
//        mLayoutTransition.setAnimator(
//                LayoutTransition.CHANGE_DISAPPEARING,
//                ObjectAnimator.ofPropertyValuesHolder(
//                        pvhLeft,
//                        pvhTop,
//                        pvhRight,
//                        pvhBottom,
//                        pvhScaleY
//                ).setDuration(
//                        mLayoutTransition.getDuration(LayoutTransition.CHANGE_DISAPPEARING)
//                )
//        );


    }

    public void addDefault(View view) {
        TextView child=new TextView(this);
        child.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        child.setTextSize(18);
        child.setTextColor(Color.BLACK);
        child.setGravity(Gravity.CENTER);
        child.setBackgroundColor(Color.GRAY);
        child.setText("This is a add item.");
        containerDefault.addView(child);
        mScrollView.fullScroll(View.FOCUS_DOWN);


    }

    public void removeDefault(View view) {
        if(containerDefault.getChildCount()>2){
            containerDefault.removeViewAt(2);
        }
    }

    public void addCustom(View view) {
        TextView child=new TextView(this);
        child.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        child.setTextSize(18);
        child.setTextColor(Color.BLACK);
        child.setGravity(Gravity.CENTER);
        child.setBackgroundColor(Color.GRAY);
        child.setText("This is a add item.");
        containerCustom.addView(child);
        mScrollView.fullScroll(View.FOCUS_DOWN);

    }

    public void removeCustom(View view) {
        if(containerCustom.getChildCount()>2){
            containerCustom.removeViewAt(2);
        }
    }

    public void animatePVH(View view) {
        Keyframe keyframe1 = Keyframe.ofFloat(0.0f,0);
        Keyframe keyframe2 = Keyframe.ofFloat(0.25f,-30);
        Keyframe keyframe3 = Keyframe.ofFloat(0.5f,0);
        Keyframe keyframe4 = Keyframe.ofFloat(0.75f, 30);
        Keyframe keyframe5 = Keyframe.ofFloat(1.0f,0);
        PropertyValuesHolder pv1=PropertyValuesHolder.ofKeyframe("rotation",keyframe1,keyframe2,keyframe3,keyframe4,keyframe5);
        PropertyValuesHolder pv2=PropertyValuesHolder.ofFloat("alpha",0.5f,1f);
        PropertyValuesHolder pv3 = PropertyValuesHolder.ofFloat("scaleX",1.0f,0.2f,1.0f);
        PropertyValuesHolder pv4 = PropertyValuesHolder.ofFloat("scaleY",1.0f,0.2f,1.0f);
        PropertyValuesHolder pv5 = PropertyValuesHolder.ofInt("BackgroundColor", 0XFFFFFF00, 0XFF0000FF);
        ObjectAnimator animator=ObjectAnimator.ofPropertyValuesHolder(btn_animatePropertyValueHolder,pv1,pv2,pv3,pv4,pv5);
        animator.setDuration(5000);
        animator.setRepeatCount(5);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

    private boolean flag;
    public void animateViewPropertyAnimation(View view) {
        if(flag){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                iv_compass.animate().translationX(300).translationY(100).translationZ(-50).alpha(.5f).rotation(180).scaleX(0.5f).scaleY(0.5f).setDuration(2000).setInterpolator(new BounceInterpolator());
            }else {
                iv_compass.animate().translationX(300).translationY(100).alpha(.5f).rotation(180).scaleX(0.5f).scaleY(0.5f).setDuration(2000).setInterpolator(new BounceInterpolator());
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                iv_compass.animate().translationX(-200).translationY(0).translationZ(100).alpha(1).rotation(-180).scaleX(1.2f).scaleY(1.2f).setDuration(2000).setInterpolator(new BounceInterpolator());
            }else {
                iv_compass.animate().translationX(-200).translationY(0).alpha(1).rotation(-180).scaleX(1.2f).scaleY(1.2f).setDuration(2000).setInterpolator(new BounceInterpolator());
            }
        }
        flag=!flag;
    }
}
