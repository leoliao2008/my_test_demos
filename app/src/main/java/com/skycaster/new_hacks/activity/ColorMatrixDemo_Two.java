package com.skycaster.new_hacks.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.skycaster.new_hacks.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorMatrixDemo_Two extends AppCompatActivity {


    @BindView(R.id.color_matrix_demo_image_view)
    ImageView mImageView;
    @BindView(R.id.color_matrix_demo_seek_bar_rotate)
    SeekBar mSeekbarRotate;
    @BindView(R.id.color_matrix_demo_seek_bar_saturation)
    SeekBar mSeekbarSaturation;
    @BindView(R.id.color_matrix_demo_seek_bar_lum)
    SeekBar mSeekbarLum;
    private Bitmap mBitmapDst;
    private Paint mPaint;
    private ColorMatrix mColorMatrix;
    private float mRotate=0.0f;
    private float mSaturation=1.f;
    private float mLum=1.f;
    private Canvas mCanvas;
    private Bitmap mBitmapSrc;

    public static void start(Context context) {
        Intent starter = new Intent(context, ColorMatrixDemo_Two.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix_demo__two);
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    private void initView() {

    }

    private void initData() {
        BitmapDrawable drawable= (BitmapDrawable) mImageView.getDrawable();
        mBitmapSrc = drawable.getBitmap();
        mBitmapDst = Bitmap.createBitmap(mBitmapSrc.getWidth(), mBitmapSrc.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmapDst);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorMatrix=new ColorMatrix();
    }

    private void initListener() {
        mSeekbarRotate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mRotate = progress*1.0f/mSeekbarRotate.getMax();
                handleColorTransformation(mRotate,mSaturation,mLum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekbarSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mSaturation = progress*1.0f/mSeekbarSaturation.getMax();
                handleColorTransformation(mRotate,mSaturation,mLum);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekbarLum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mLum = progress*1.0f/mSeekbarLum.getMax();
                handleColorTransformation(mRotate,mSaturation,mLum);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void handleColorTransformation(float rotate, float saturation, float lum) {
        Log.e(getClass().getSimpleName(),"rotate ="+rotate+" saturation = "+saturation+" lum = "+lum);
        ColorMatrix rotateMatrix=new ColorMatrix();
        rotateMatrix.setRotate(0,rotate*180);
        rotateMatrix.setRotate(1,rotate*180);
        rotateMatrix.setRotate(2,rotate*180);

        ColorMatrix saturationMatrix=new ColorMatrix();
        saturationMatrix.setSaturation(saturation+1);

        ColorMatrix lumMatrix=new ColorMatrix();
        lumMatrix.setScale(lum+1,lum+1,lum+1,1);

        mColorMatrix.set(rotateMatrix);
        mColorMatrix.postConcat(saturationMatrix);
        mColorMatrix.postConcat(lumMatrix);

        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        mCanvas.drawBitmap(mBitmapSrc,0,0,mPaint);
        mImageView.setImageBitmap(mBitmapDst);
    }
}
