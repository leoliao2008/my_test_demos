package com.skycaster.new_hacks.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.customized.ViewTransformer;

public class MatrixTestActivity extends AppCompatActivity {
    private ViewTransformer mViewTransformer;

    public static void start(Context context) {
        Intent starter = new Intent(context, MatrixTestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix_test);
        mViewTransformer= (ViewTransformer) findViewById(R.id.matrix_test_view_transformer);
    }

    public void startSkew(View view) {
        mViewTransformer.startSkewAnimation();
    }

    public void stopSkew(View view) {
        mViewTransformer.stopSkewAnimation();
    }
}
