package com.skycaster.new_hacks.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.skycaster.new_hacks.R;

public class ToolBarActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    public static void start(Context context) {
        Intent starter = new Intent(context, ToolBarActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);
    }

//    private void initToolBar() {
//        mToolbar = new Toolbar(this);
//        mToolbar.setTitle("This is the title of toolbar");
//        mToolbar.setTitleTextColor(Color.WHITE);
//        mToolbar.setSubtitleTextColor(Color.WHITE);
//        mToolbar.setBackgroundColor(getColor(R.color.colorPrimary));
//        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        addContentView(mToolbar,layoutParams);
//        setSupportActionBar(mToolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                onBackPressed();
//                break;
//        }
//        return true;
//    }
}
