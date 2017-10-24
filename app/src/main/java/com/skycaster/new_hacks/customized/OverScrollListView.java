package com.skycaster.new_hacks.customized;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;

/**
 * Created by 廖华凯 on 2017/10/16.
 */

public class OverScrollListView extends ListView {
    public OverScrollListView(Context context) {
        this(context,null);
    }

    public OverScrollListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OverScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private int originTop;
    private int originBottom;
    private int originRight;
    private int originLeft;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(getClass().getSimpleName(),"onSizeChanged = "+getLeft()+" "+getTop()+" "+getRight()+" "+getBottom());
        originTop=getTop();
        originBottom=getBottom();
        originLeft=getLeft();
        originRight=getRight();
    }

    private float startY;
    private int dY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY=event.getY();
                dY=0;
                break;
            case MotionEvent.ACTION_MOVE:
                if(getFirstVisiblePosition()==0){
                    dY= Math.min((int) (event.getY()-startY),100);
                    layout(originLeft,originTop+dY,originRight,originBottom+dY);
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                animateLayoutTranslationVertical(dY,0,500);
                break;
        }
        return super.onTouchEvent(event);
    }

    private void animateLayoutTranslationVertical(int from, int to,long milliSeconds) {
        ValueAnimator animator=ValueAnimator.ofInt(from,to);
        animator.setDuration(milliSeconds).setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value= (int) animation.getAnimatedValue();
                layout(originLeft,originTop+value,originRight,originBottom+value);
                postInvalidate();
            }
        });
        animator.start();
    }


}
