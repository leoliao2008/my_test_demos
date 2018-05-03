package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.skycaster.new_hacks.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

    private int mViewWidth;
    private int mViewHeight;
    private Camera mCamera;
    private Matrix mMatrix;
    private Paint mPaint;
    private Bitmap mBitmap;
    private BitmapFactory.Options mOptions;
    private int mCenterX;
    private int mCenterY;

    public MyCameraView1(Context context) {
        this(context,null);
    }

    public MyCameraView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCameraView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCamera = new Camera();
        mMatrix = new Matrix();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#ff4081"));

        mOptions = new BitmapFactory.Options();
        mOptions.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(context.getResources(), R.drawable.pic_03, mOptions);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = getLayoutParams();
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int defaultWidth=400;
        int defaultHeight=400;
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
        mOptions.inSampleSize=calculateInSampleSize(w,h);
        mOptions.inJustDecodeBounds=false;
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.pic_03, mOptions);
        mCenterX = mBitmap.getWidth() / 2;
        mCenterY = mBitmap.getHeight()/2;
    }

    private int calculateInSampleSize(int requestW, int requestH) {
        int result=1;
        int width=mOptions.outWidth;
        int height=mOptions.outHeight;
        while (width/result>requestW||height/result>requestH){
            result*=2;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mMatrix.preTranslate(mViewWidth/2-mCenterX,mViewHeight/2-mCenterY);
        mMatrix.preTranslate(-mViewWidth/2,-mViewHeight/2);
        mMatrix.postTranslate(mViewWidth/2,mViewHeight/2);
        canvas.drawCircle(mViewWidth/2,mViewHeight/2,60,mPaint);
        canvas.drawBitmap(mBitmap, mMatrix,mPaint);
    }

    public void rotateX(){
        rotate(0);
    }

    public void rotateY() {
        rotate(1);
    }

    public void rotateZ(){
        rotate(2);
    }

    private void rotate(final int axis){
        Observable.intervalRange(0,360,0,1,TimeUnit.MILLISECONDS,Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull Long integer) {
                        mCamera.save();
                        mMatrix.reset();
                        switch (axis){
                            case 0:
                                mCamera.rotateX(integer);
                                break;
                            case 1:
                                mCamera.rotateY(integer);
                                break;
                            default:
                                mCamera.rotateZ(integer);
                                break;
                        }
                        mCamera.getMatrix(mMatrix);
                        mCamera.restore();
                        invalidate();
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {


                    }
                });
    }


}
