package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 廖华凯 on 2018/4/24.
 */

public class CloudAndLeafView extends View {
    private float mScaleW, mScaleH;
    private int mWidth, mHeight;
    private Paint mPaint;
    private int mCenterX, mCenterY;

    public CloudAndLeafView(Context context) {
        this(context,null);
    }

    public CloudAndLeafView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CloudAndLeafView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(60);
        mPaint.setPathEffect(new CornerPathEffect(4));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        mScaleW = w / 120;
        mScaleH = h / 60;
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w - 80;
        mCenterY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(5.82f * mScaleW, 51.78f * mScaleH);
        path.quadTo(24 * mScaleW, 28 * mScaleH, 51.2f * mScaleW, 34.4f * mScaleH);
        path.cubicTo(51f * mScaleW, 14 * mScaleH, 113 * mScaleW, -3 * mScaleH, mWidth, 51.78f * mScaleH);
        path.lineTo(5.82f * mScaleW, 51.78f * mScaleH);
        canvas.drawPath(path, mPaint);

    }
}
