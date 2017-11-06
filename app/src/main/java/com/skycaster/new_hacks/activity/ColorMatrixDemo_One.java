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
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.skycaster.new_hacks.R;

public class ColorMatrixDemo_One extends AppCompatActivity {

    private ImageView mImageView;
    private GridLayout mGridLayout;
    private float[] mColorMatrix =new float[20];
    private Paint mPaint;
    private Bitmap mBitmapSrc;
    private Bitmap mBitMapDst;
    private Canvas mCanvas;

    public static void start(Context context) {
        Intent starter = new Intent(context, ColorMatrixDemo_One.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix_demo_one);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mImageView= (ImageView) findViewById(R.id.color_matrix_demo_image_view);
        mGridLayout= (GridLayout) findViewById(R.id.color_matrix_demo_grid_layout);
    }

    private void initData() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        BitmapDrawable bitmapDrawable= (BitmapDrawable) mImageView.getDrawable();
        mBitmapSrc = bitmapDrawable.getBitmap();
        mBitMapDst = Bitmap.createBitmap(mBitmapSrc.getWidth(), mBitmapSrc.getHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitMapDst);
        mGridLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                for(int i=0;i<20;i++){
                    EditText edt=new EditText(ColorMatrixDemo_One.this);
                    GridLayout.LayoutParams params=new GridLayout.LayoutParams();
                    params.width=mGridLayout.getWidth()/5;
                    params.height=mGridLayout.getHeight()/4;
                    edt.setLayoutParams(params);
                    edt.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    edt.setGravity(Gravity.CENTER);
                    edt.setText(i%6==0?"1":"0");
                    mGridLayout.addView(edt);
                }
            }
        });
    }

    private void initListener() {

    }

    public void applyChange(View view) {
        for(int i=0;i<20;i++){
            EditText temp = (EditText) mGridLayout.getChildAt(i);
            String input = temp.getText().toString();
            if(TextUtils.isEmpty(input)){
                mColorMatrix[i]=0;
            }else {
                try {
                    mColorMatrix[i]=Float.valueOf(input.trim());
                }catch (NumberFormatException e){
                    mColorMatrix[i]=0;
                }

            }
        }
        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        mCanvas.drawBitmap(mBitmapSrc,0,0,mPaint);
        mImageView.setImageBitmap(mBitMapDst);
    }

    public void resetMatrix(View view) {
        mPaint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix()));
        mCanvas.drawBitmap(mBitmapSrc,0,0,mPaint);
        mImageView.setImageBitmap(mBitMapDst);
    }
}
