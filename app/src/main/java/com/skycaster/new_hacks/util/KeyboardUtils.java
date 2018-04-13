package com.skycaster.new_hacks.util;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.skycaster.new_hacks.R;

/**
 * Created by 廖华凯 on 2018/4/2.
 * 参考代码 https://blog.csdn.net/tianzhaoai/article/details/64130332
 */

public class KeyboardUtils {

    private static KeyboardView keyboardView;
    private static ViewGroup rootView;

    public static void showKeyBoard(Activity activity, EditText editText){
        rootView = activity.findViewById(android.R.id.content);
        keyboardView= (KeyboardView) View.inflate(activity,R.layout.key_board_utils,null);
        Keyboard keyboard=new Keyboard(activity, R.xml.key_board_numbers);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {

            }

            @Override
            public void onRelease(int primaryCode) {

            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {

            }

            @Override
            public void onText(CharSequence text) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        });
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
        rootView.addView(keyboardView,params);
    }

    public static void dismissKeyboardView(){
        rootView.removeView(keyboardView);
    }
}
