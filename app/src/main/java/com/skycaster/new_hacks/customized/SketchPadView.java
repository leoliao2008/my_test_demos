package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 创建者     $Author$
 * 创建时间   2017/11/5 22:05
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class SketchPadView extends SurfaceView {

    private static final int FLAG_WIDTH_SPEC = 121;
    private static final int FLAG_HEIGHT_SPEC = 122;
    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Path mPath;
    private boolean mIsDrawing;
    private Thread mThread;
    private Canvas mSnapShotCanvas;
    private Bitmap mSnapShotBitmap;
    private boolean mIsClear;
    private PorterDuffXfermode mClearMode;
    private PorterDuffXfermode mSrcMode;


    public SketchPadView(Context context) {
        this(context,null);
    }

    public SketchPadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SketchPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getDimension(widthMeasureSpec, FLAG_WIDTH_SPEC);
        int height = getDimension(heightMeasureSpec, FLAG_HEIGHT_SPEC);
        mSnapShotBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        mSnapShotCanvas=new Canvas(mSnapShotBitmap);
        setMeasuredDimension(width, height);
    }

    private int getDimension(int measureSpec, int flag) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                if(flag==FLAG_WIDTH_SPEC){
                    size=Math.min(size,200);
                }else {
                    size=Math.min(size,400);
                }
                break;
            case MeasureSpec.EXACTLY:
            default://MeasureSpec.UNSPECIFIED

                break;
        }
        return size;
    }


    private void init() {

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath=new Path();
        mPath.setFillType(Path.FillType.WINDING);
        mClearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        mSrcMode = new PorterDuffXfermode(PorterDuff.Mode.SRC);
        mHolder = getHolder();
        mHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mIsDrawing = true;
                draw();

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                mIsDrawing=false;
                if(mThread!=null){
                    mThread.interrupt();
                }

            }
        });
    }

    private void draw() {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mIsDrawing) {
                    long timeStart = System.currentTimeMillis();
                    Canvas canvas = mHolder.lockCanvas();
                    if(mThread.isInterrupted()||canvas==null){
                        break;
                    }

                    //重要，这一步是要清除之前的轨迹，否则点击清除后还会保留之前轨迹。
                    mPaint.setXfermode(mClearMode);
                    canvas.drawPaint(mPaint);
                    if(mSnapShotCanvas!=null){
                        mSnapShotCanvas.drawPaint(mPaint);
                    }
                    mPaint.setXfermode(mSrcMode);



                    canvas.drawColor(Color.WHITE);
                    canvas.drawPath(mPath, mPaint);
                    if(mSnapShotCanvas!=null){
                        mSnapShotCanvas.drawPath(mPath,mPaint);
                    }
                    mHolder.unlockCanvasAndPost(canvas);
                    long timeEnd=System.currentTimeMillis();
                    long interval = timeEnd - timeStart;
                    if(interval<100){
                        try {
                            Thread.sleep(100-interval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        mThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(),event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(),event.getY());
                break;

            default:
                break;
        }
        return true;
    }

    public void clear(){
        mPath.reset();
    }

    public Bitmap getSnapShot(){
//        setDrawingCacheEnabled(true);
//        buildDrawingCache(true);
//        Bitmap drawingCache = getDrawingCache();
//        Bitmap bitmap = Bitmap.createBitmap(drawingCache);
//        setDrawingCacheEnabled(false);
        return mSnapShotBitmap;
    }

}
