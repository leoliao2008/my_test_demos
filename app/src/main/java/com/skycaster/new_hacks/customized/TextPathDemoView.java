package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 廖华凯 on 2018/5/3.
 */

public class TextPathDemoView extends View {
    private TextPaint mTextPaint;
    private Path mPath;
    private Matrix mMatrix;
    private PathMeasure mPathMeasure;
    private float mLength;
    private Path mCurrentPath;
    private Rect mRect;

    public TextPathDemoView(Context context) {
        this(context,null);
    }

    public TextPathDemoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextPathDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextPaint=new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(80);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setStrokeWidth(2);
        String s="可喜可贺";
        mPath=new Path();
        mCurrentPath=new Path();
        mTextPaint.getTextPath(s,0,s.length(),0,0,mPath);
        mRect = new Rect();
        mTextPaint.getTextBounds(s,0,s.length(), mRect);
        mPathMeasure = new PathMeasure(mPath,false);
        mLength = mPathMeasure.getLength();
        while (mPathMeasure.nextContour()){
            mLength += mPathMeasure.getLength();
        }
//        mPathMeasure.getSegment(0, mLength,mPath,false);
        mMatrix=new Matrix();
    }

    public void startAnimStrokeByStoke(){
        Observable.intervalRange(0,100,0,10, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        float progress = aLong * 1.f / 100;
                        float len = progress * mLength;
                        mPathMeasure.setPath(mPath,false);
                        mCurrentPath.reset();
                        while (len>mPathMeasure.getLength()){
                            len=len-mPathMeasure.getLength()+0.5f;
                            mPathMeasure.getSegment(0, mPathMeasure.getLength(),mCurrentPath,true);
                            if(!mPathMeasure.nextContour()){
                                break;
                            }
                        }
                        mPathMeasure.getSegment(0,mPathMeasure.getLength(),mCurrentPath,true);
                        invalidate();
                    }
                });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMatrix.preTranslate(-mRect.centerX(),-mRect.centerY());
        mMatrix.preTranslate(w>>1,h>>1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.concat(mMatrix);
        canvas.drawPath(mCurrentPath,mTextPaint);
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }

    public void startAnimAsync() {
        Observable.intervalRange(0,100,0,10,TimeUnit.MILLISECONDS,Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        float progress=aLong*1.f/100;
                        mPathMeasure.setPath(mPath,false);
                        mCurrentPath.reset();
                        while (mPathMeasure.nextContour()){
                            mPathMeasure.getSegment(0,mPathMeasure.getLength()*progress,mCurrentPath,true);
                        }
                        postInvalidate();
                    }
                });
    }
}
