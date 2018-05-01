package com.skycaster.new_hacks.customized;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.skycaster.new_hacks.R;

/**
 * 创建者     $Author$
 * 创建时间   2018/5/1 21:54
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class MyCameraView1 extends View {

    private Paint mDefaultPaint;
    private Camera mCamera;
    private Bitmap mBitmap;
    private int mViewWidth;
    private int mViewHeight;

    public MyCameraView1(Context context) {
        this(context,null);
    }

    public MyCameraView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCameraView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDefaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_01);
        resizeBitmap(0.5f);

        mCamera = new Camera();
        mCamera.save();
    }

    private void resizeBitmap(float scale){
        Matrix matrix=new Matrix();
        matrix.setScale(scale,scale);
        mBitmap=Bitmap.createBitmap(mBitmap,0,0,mBitmap.getWidth(),mBitmap.getHeight(),matrix,true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = getLayoutParams();
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int defaultWidth=200;
        int defaultHeight=200;
        int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;
        if(params.width == wrapContent && params.height == wrapContent){
            setMeasuredDimension(defaultWidth,defaultHeight);
        }else if(params.width==wrapContent){
            setMeasuredDimension(defaultWidth,specHeight);
        }else if(params.height==wrapContent){
            setMeasuredDimension(specWidth,defaultHeight);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Matrix matrix=new Matrix();
        mCamera.getMatrix(matrix);
        canvas.drawBitmap(mBitmap,matrix,mDefaultPaint);
    }

    private void rota(final int axis){
        ValueAnimator animator=ValueAnimator.ofFloat(1,180);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                mCamera.restore();
                if(axis==0){
                    mCamera.rotateX(value);
                }else if(axis==1){
                    mCamera.rotateY(value);
                }else {
                    mCamera.rotateZ(value);
                }
                postInvalidate();
            }
        });
        animator.start();
//        Observable.range(1,180)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Integer integer) {
//                        mCamera.restore();
//                        if(axis==0){
//                            mCamera.rotateX(integer);
//                        }else if(axis==1){
//                            mCamera.rotateY(integer);
//                        }else {
//                            mCamera.rotateZ(integer);
//                        }
//                        Log.e(getClass().getName(),integer+"");
////                        invalidate();
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }
    public void rotaX(){
        rota(0);
    }
    public void rotaY(){
        rota(1);
    }
    public void rotaZ(){
        rota(2);
    }


}
