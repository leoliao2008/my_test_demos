package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 廖华凯 on 2018/4/28.
 */

public class CameraView extends View {
    private int mViewWidth;
    private int mViewHeight;

    public CameraView(Context context) {
        this(context,null);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = getLayoutParams();
        ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width;
        params.height;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
