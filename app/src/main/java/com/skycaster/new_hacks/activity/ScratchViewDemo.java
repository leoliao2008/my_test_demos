package com.skycaster.new_hacks.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.skycaster.new_hacks.R;

public class ScratchViewDemo extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ScratchViewDemo.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_view_demo);
    }
}
