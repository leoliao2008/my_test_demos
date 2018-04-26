package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import static android.view.View.MeasureSpec.getSize;

/**
 * Created by 廖华凯 on 2018/4/25.
 */

public class RectAnimView extends View{
    private Paint mPaint;
    private float mOriginStrokeWidth=2;
    private float mViewWidth;
    private float mViewHeight;
    private float mOuterWidth;
    private float mDev;
    private double mSqrt;

    public RectAnimView(Context context) {
        this(context,null);
    }

    public RectAnimView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RectAnimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mOriginStrokeWidth);
        mPaint.setColor(Color.BLACK);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = getLayoutParams();
        int defaultWidth=400;
        int defaultHeight=400;
        int spcWidth = getSize(widthMeasureSpec);
        int spcHeight=MeasureSpec.getSize(heightMeasureSpec);
        if(params.width== ViewGroup.LayoutParams.WRAP_CONTENT&&params.height==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(defaultWidth,defaultHeight);
        }else if(params.width==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(defaultWidth,spcHeight);
        }else if(params.height==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(spcWidth,defaultHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;
        if(mViewWidth>mViewHeight){
            mOuterWidth=mViewWidth/2;
        }else {
            mOuterWidth=mViewHeight/2;
        }
        mSqrt = Math.sqrt(mOuterWidth * mOuterWidth * 2);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mViewWidth/2,mViewHeight/2);
        for(int i=0;i<=mSqrt;i+=10){
            float d = (float) Math.sqrt(Math.pow(i, 2) / 2)+mDev;
            canvas.drawRect(new RectF(-d,-d,d,d),mPaint);
        }
        mDev+=2;
        if(mDev==10){
            mDev=0;
        }
        postInvalidateDelayed(16);
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
