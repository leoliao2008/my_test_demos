package com.skycaster.new_hacks.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.skycaster.new_hacks.R;

import java.util.ArrayList;

/**
 * Created by 廖华凯 on 2017/11/6.
 */

public class SnapShotAdapter extends RecyclerView.Adapter<SnapShotAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Bitmap> mList=new ArrayList<>();

    public SnapShotAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_snap_shot,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mImageView.setImageBitmap(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addSnapShot(Bitmap src) {
        Bitmap bitmap=Bitmap.createBitmap(src);
        mList.add(bitmap);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.item_snap_shot_iv_snap_shot);
        }
    }
}
