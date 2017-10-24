package com.skycaster.new_hacks.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by 廖华凯 on 2017/10/16.
 */

public class SimpleAdapter extends BaseAdapter {
    private ArrayList<Integer>mList;
    private Context mContext;

    public SimpleAdapter(ArrayList<Integer> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=new TextView(mContext);
        }
        ((TextView)convertView).setText(String.format(Locale.CHINA,"%02d",mList.get(position)));
        return convertView;
    }
}
