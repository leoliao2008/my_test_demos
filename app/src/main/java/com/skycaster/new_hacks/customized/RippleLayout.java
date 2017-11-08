package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by 廖华凯 on 2017/10/25.
 */

public class RippleLayout extends FrameLayout {

    private final int HORIZONTAL_BLOCKS_COUNT=20;
    private final int VERTICAL_BLOCKS_COUNT=20;
    private final int VERTICS_COUNT=(HORIZONTAL_BLOCKS_COUNT+1)*(VERTICAL_BLOCKS_COUNT+1)*2;
    /**
     * 网格顶点坐标的集合
     */
    private float mVerticsOrigin[]=new float[VERTICS_COUNT];
    /**
     * 启动波纹时的网格顶点坐标的集合
     */
    private float mVerticsRipple[] =new float[VERTICS_COUNT];
    /**
     * 从波纹中心到外环最边缘的的距离减去外环宽度的一半
     */
    private int mRippleRadius;
    /**
     * 波纹传播的速度
     */
    private int mRippleSpeed=16;
    /**
     * 外环的环宽
     */
    private int mRippleRingWidth=100;
    /**
     * 当前页面截图
     */
    private Bitmap mSnapShot;
    private boolean isRippling;
    private Paint mPaint;

    public RippleLayout(@NonNull Context context) {
        this(context,null);
    }

    public RippleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RippleLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startRipplesAt(event.getX(),event.getY());
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void startRipplesAt(final float x, final float y){
        if(isRippling){
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(initRipple()){
                    isRippling=true;
                    double distance = getDistance(0, 0, mSnapShot.getWidth(), mSnapShot.getHeight())+mRippleRingWidth/2;
                    for(mRippleRadius=0;mRippleRadius<distance;mRippleRadius+=mRippleSpeed){
                        findAndMorphAffectedVertics(x,y);
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    isRippling=false;
                }
            }
        }).start();
    }

    private void findAndMorphAffectedVertics(float x, float y) {
        for(int i=0;i<VERTICS_COUNT;i+=2){
            float fromX = mVerticsOrigin[i ];
            float fromY = mVerticsOrigin[i + 1];
            double distance = getDistance(fromX, fromY, x, y);
            if(distance>mRippleRadius-mRippleRingWidth/2&&distance<mRippleRadius+mRippleRingWidth/2){
                morphVertic(i,x,y,distance);
            }else {
                mVerticsRipple[i]=mVerticsOrigin[i];
                mVerticsRipple[i+1]=mVerticsOrigin[i+1];
            }
        }
        postInvalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if(isRippling){
            canvas.drawBitmapMesh(
                    mSnapShot,
                    HORIZONTAL_BLOCKS_COUNT,
                    VERTICAL_BLOCKS_COUNT,
                    mVerticsRipple,
                    0,
                    null,
                    0,
                    mPaint
                    );
        }else {
            super.dispatchDraw(canvas);
        }
    }

    /**
     * 执行指定的某个网格顶点的变形
     * @param index 该网格顶点的下标，从X坐标开始
     * @param centerX 水纹中心的x坐标
     * @param centerY 水纹中心的y坐标
     * @param distance 该顶点距离水纹中心的距离
     */
    private void morphVertic(int index, float centerX, float centerY, double distance) {
        float x = mVerticsOrigin[index];
        float y = mVerticsOrigin[index + 1];
        double rate = (distance - mRippleRadius) / (mRippleRingWidth / 2f);
        double offSetLength = Math.cos(rate) * 10f;
        double angle=Math.atan((y-centerY)/(x-centerX));
        double offSetX=offSetLength*Math.cos(angle);
        double offSetY=offSetLength*Math.sin(angle);
        if(distance>=mRippleRadius){
            //波峰外环
            if(x>centerX){
                x+=offSetX;
            }else {
                x-=offSetX;
            }
            if(y>centerY){
                y-=offSetY;
            }else {
                y+=offSetY;
            }
        }else {
            //波峰内环
            if(x>centerX){
                x-=offSetX;
            }else {
                x+=offSetX;
            }
            if(y>centerY){
                y+=offSetY;
            }else {
                y-=offSetY;
            }
        }
        mVerticsRipple[index]=x;
        mVerticsRipple[index+1]=y;

    }

    /**
     * 初始化水纹动画需要的基础数据。
     * @return 如果满足动画的初始条件，返回true，否则返回false。
     */
    private boolean initRipple() {
        mSnapShot=null;
        mSnapShot = getSnapShot();
        if(mSnapShot ==null){
            return false;
        }
        //填充顶点坐标：
        int index=0;
        for(int height=0;height<=VERTICAL_BLOCKS_COUNT;height++){
            //先相乘，再除，这样才能获得准确的坐标
            int y = mSnapShot.getHeight() * height / VERTICAL_BLOCKS_COUNT;
            for (int width=0;width<=HORIZONTAL_BLOCKS_COUNT;width++){
                mVerticsOrigin[index*2]=mVerticsRipple[index*2]=mSnapShot.getWidth() *width/ HORIZONTAL_BLOCKS_COUNT;
                mVerticsOrigin[index*2+1]=mVerticsRipple[index*2+1]= y;
                index++;
            }
        }
        return true;
    }

    /**
     * 获取屏幕截图
     * @return 返回一个屏幕截图的bitmap
     */
    private Bitmap getSnapShot(){
        Bitmap bitmap=null;
        setDrawingCacheEnabled(true);
        buildDrawingCache(true);
        Bitmap drawingCache = getDrawingCache();
        if(drawingCache!=null){
            bitmap=Bitmap.createBitmap(drawingCache);
        }
        setDrawingCacheEnabled(false);
        return bitmap;
    }

    private double getDistance(float fromX,float fromY,float toX,float toY){
        float dx = toX - fromX;
        float dy = toY - fromY;
        return Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
    }


}
