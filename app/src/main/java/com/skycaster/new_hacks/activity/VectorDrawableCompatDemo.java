package com.skycaster.new_hacks.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.skycaster.new_hacks.R;

public class VectorDrawableCompatDemo extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, VectorDrawableCompatDemo.class);
        context.startActivity(starter);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector_drawable_compat_demo);
    }
}
