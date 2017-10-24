package com.skycaster.zoomableimageviewlib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;


/**
 * Created by 廖华凯 on 2017/10/12.
 */

public class ZoomableImageView extends android.support.v7.widget.AppCompatImageView {
    private Matrix mMatrix;
    private GestureDetectorCompat mGestureDetector;
    private float[] mScaleFactors=new float[]{0.25f,0.5f,1.f,1.5f,2.0f};//缩放比例级别
    private int mScaleIndex=2;//对应1.f倍的缩放比例
    private Rect mClipBound;
    private float translateX;
    private float translateY;

    public ZoomableImageView(Context context) {
        this(context,null);
    }

    public ZoomableImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZoomableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMatrix=new Matrix();
        mClipBound=new Rect();
        setScaleType(ScaleType.MATRIX);
        setImageMatrix(mMatrix);
        mGestureDetector = new GestureDetectorCompat(
                context,
                new GestureDetector.OnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onShowPress(MotionEvent e) {

                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return false;
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        translateX=-distanceX;
                        translateY=-distanceY;
                        mMatrix.postTranslate(translateX,translateY);
                        invalidate();
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {

                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        return false;
                    }
                }
        );
        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                zoomOut(e.getRawX(),e.getRawY());
                return true;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                zoomIn(e.getRawX(),e.getRawY());
                return true;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
    }


    /**
     * 以指定的坐标为中心拉伸图片
     * @param centerX 中心点x坐标
     * @param centerY 中心点y坐标
     */
    public void zoomIn(float centerX, float centerY) {
        zoom(centerX,centerY,ZoomDirection.IN);
    }

    /**
     * 以指定的坐标为中心缩小图片
     * @param centerX 中心点x坐标
     * @param centerY 中心点y坐标
     */
    public void zoomOut(float centerX,float centerY){
        zoom(centerX,centerY,ZoomDirection.OUT);
    }


    private void zoom(final float centerX, final float centerY, ZoomDirection direction){
        showLog("center x ="+centerX+", center y ="+centerY);
        if(mScaleIndex==0&&direction==ZoomDirection.OUT){
            return;
        }
        if(mScaleIndex==mScaleFactors.length-1&&direction==ZoomDirection.IN){
            return;
        }
        float preScaleFactor=mScaleFactors[mScaleIndex];
        switch (direction){
            case IN:
                mScaleIndex++;
                break;
            case OUT:
                mScaleIndex--;
                break;
        }
        final float newScaleFactor=mScaleFactors[mScaleIndex];
        ValueAnimator animator=ValueAnimator.ofFloat(preScaleFactor,newScaleFactor);
        animator.setDuration(100)
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                mMatrix.postScale(value,value,centerX,centerY);
                invalidate();
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setMatrix(mMatrix);
        super.onDraw(canvas);
        canvas.getClipBounds(mClipBound);
        showLog("clip bound width ="+mClipBound.width());
        showLog("bitmap width="+getDrawable().getBounds().width());
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }

    private enum ZoomDirection{
        IN,OUT
    }
}
