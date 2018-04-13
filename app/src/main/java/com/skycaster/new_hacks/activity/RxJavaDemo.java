package com.skycaster.new_hacks.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.skycaster.new_hacks.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * http://gank.io/post/560e15be2dca930e00da1083
 */
public class RxJavaDemo extends AppCompatActivity {

    @BindView(R.id.list_view)
    ListView mListView;
    private ArrayList<String> mList=new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private String[] mSource=new String[]{"aaa","bbb","ccc","ddd","eee","fff","ggg"};
//    private Observable<String> mObservable;
//    private Subscriber<String> mSubscriber;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_demo);
        ButterKnife.bind(this);
        initData();
        initViews();
        initListeners();
    }

    private void initData() {
        mAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                mList
        );
        mListView.setAdapter(mAdapter);
    }

    private void startDemo() {

//        mSubscriber = new Subscriber<String>() {
//            @Override
//            public void onStart() {
//                showLog("onStart");
//                super.onStart();
//                mList.clear();
//            }
//
//            @Override
//            public void onCompleted() {
//                showLog("onCompleted");
//                if(!mSubscriber.isUnsubscribed()){
//                    mSubscriber.unsubscribe();
//                }
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                throwable.printStackTrace();
//                showLog("onError :"+throwable.getMessage());
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                showLog("onNext: "+s);
//                mList.add(s);
//                mAdapter.notifyDataSetChanged();
//                mListView.smoothScrollToPosition(Integer.MAX_VALUE);
//            }
//        };
//
//        mObservable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                showLog("OnSubscribe---call()");
//                for(String s:mSource){
//                    subscriber.onNext(s);
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                mSubscriber.onCompleted();
//            }
//        });
//
//        mObservable.subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        mProgressDialog = new ProgressDialog(RxJavaDemo.this);
//                        mProgressDialog.setTitle("Please Wait...");
//                        mProgressDialog.setCancelable(false);
//                        mProgressDialog.show();
//                    }
//                })
//                .doOnUnsubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        if(mProgressDialog!=null&&mProgressDialog.isShowing()){
//                            mProgressDialog.dismiss();
//                        }
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(mSubscriber);

    }

    private void initViews() {

    }

    private void initListeners() {

    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }

    public void startDemo(View view) {
        startDemo();
    }
}
