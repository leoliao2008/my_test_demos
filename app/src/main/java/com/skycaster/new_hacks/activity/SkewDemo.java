package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.customized.SkewView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SkewDemo extends AppCompatActivity {


    @BindView(R.id.edt_input_x)
    EditText mEdtInputX;
    @BindView(R.id.edt_input_y)
    EditText mEdtInputY;
    @BindView(R.id.confirm)
    Button mConfirm;
    @BindView(R.id.skew_view)
    SkewView mSkewView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skew_demo);
        ButterKnife.bind(this);
        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = mEdtInputX.getText().toString();
                if(!TextUtils.isEmpty(x)){
                    mSkewView.setSkewX(Float.valueOf(x));
                }else {
                    mSkewView.setSkewX(0);
                }
                String y = mEdtInputY.getText().toString();
                if(!TextUtils.isEmpty(y)){
                    mSkewView.setSkewY(Float.valueOf(y));
                }else {
                    mSkewView.setSkewY(0);
                }
                mSkewView.invalidate();
            }
        });
    }
}
