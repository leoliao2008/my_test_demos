package com.skycaster.new_hacks.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.skycaster.new_hacks.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StripperDemo extends AppCompatActivity {

    @BindView(R.id.stripper_iv_after)
    ImageView iv_after;
    @BindView(R.id.stripper_iv_before)
    ImageView iv_before;
    private Bitmap mUpdateBitMap;

    public static void start(Context context) {
        Intent starter = new Intent(context, StripperDemo.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripper_demo);
        ButterKnife.bind(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic_01);
        mUpdateBitMap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        Canvas canvas=new Canvas(mUpdateBitMap);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        canvas.drawBitmap(bitmap,new Matrix(),paint);
        iv_before.setImageBitmap(mUpdateBitMap);
        iv_before.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        mUpdateBitMap.setPixel((int) event.getX(), (int) event.getY(),
                                Color.TRANSPARENT);
                        iv_before.setImageBitmap(mUpdateBitMap);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }

}
