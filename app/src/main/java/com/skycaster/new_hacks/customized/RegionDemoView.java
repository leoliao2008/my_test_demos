package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.skycaster.new_hacks.util.ToastUtil;

/**
 * Created by 廖华凯 on 2018/5/2.
 */

public class RegionDemoView extends View {
    private Path mCirclePath;
    private int mViewHeight;
    private int mViewWidth;
    private Paint mPaint;
    private Region mCircleRegion;
    private float mDownX=-1;
    private float mDownY=-1;

    public RegionDemoView(Context context) {
        this(context,null);
    }

    public RegionDemoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RegionDemoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCirclePath=new Path();
        mCircleRegion=new Region();
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int defaultWidth=200;
        int defaultHeight=200;
        ViewGroup.LayoutParams params = getLayoutParams();
        int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;
        if(params.width==wrapContent&&params.height==wrapContent){
            setMeasuredDimension(defaultWidth,defaultHeight);
        }else if(params.width==wrapContent){
            setMeasuredDimension(defaultWidth,height);
        }else if(params.height==wrapContent){
            setMeasuredDimension(width,defaultHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;
        mCirclePath.addCircle(mViewWidth>>1,mViewHeight>>1,90, Path.Direction.CW);
        Region region=new Region(0,0,mViewWidth,mViewHeight);
        mCircleRegion.setPath(mCirclePath,region);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path circle=mCirclePath;
        canvas.drawPath(circle,mPaint);


        canvas.translate(mViewWidth>>1,mViewHeight>>1);
        if(mDownX!=-1&&mDownY!=-1){
            float[] pos = {mDownX, mDownY};
            Matrix matrix=new Matrix();
            canvas.getMatrix().invert(matrix);
            matrix.mapPoints(pos);
            canvas.drawCircle(pos[0],pos[1],10,mPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(mCircleRegion.contains((int)(event.getX()), (int)(event.getY()))){
                    ToastUtil.toast(getContext(),"圆圈被点击了。");
                }else {
                    drawCircle(event.getX(),event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                drawCircle(-1,-1);
                break;
            default:
                break;
        }
        return true;
    }

    private void drawCircle(float x, float y) {
        mDownX = x;
        mDownY = y;
        invalidate();
    }
}
