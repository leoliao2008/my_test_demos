package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.skycaster.new_hacks.R;

/**
 * Created by 廖华凯 on 2017/10/17.
 */

public class ViewTransformer extends View {
    private Matrix mMatrix;
    private Bitmap mBitmap;
    private Paint mPaint;
    private boolean isSkewing;
    private float mSkewX;
    private float mSkewY;

    public ViewTransformer(Context context) {
        this(context,null);
    }

    public ViewTransformer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ViewTransformer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMatrix=new Matrix();
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.pic_01)).getBitmap();
        mMatrix.setScale(400f/ mBitmap.getWidth(),400f/ mBitmap.getHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(getLengthBySpec(widthMeasureSpec,0),getLengthBySpec(heightMeasureSpec,1));
    }

    public void startSkewAnimation(){
        isSkewing=true;
        postInvalidate();
    }

    public void stopSkewAnimation(){
        isSkewing=false;
    }

    /**
     *
     * @param spec
     * @param flag 0:width 1:height
     * @return
     */
    private int getLengthBySpec(int spec,int flag) {
        int length;
        int mode = MeasureSpec.getMode(spec);
        int exactSize = MeasureSpec.getSize(spec);
        switch (mode){
            case MeasureSpec.AT_MOST://wrap content
                length= Math.min(400,exactSize);
                break;
            case MeasureSpec.EXACTLY://match parent
                length=exactSize;
                break;
            default:
                length=400;
                break;
        }

        return length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap,mMatrix,mPaint);
        if(isSkewing){
            mSkewX+=0.01f;
            mSkewY+=0.02f;
            if(mSkewX==1f){
                mSkewX=0;
            }
            if(mSkewY==1f){
                mSkewY=0;
            }
            mMatrix.setSkew(mSkewX,mSkewY);
            postInvalidateDelayed(100);
        }
    }
}
