package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.customized.GetTangeView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetTangeDemo extends AppCompatActivity {

    @BindView(R.id.tange_view)
    GetTangeView mTangeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_tange_demo);
        ButterKnife.bind(this);

    }

    public void startAnim(View view) {
        mTangeView.animCircling();
    }

    public void stopAnim(View view) {
        mTangeView.stopAnim();
    }
}
