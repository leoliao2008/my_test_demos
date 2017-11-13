package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.skycaster.new_hacks.R;

/**
 * Created by 廖华凯 on 2017/11/8.
 */

public class WaveView extends View {
    private Bitmap mBitmapDst;
    private Paint mPaint;
    private Bitmap mBitmapSrc;
    private int HORIZONTAL_BLOCK_COUNT =20;
    private int VERTICAL_BLOCK_COUNT =20;
    private int WAVE_HEIGHT=50;
    private int GROUP_SIZE= (HORIZONTAL_BLOCK_COUNT +1)*(VERTICAL_BLOCK_COUNT +1)*2;
    private float[] mVerticsSrc=new float[GROUP_SIZE];
    private float[] mVerticsDst=new float[GROUP_SIZE];
    private double mOffSet;
    private double mSpeed =Math.PI/5;
    private boolean isAnimationBegin;
    private Runnable mRunnableAnimateWave = new Runnable() {
        @Override
        public void run() {
            startWaveAnimation();
        }
    };

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        int resourceId = array.getResourceId(R.styleable.WaveView_src, R.drawable.pic_01);
        mBitmapSrc = BitmapFactory.decodeResource(context.getResources(), resourceId);
        if(mBitmapSrc!=null){
            init();
        }
        array.recycle();
    }

    private void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmapDst=Bitmap.createBitmap(mBitmapSrc.getWidth(), mBitmapSrc.getHeight(), Bitmap.Config.ARGB_4444);
                Canvas canvas=new Canvas(mBitmapDst);
                mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
                canvas.drawBitmap(mBitmapSrc,0,0,mPaint);
                locateVertics();
                isAnimationBegin=true;
                startWaveAnimation();
            }
        }).start();
    }

    private void locateVertics() {
        int index=0;
        for(int height = 0; height<= VERTICAL_BLOCK_COUNT; height++){
            int y=mBitmapDst.getHeight()*height/ VERTICAL_BLOCK_COUNT;
            for(int width=0;width<=HORIZONTAL_BLOCK_COUNT;width++){
                int x = mBitmapDst.getWidth() * width / HORIZONTAL_BLOCK_COUNT;
                mVerticsSrc[index*2]=mVerticsDst[index*2]=x;
                mVerticsSrc[index*2+1]=mVerticsDst[index*2+1]=y;
                index++;
            }
        }
    }

    private void startWaveAnimation() {
        for(int i=0;i<GROUP_SIZE;i+=2){
            float y = mVerticsSrc[i + 1];
            y+=Math.sin(mOffSet)*WAVE_HEIGHT;
            mVerticsDst[i+1]=y;
            mOffSet+=mSpeed;
        }

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isAnimationBegin){
            canvas.drawBitmapMesh(mBitmapSrc,HORIZONTAL_BLOCK_COUNT,VERTICAL_BLOCK_COUNT,mVerticsDst,0,null,0,mPaint);
            postDelayed(mRunnableAnimateWave,50);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAnimationBegin=false;
    }
}
