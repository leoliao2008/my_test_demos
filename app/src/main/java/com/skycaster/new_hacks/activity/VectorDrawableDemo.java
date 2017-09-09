package com.skycaster.new_hacks.activity;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.skycaster.new_hacks.R;

public class VectorDrawableDemo extends AppCompatActivity {
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable_demo);
        mImageView1 = (ImageView) findViewById(R.id.vector_drawable_iv_1);
        Drawable drawable = mImageView1.getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }

        mImageView2= (ImageView) findViewById(R.id.vector_drawable_iv_2);
        mImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable1 = mImageView2.getDrawable();
                if(drawable1 instanceof Animatable){
                    ((Animatable) drawable1).start();
                }
            }
        });
        mImageView3= (ImageView) findViewById(R.id.vector_drawable_iv_3);
        mImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable1 = mImageView3.getDrawable();
                if(drawable1 instanceof Animatable){
                    ((Animatable) drawable1).start();
                }
            }
        });

    }
}
