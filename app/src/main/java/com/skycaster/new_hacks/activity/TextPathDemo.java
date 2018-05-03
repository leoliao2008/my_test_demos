package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.customized.TextPathDemoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextPathDemo extends AppCompatActivity {

    @BindView(R.id.text_progress_view)
    TextPathDemoView mTextProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_path_demo);
        ButterKnife.bind(this);
    }

    public void startAnimStrokeByStroke(View view) {
        mTextProgressView.startAnimStrokeByStoke();
    }

    public void startAnimAsyncWriting(View view) {
        mTextProgressView.startAnimAsync();
    }
}
