package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 廖华凯 on 2018/5/3.
 */

public class RegionDemoView2 extends View {
    private Matrix mMatrix;
    private Paint  mPaint;
    private Path mPLeft;
    private Path mPTop;
    private Path mPRight;
    private Path mPBottom;
    private Path mPCenter;
    private Region mRegClip;
    private Region mRegLeft;
    private Region mRegTop;
    private Region mRegRight;
    private Region mRegBottom;
    private Region mRegCenter;
    private final int CENTER=1;
    private final int LEFT=2;
    private final int TOP=3;
    private final int RIGHT=4;
    private final int BOTTOM=5;
    private int mCurrentPressLocation=-1;
    private int mDx=-1;
    private int mDy=-1;

    public RegionDemoView2(Context context) {
        this(context,null);
    }

    public RegionDemoView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RegionDemoView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMatrix=new Matrix();

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#4D5266"));

        Path pInnerCircle = new Path();
        pInnerCircle.addCircle(0,0,90, Path.Direction.CW);
        Path pOuterCircle = new Path();
        pOuterCircle.addCircle(0,0,150, Path.Direction.CW);
        Path pOuterRing = new Path();
        //计算获得最外层的黑色圆环，待会把它分成四份，作为四个按钮
        pOuterRing.op(pInnerCircle, pOuterCircle, Path.Op.XOR);

        Path temp=new Path();
        mPLeft=new Path();
        mPTop=new Path();
        mPRight=new Path();
        mPBottom =new Path();
        mPCenter=new Path();


        RectF rectF=new RectF(-150,-150,150,150);
        temp.moveTo(0,0);
        temp.arcTo(rectF,50,80,false);
        temp.moveTo(0,0);
        //底部按钮
        mPBottom.op(pOuterRing,temp, Path.Op.INTERSECT);
        temp.reset();
        temp.moveTo(0,0);
        temp.arcTo(rectF,140,80,false);
        temp.moveTo(0,0);
        //左边按钮
        mPLeft.op(pOuterRing,temp, Path.Op.INTERSECT);
        temp.reset();
        temp.moveTo(0,0);
        temp.arcTo(rectF,230,80,false);
        temp.moveTo(0,0);
        //顶部按钮
        mPTop.op(pOuterRing,temp,Path.Op.INTERSECT);
        temp.reset();
        temp.moveTo(0,0);
        temp.arcTo(rectF,320,80,false);
        temp.moveTo(0,0);
        //右边按钮
        mPRight.op(pOuterRing,temp, Path.Op.INTERSECT);
        //中间按钮
        mPCenter.addCircle(0,0,60, Path.Direction.CW);

        mRegClip = new Region();
        mRegLeft = new Region();
        mRegTop = new Region();
        mRegRight = new Region();
        mRegBottom = new Region();
        mRegCenter = new Region();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mzW = MeasureSpec.getSize(widthMeasureSpec);
        int mzH = MeasureSpec.getSize(heightMeasureSpec);
        int dfW=400;
        int dfH=400;
        int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;
        ViewGroup.LayoutParams params = getLayoutParams();
        if(params.width==wrapContent&&params.height==wrapContent){
            setMeasuredDimension(dfW,dfH);
        }else if(params.width==wrapContent){
            setMeasuredDimension(dfW,mzH);
        }else if(params.height==wrapContent){
            setMeasuredDimension(mzW,dfH);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMatrix.preTranslate(w>>1,h>>1);
        mRegClip.set(-w,-h, w, h);
        //计算获得五个按钮的区域
        mRegCenter.setPath(mPCenter,mRegClip);
        mRegLeft.setPath(mPLeft,mRegClip);
        mRegTop.setPath(mPTop,mRegClip);
        mRegRight.setPath(mPRight,mRegClip);
        mRegBottom.setPath(mPBottom,mRegClip);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.concat(mMatrix);
        mPaint.setColor(Color.parseColor("#4D5266"));
        canvas.drawPath(mPCenter,mPaint);
        canvas.drawPath(mPBottom,mPaint);
        canvas.drawPath(mPLeft,mPaint);
        canvas.drawPath(mPTop,mPaint);
        canvas.drawPath(mPRight,mPaint);
        mPaint.setColor(Color.parseColor("#DE9D81"));
        Matrix matrix=new Matrix();
        if(canvas.getMatrix().invert(matrix)){
            float[] pos=new float[]{mDx,mDy};
            matrix.mapPoints(pos);
            mCurrentPressLocation=calculatePressLocation((int)pos[0],(int)pos[1]);
        }
        switch (mCurrentPressLocation){
            case CENTER:
                canvas.drawPath(mPCenter,mPaint);
                break;
            case LEFT:
                canvas.drawPath(mPLeft,mPaint);
                break;
            case TOP:
                canvas.drawPath(mPTop,mPaint);
                break;
            case RIGHT:
                canvas.drawPath(mPRight,mPaint);
                break;
            case BOTTOM:
                canvas.drawPath(mPBottom,mPaint);
                break;
            default:
                break;

        }
    }

    private int calculatePressLocation(int x,int y) {
        if(mRegCenter.contains(x,y)){
            return CENTER;
        }
        if(mRegLeft.contains(x,y)){
            return LEFT;
        }
        if(mRegTop.contains(x,y)){
            return TOP;
        }
        if(mRegRight.contains(x,y)){
            return RIGHT;
        }
        if(mRegBottom.contains(x,y)){
            return BOTTOM;
        }
        return -1;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDx = (int) event.getX();
                mDy = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDx=-1;
                mDy=-1;
                invalidate();
                break;
            default:
                break;
        }

        return true;
    }
}
