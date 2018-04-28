package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.skycaster.new_hacks.R;

import static android.graphics.BitmapFactory.decodeResource;

/**
 * Created by 廖华凯 on 2018/4/27.
 */

public class PolyToPolyView extends android.support.v7.widget.AppCompatImageView {
    private int mPointCnt;
    private int mViewWidth;
    private int mViewHeight;
    private Bitmap mBitmap;
    private Matrix mP2PMatrix;
    private Paint mPaintImage;
    private Paint mPaintPoint;
    private float[] mSrc;
    private float[] mDest;



    public PolyToPolyView(Context context) {
        this(context,null);
    }

    public PolyToPolyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PolyToPolyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mP2PMatrix =new Matrix();


        mPaintImage=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintImage.setColor(Color.BLUE);

        mPaintPoint=new Paint(mPaintImage);
        mPaintPoint.setStyle(Paint.Style.FILL);
        mPaintPoint.setColor(Color.GREEN);

        BitmapDrawable drawable= (BitmapDrawable) getDrawable();
        if(drawable!=null){
            mBitmap=drawable.getBitmap();
        }else {
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=2;
            mBitmap= decodeResource(context.getResources(), R.drawable.pic_01,options);
        }
        mSrc =new float[]{
                0,0,
                mBitmap.getWidth(),0,
                mBitmap.getWidth(),mBitmap.getHeight(),
                0,mBitmap.getHeight()
        };
        mDest=new float[8];
        System.arraycopy(mSrc,0,mDest,0,mSrc.length);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PolyToPolyView);
        mPointCnt=typedArray.getInt(R.styleable.PolyToPolyView_ctrlPointCnt,0);
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;
    }

    public void setCtrlPointsCnt(int count){
        if(mBitmap==null){
            return;
        }
        mPointCnt=count;
        polyToPoly();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, mP2PMatrix,mPaintImage);
        drawCtrlPoints(canvas);
    }

    private void drawCtrlPoints(Canvas canvas) {
        for(int i = 0; i< mPointCnt; i++){
            canvas.drawCircle(mDest[i*2],mDest[i*2+1],10,mPaintPoint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                for(int i=0;i<mPointCnt;i++){
                    if(Math.abs(event.getX()-mDest[i*2])<=100&&Math.abs(event.getY()-mDest[i*2+1])<=100){
                        mDest[i*2]=event.getX();
                        mDest[i*2+1]=event.getY();
                        polyToPoly();
                        invalidate();
                        break;
                    }
                }
                break;

        }
        return true;
    }

    private void polyToPoly() {
        mP2PMatrix.reset();
        mP2PMatrix.setPolyToPoly(mSrc,0,mDest,0,mPointCnt);
    }


}
