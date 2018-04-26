package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 廖华凯 on 2018/4/25.
 */

public class SkewView extends View {
    private int mViewWidth;
    private int mViewHeight;
    private float mskX;
    private float mskY;
    private RectF mRectF;
    private Paint mPaint;

    public SkewView(Context context) {
        this(context,null);
    }

    public SkewView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SkewView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mRectF=new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = getLayoutParams();
        int defaultWidth=400;
        int defaultHeight=400;
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight= MeasureSpec.getSize(heightMeasureSpec);
        if(params.width== ViewGroup.LayoutParams.WRAP_CONTENT&&params.height==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(defaultWidth,defaultHeight);
        }else if(params.width==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(defaultWidth,specHeight);
        }else if(params.height==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(specWidth,defaultHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;
        mRectF.set(-w/4,-h/4,w/4,h/4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mViewWidth/2,mViewHeight/2);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(mRectF,mPaint);
        canvas.skew(mskX,mskY);
        mPaint.setColor(Color.RED);
        canvas.drawRect(mRectF,mPaint);
    }

    public void setSkewX(float skewX){
        mskX=skewX;
    }

    public void setSkewY(float skewY){
        mskY=skewY;
    }
}
