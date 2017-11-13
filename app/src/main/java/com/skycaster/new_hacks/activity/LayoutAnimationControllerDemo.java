package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;

import com.skycaster.new_hacks.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LayoutAnimationControllerDemo extends AppCompatActivity {

    @BindView(R.id.content_view)
    LinearLayout mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation_controller_demo);
        ButterKnife.bind(this);
    }

    private LayoutAnimationController getAnimCtrl(Animation anim){
        LayoutAnimationController controller=new LayoutAnimationController(anim,0.5f);
        return controller;
    }
}
