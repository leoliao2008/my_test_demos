package com.skycaster.new_hacks.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.skycaster.new_hacks.R.id.menu_item_1;
import static com.skycaster.new_hacks.R.id.menu_item_2;
import static com.skycaster.new_hacks.R.id.menu_item_3;
import static com.skycaster.new_hacks.R.id.menu_item_4;

public class ActionBarDemo extends AppCompatActivity {

    @BindView(R.id.toggle_button_1)
    ToggleButton mToggleButton;

    public static void start(Context context) {
        Intent starter = new Intent(context, ActionBarDemo.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar_demo);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("ActionBar实验");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_type_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    public void toggleActionMode(View view) {
        startActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                getMenuInflater().inflate(R.menu.menu_type_2, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case menu_item_1:
                        showTost("item 1 is clicked.");
                        break;
                    case menu_item_2:
                        showTost("item 2 is clicked.");
                        break;
                    case menu_item_3:
                        showTost("item 3 is clicked.");
                        break;
                    case menu_item_4:
                        showTost("item 4 is clicked.");
                        break;
                    default:
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mToggleButton.setChecked(false);

            }
        });
    }

    public void showTost(String msg) {
        ToastUtil.toast(this, msg);
    }
}
