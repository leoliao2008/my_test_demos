package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.skycaster.new_hacks.R;

import static android.view.View.MeasureSpec.getSize;

/**
 * Created by 廖华凯 on 2018/4/24.
 */

public class PathDemoView extends View {
    private Paint mPaint;
    private Path mPath;
    private int mViewWidth;
    private int mViewHeight;
    private PathMeasure mPathMeasure;
    private float mCurrentValue;
    private float mPahtLength;
    private float [] mPos=new float[2];
    private float [] mTan=new float[2];
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private int mDevX;
    private int mDevY;

    public PathDemoView(Context context) {
        this(context,null);
    }

    public PathDemoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(2);

        mPath=new Path();
        mPathMeasure=new PathMeasure();
        mBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_directions_bike_black_18dp);
        mDevX = mBitmap.getWidth() / 2;
        mDevY = mBitmap.getHeight() / 2;

        mMatrix=new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specWidth = getSize(widthMeasureSpec);
        int spceHeight= MeasureSpec.getSize(heightMeasureSpec);
        int defaultWidth=400;
        int defaultHeight=400;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if(layoutParams.width== ViewGroup.LayoutParams.WRAP_CONTENT&&layoutParams.height== ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(defaultWidth,defaultHeight);
        }else if(layoutParams.width==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(defaultWidth,spceHeight);
        }else if(layoutParams.height==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(specWidth,defaultHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;
        int i=w>h?h:w;
        mPath.reset();
        mPath.addCircle(0,0,i/3, Path.Direction.CW);
        mPathMeasure.setPath(mPath,false);
        mPahtLength=mPathMeasure.getLength();
        showLog("width="+mViewWidth+" height="+mViewHeight+" pathLen="+mPahtLength);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mViewWidth/2,mViewHeight/2);
        mCurrentValue+=0.005;
        if(mCurrentValue>=1){
            mCurrentValue=mCurrentValue-1;
        }
        mPathMeasure.getPosTan(mCurrentValue*mPahtLength,mPos,mTan);
        canvas.drawPath(mPath,mPaint);
        mMatrix.reset();
        float degree = (float) (Math.atan2(mTan[1], mTan[0]) * 180 / Math.PI);
        mMatrix.postRotate(degree,mDevX,mDevY);
        mMatrix.postTranslate(mPos[0]-mDevX,mPos[1]-mDevY);
        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
        invalidate();
    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
