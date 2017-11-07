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
    private float mVerticsOrigin[]=new float[VERTICS_COUNT];
    private float mVerticsRipple[] =new float[VERTICS_COUNT];
    private int mRippleRadius;
    private int mRippleSpeed=16;
    private int mRippleRingWidth=100;
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
                    int v = (int) (distance / mRippleSpeed+0.5f);
                    for(mRippleRadius=0;mRippleRadius<v;mRippleRadius+=mRippleSpeed){
                        findAffectedVertics(x,y);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    isRippling=false;
                }
            }
        }).start();
    }

    private void findAffectedVertics(float x, float y) {
        for(int i=0;i<VERTICS_COUNT/2;i++){
            float fromX = mVerticsOrigin[i * 2];
            float fromY = mVerticsOrigin[i * 2 + 1];
            double distance = getDistance(fromX, fromY, x, y);
            if(distance>=mRippleRadius-mRippleRingWidth/2&&distance<=mRippleRadius+mRippleRingWidth/2){
                morphAffectedVertics(i*2,x,y,distance);
            }else {
                mVerticsRipple[i*2]=mVerticsOrigin[i*2];
                mVerticsRipple[i*2+1]=mVerticsOrigin[i*2+1];
            }
            postInvalidate();
        }
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
    private void morphAffectedVertics(int index, float centerX, float centerY, double distance) {
        float x = mVerticsRipple[index];
        float y = mVerticsRipple[index + 1];
        double rate = (distance - mRippleRadius) / (mRippleRingWidth / 2);
        double offSetLength = Math.cos(rate) * 10;
        double angle=Math.atan((y-centerY)/(x-centerX));
        double offSetX=Math.cos(angle)*offSetLength;
        double offSetY=Math.sin(angle)*offSetLength;
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
        int blockWidth= mSnapShot.getWidth() / HORIZONTAL_BLOCKS_COUNT;
        int blockHeight= mSnapShot.getHeight()/VERTICAL_BLOCKS_COUNT;
        //填充坐标
//        //第一行
//        for(int i=0;i<HORIZONTAL_BLOCKS_COUNT+1;i++){
//            int index=(i+(HORIZONTAL_BLOCKS_COUNT+1)*0)*2;
//            mVerticsOrigin[index]=blockWidth*i;
//            mVerticsOrigin[index+1]=0;
//        }
//        //第二行
//        for(int i=0;i<HORIZONTAL_BLOCKS_COUNT+1;i++){
//            int index=(i+(HORIZONTAL_BLOCKS_COUNT+1)*1)*2;
//            mVerticsOrigin[index]=blockWidth*i;
//            mVerticsOrigin[index+1]=blockHeight*1;
//        }
//        //第三行
//        for(int i=0;i<HORIZONTAL_BLOCKS_COUNT+1;i++){
//            int index=(i+(HORIZONTAL_BLOCKS_COUNT+1)*2)*2;
//            mVerticsOrigin[index]=blockWidth*i;
//            mVerticsOrigin[index+1]=blockHeight*2;
//        }
//        //......
        //由上面可以推导以下填充逻辑：
        for(int line=0;line<VERTICAL_BLOCKS_COUNT+1;line++){
            for (int i=0;i<HORIZONTAL_BLOCKS_COUNT+1;i++){
                int index=(i+(HORIZONTAL_BLOCKS_COUNT+1)*line)*2;
                mVerticsOrigin[index]=mVerticsRipple[index]=blockWidth*i;
                mVerticsOrigin[index+1]=mVerticsRipple[index+1]=blockHeight*line;
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
