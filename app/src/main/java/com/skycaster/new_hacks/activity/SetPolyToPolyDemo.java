package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.customized.PolyToPolyView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetPolyToPolyDemo extends AppCompatActivity {

    @BindView(R.id.custom_view)
    PolyToPolyView mCustomView;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_poly_to_poly_demo);
        ButterKnife.bind(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.btn0:
                        mCustomView.setCtrlPointsCnt(0);
                        break;
                    case R.id.btn1:
                        mCustomView.setCtrlPointsCnt(1);
                        break;
                    case R.id.btn2:
                        mCustomView.setCtrlPointsCnt(2);
                        break;
                    case R.id.btn3:
                        mCustomView.setCtrlPointsCnt(3);
                        break;
                    case R.id.btn4:
                        mCustomView.setCtrlPointsCnt(4);
                        break;
                }
            }
        });
    }
}
