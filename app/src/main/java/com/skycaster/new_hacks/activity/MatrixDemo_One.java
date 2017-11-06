package com.skycaster.new_hacks.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.skycaster.new_hacks.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatrixDemo_One extends AppCompatActivity {

    @BindView(R.id.matrix_demo_one_image_view)
    ImageView mImageView;
    private Paint mPaint;
    private Matrix mMatrix;
    private Bitmap mBitmapSrc;
    private Bitmap mBitmapDst;
    private Canvas mCanvas;
    private int w;
    private int h;

    public static void start(Context context) {
        Intent starter = new Intent(context, MatrixDemo_One.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_demo_one);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initData();
    }

    private void initData() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMatrix = new Matrix();
        BitmapDrawable bitmapDrawable= (BitmapDrawable) mImageView.getDrawable();
        if(bitmapDrawable!=null){
            mBitmapSrc = bitmapDrawable.getBitmap();
        }
        w =mBitmapSrc.getWidth();
        h =mBitmapSrc.getHeight();
        mBitmapDst = Bitmap.createBitmap(mBitmapSrc.getWidth(), mBitmapSrc.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmapDst);
    }

    public void scrollUp(View view) {
        ValueAnimator animator=ValueAnimator.ofInt(0, h);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float[] src=new float[]{0,0,0, h, w, h, w,0};
                int value = (int) animation.getAnimatedValue();
                float[] dst=new float[]{value,-value,0, h -value, w, h -value, w -value,-value};
                mMatrix.setPolyToPoly(src,0,dst,0,4);
                mCanvas.drawColor(Color.WHITE);
                mCanvas.setMatrix(mMatrix);
                mCanvas.drawBitmap(mBitmapSrc,0,0,mPaint);
                mImageView.setImageBitmap(mBitmapDst);
            }
        });
        animator.start();
    }

    public void scrollDown(View view) {
        ValueAnimator animator=ValueAnimator.ofInt(h,0);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float[] src=new float[]{0,0,0, h, w, h, w,0};
                int value = (int) animation.getAnimatedValue();
                float[] dst=new float[]{value,-value,0, h -value, w, h -value, w -value,-value};
                mMatrix.setPolyToPoly(src,0,dst,0,4);
                mCanvas.drawColor(Color.WHITE);
                mCanvas.setMatrix(mMatrix);
                mCanvas.drawBitmap(mBitmapSrc,0,0,mPaint);
                mImageView.setImageBitmap(mBitmapDst);
            }
        });
        animator.start();
    }

    public void scrollLeft(View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                float[] src=new float[]{0,0,0, h, w, h, w,0};
                float[] dst=new float[]{
                        -w/2*fraction,-h/5*fraction,
                        -w/2*fraction,h+h/5*fraction,
                        w-w/2*fraction, (float) (h-0.2*h*fraction),
                        w-w/2*fraction,h/5*fraction
                };
                mMatrix.setPolyToPoly(src,0,dst,0,4);
                mCanvas.drawColor(Color.WHITE);
                mCanvas.setMatrix(mMatrix);
                mCanvas.drawBitmap(mBitmapSrc,0,0,mPaint);
                mImageView.setImageBitmap(mBitmapDst);
            }
        });
        animator.start();
    }

    public void scrollRight(View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                float[] dst=new float[]{0,0,0, h, w, h, w,0};
                float[] src=new float[]{
                        -w/2*fraction,-h/5*fraction,
                        -w/2*fraction,h+h/5*fraction,
                        w-w/2*fraction, (float) (h-0.2*h*fraction),
                        w-w/2*fraction,h/5*fraction
                };
                mMatrix.setPolyToPoly(src,0,dst,0,4);
                mCanvas.drawColor(Color.WHITE);
                mCanvas.setMatrix(mMatrix);
                mCanvas.drawBitmap(mBitmapSrc,0,0,mPaint);
                mImageView.setImageBitmap(mBitmapDst);
            }
        });
        animator.start();
    }
}
