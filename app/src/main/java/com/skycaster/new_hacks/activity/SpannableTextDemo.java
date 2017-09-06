package com.skycaster.new_hacks.activity;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Property;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.customized.MutableForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpannableTextDemo extends AppCompatActivity {

    private TextView tv_1;
    private TextView tv_2;
    private Property<MutableForegroundColorSpan,Integer> mProperty=new Property<MutableForegroundColorSpan, Integer>(Integer.class,"MUTABLE_COLOR") {
        @Override
        public Integer get(MutableForegroundColorSpan object) {
            return object.getColor();
        }

        @Override
        public void set(MutableForegroundColorSpan object, Integer value) {
            object.setColor(value);
        }
    };
    private ValueAnimator mValueAnimator;
    private TextView mChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_text_demo);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        tv_1= (TextView) findViewById(R.id.spannable_text_1);
        tv_2= (TextView) findViewById(R.id.spannable_text_2);

    }

    private void initData() {

        final SpannableString sps=new SpannableString(tv_1.getText());
        final String target1="test";
        final int startOffset = sps.toString().indexOf(target1);
        final MutableForegroundColorSpan mtsp=new MutableForegroundColorSpan(Color.GREEN,255);
        sps.setSpan(mtsp,startOffset,startOffset+target1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_1.setText(sps);
        mValueAnimator = ValueAnimator.ofInt(Color.GREEN,Color.RED);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setEvaluator(new ArgbEvaluator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int newColor = (int) animation.getAnimatedValue();
                mtsp.setColor(newColor);
                tv_1.setText(sps);
            }
        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                showLog("onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showLog("onAnimationEnd");

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                showLog("onAnimationCancel");

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                showLog("onAnimationRepeat");

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mValueAnimator.addPauseListener(new Animator.AnimatorPauseListener() {
                @Override
                public void onAnimationPause(Animator animation) {
                    showLog("onAnimationPause");
                }

                @Override
                public void onAnimationResume(Animator animation) {
                    showLog("onAnimationResume");
                }
            });
        }
        mValueAnimator.start();




        String target2="jjj";
        SpannableString sps2=new SpannableString(tv_2.getText());
        Pattern pattern=Pattern.compile(target2);
        Matcher matcher = pattern.matcher(tv_2.getText());
        while (matcher.find()){
            sps2.setSpan(new ForegroundColorSpan(Color.RED),matcher.start(),matcher.end(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv_2.setText(sps2);


    }

    private void initListener() {

    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isFinishing()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mValueAnimator.pause();
                mValueAnimator.resume();
            }
            mValueAnimator.cancel();
            mValueAnimator.removeAllUpdateListeners();
            mValueAnimator.removeAllListeners();
            mValueAnimator=null;
        }
    }
}
