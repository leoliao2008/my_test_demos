package com.skycaster.new_hacks.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.skycaster.new_hacks.R;

public class DrawBitmapMessDemo extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, DrawBitmapMessDemo.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_bitmap_mess_demo);
    }
}
