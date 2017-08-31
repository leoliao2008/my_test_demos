package com.skycaster.new_hacks.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.skycaster.new_hacks.R;

/**
 * Created by 廖华凯 on 2017/8/24.
 */

public class ToastUtil {

    public static void showCustomToast(Context context,String msg){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toast, null);
        TextView tv=view.findViewById(R.id.custom_toast_text);
        tv.setText(msg);
        Toast toast=new Toast(context);
        toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public static void toast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
