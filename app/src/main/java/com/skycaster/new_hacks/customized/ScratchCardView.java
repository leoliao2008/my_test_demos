package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.skycaster.new_hacks.R;

/**
 * Created by 廖华凯 on 2017/10/24.
 */

public class ScratchCardView extends View {

    private Bitmap mBitMapScratch;
    private Paint mPaint;
    private Bitmap mBitMapBg;
    private Canvas mCanvas;
    private Paint mScratchPaint;
    private Path mPath;

    public ScratchCardView(Context context) {
        this(context,null);
    }

    public ScratchCardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScratchCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ScratchCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScratchCardView, defStyleAttr, defStyleRes);
        int srcBg=typedArray.getResourceId(R.styleable.ScratchCardView_bgSrc,-1);
        int srcFg=typedArray.getResourceId(R.styleable.ScratchCardView_fgSrc,-1);
        mBitMapBg = BitmapFactory.decodeResource(typedArray.getResources(), srcBg);
        Bitmap bitMapFg = BitmapFactory.decodeResource(typedArray.getResources(), srcFg);
        if(bitMapFg!=null){
            mBitMapScratch = Bitmap.createBitmap(bitMapFg.getWidth(), bitMapFg.getHeight(), bitMapFg.getConfig());
            mCanvas = new Canvas(mBitMapScratch);
            mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
            mScratchPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
            mScratchPaint.setAlpha(0);
            mScratchPaint.setStyle(Paint.Style.STROKE);
            mScratchPaint.setStrokeWidth(10);
            mScratchPaint.setStrokeJoin(Paint.Join.ROUND);
            mScratchPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            mPath=new Path();
            mCanvas.drawBitmap(bitMapFg,new Matrix(),mPaint);
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasure(widthMeasureSpec),getMeasure(heightMeasureSpec));
    }

    private int getMeasure(int spec) {
        int mode = MeasureSpec.getMode(spec);
        int size = MeasureSpec.getSize(spec);
        switch (mode){
            case MeasureSpec.AT_MOST://wrap content
                size=Math.min(400,size);
                break;
            case MeasureSpec.EXACTLY://match parent or exact size
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                break;
        }
        return size;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mPath!=null&&mCanvas!=null){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mPath.moveTo(event.getX(),event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    mPath.lineTo(event.getX(),event.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                default:
                    break;
            }
            mCanvas.drawPath(mPath,mScratchPaint);
            postInvalidate();
            return true;
        }else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mBitMapBg!=null){
            canvas.drawBitmap(mBitMapBg,0,0,mPaint);
        }
        if(mBitMapScratch!=null){
            canvas.drawBitmap(mBitMapScratch,0,0,mPaint);
        }
    }
}
