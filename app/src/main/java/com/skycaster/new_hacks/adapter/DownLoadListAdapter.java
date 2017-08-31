package com.skycaster.new_hacks.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skycaster.new_hacks.R;
import com.skycaster.new_hacks.bean.DownLoadBean;
import com.skycaster.new_hacks.bean.DownLoadTask;
import com.skycaster.new_hacks.data.StaticData;

import java.util.ArrayList;

/**
 * Created by 廖华凯 on 2017/8/29.
 */

public class DownLoadListAdapter extends BaseAdapter {
    private ArrayList<DownLoadBean> mList;
    private Context mContext;
    private CallBack mCallBack;
    private Handler mHandler;

    public DownLoadListAdapter(ArrayList<DownLoadBean> list, Context context) {
        mList = list;
        mContext = context;
        mCallBack=new CallBack();
        mHandler=new Handler(Looper.getMainLooper());
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder vh;
        if(convertView==null){
            convertView=View.inflate(mContext,R.layout.item_down_load,null);
            vh=new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        final DownLoadBean bean = mList.get(position);
        bean.setCallBack(mCallBack);
        vh.title.setText(bean.getTitle());
        vh.progress.setProgress(bean.getProgress());
        switch (bean.getState()){
            case StaticData.DOWNLOAD_STATE_DOWNLOADING:
                vh.getAction().setText("暂停");
                break;
            case StaticData.DOWNLOAD_STATE_FAILED:
                vh.getAction().setText("重试");
                break;
            case StaticData.DOWNLOAD_STATE_PAUSE:
                vh.getAction().setText("继续");
                break;
            case StaticData.DOWNLOAD_STATE_FINISHED:
                vh.getAction().setText("完成");
                break;
            case StaticData.DONWLOAD_STATE_PENDING:
                vh.getAction().setText("等待开始");
                break;
            case StaticData.DOWNLOAD_STATE_DEFAULT:
            default:
                vh.getAction().setText("开始");
                break;
        }
        final DownLoadTask downLoadTask=new DownLoadTask(bean);
        vh.getAction().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadTask.handleActionByState(bean.getState());
            }
        });
        return convertView;
    }

    public class CallBack {
        public void onProgressUpdate(int progress){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    DownLoadListAdapter.this.notifyDataSetChanged();
                }
            });

        }
        public void onPause(){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    DownLoadListAdapter.this.notifyDataSetChanged();
                }
            });

        }
        public void onComplete(){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    DownLoadListAdapter.this.notifyDataSetChanged();
                }
            });

        }
        public void onFail(){
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    DownLoadListAdapter.this.notifyDataSetChanged();
                }
            });

        }

        public void onPending() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    DownLoadListAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }

    private class ViewHolder {
        private TextView title;
        private ProgressBar progress;
        private Button action;

        public ViewHolder(View convertView) {
            this.title = convertView.findViewById(R.id.down_load_item_title);
            this.progress = convertView.findViewById(R.id.down_load_item_progress);
            this.action = convertView.findViewById(R.id.down_load_item_action);
        }

        public TextView getTitle() {
            return title;
        }

        public ProgressBar getProgress() {
            return progress;
        }

        public Button getAction() {
            return action;
        }
    }
}
