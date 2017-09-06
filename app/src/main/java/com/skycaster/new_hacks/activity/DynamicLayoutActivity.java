package com.skycaster.new_hacks.activity;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.skycaster.new_hacks.R;

public class DynamicLayoutActivity extends AppCompatActivity {
    private LinearLayout containerDefault;
    private LinearLayout containerCustom;
    private LayoutTransition mLayoutTransition;
    private ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_layout);
        mScrollView= (ScrollView) findViewById(R.id.dynamic_root_view);
        containerDefault= (LinearLayout) findViewById(R.id.dynamic_container_default);
        containerCustom= (LinearLayout) findViewById(R.id.dynamic_container_custom);
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
}
