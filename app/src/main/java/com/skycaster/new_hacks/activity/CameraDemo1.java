package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.customized.MyCameraView1;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraDemo1 extends AppCompatActivity {

    @BindView(R.id.my_custom_view)
    MyCameraView1 mMyCustomView;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_demo1);
        ButterKnife.bind(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.x:
                        mMyCustomView.rotateX();
                        break;
                    case R.id.y:
                        mMyCustomView.rotateY();
                        break;
                    case R.id.z:
                        mMyCustomView.rotateZ();
                        break;
                }
            }
        });
    }
}
