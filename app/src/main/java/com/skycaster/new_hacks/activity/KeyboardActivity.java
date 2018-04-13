package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.util.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KeyboardActivity extends AppCompatActivity {


    @BindView(R.id.edt)
    EditText mEdt;
    private boolean isToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        ButterKnife.bind(this);

        mEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isToShow=!isToShow;
                if(isToShow){
                    KeyboardUtils.showKeyBoard(KeyboardActivity.this,mEdt);
                }else {
                    KeyboardUtils.dismissKeyboardView();
                }

            }
        });

    }
}
