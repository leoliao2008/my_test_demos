package com.skycaster.new_hacks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.skycaster.new_hacks.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 廖华凯 on 2017/8/28.
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_list_view)
    ListView mListView;
    private String[] mTitles=new String[]{
            "进程间通讯最新用法",
            "通知栏测试",
            "线程池最佳案例",
            "柱形图",
            "屏幕旋转及开关机",
            "字符串高级用法",
            "动态布局测试",
            "矢量图动画",
            "5.0新动画实验",
            "自定义详情图",
            "矩阵测试一",
            "脱衣服实验",
            "刮刮乐实验",
            "颜色矩阵实验1",
            "颜色矩阵实验2",
            "SurfaceView画板",
            "Action Bar实验",
            "矩阵测试二",
            "Tool Bar测试",
            "雨点效果测试",
            "Pending Intent 测试",
            "截图测试"

    };
    private Class[] mActivities=new Class[]{
            IpcTestActivity.class,
            NotificationDemo.class,
            ThreadPoolDemo.class,
            SnrChartViewDemo.class,
            ConfigChangeDemo.class,
            SpannableTextDemo.class,
            DynamicLayoutActivity.class,
            VectorDrawableDemo.class,
            VectorDrawableCompatDemo.class,
            ZmbleImageViewDemo.class,
            MatrixTestActivity.class,
            StripperDemo.class,
            ScratchViewDemo.class,
            ColorMatrixDemo_One.class,
            ColorMatrixDemo_Two.class,
            DrawingPadDemo.class,
            ActionBarDemo.class,
            MatrixDemo_One.class,
            ToolBarActivity.class,
            DrawBitmapMessDemo.class,
            PendingIntentActivity.class,
            DrawingCacheDemo.class
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        iniData();
    }

    private void iniData() {
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                mTitles
        ){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView tv= (TextView) super.getView(position, convertView, parent);
                tv.setGravity(Gravity.CENTER);
                return tv;
            }
        };
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Class activity = mActivities[i];
                Intent intent=new Intent(MainActivity.this,activity);
                startActivity(intent);

            }
        });
    }


}
