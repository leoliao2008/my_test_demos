package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.skycaster.new_hacks.R;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJava2Demo extends AppCompatActivity {

    @BindView(R.id.list_view)
    ListView mListView;
    private ArrayList<String> mList=new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java2_demo);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                mList
        );
        mListView.setAdapter(mAdapter);
    }

    public void startDemo(View view) {
//        startDemoUsingConsumerClass();
        testInterval();
    }

    private void startDemoUsingConsumerClass(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                int i=100;
                while (i<200){
                    i++;
                    e.onNext(i);
                    try {
                        Thread.sleep(500);
                    }catch (Exception err){
                        err.printStackTrace();
                        e.onComplete();
                        break;
                    }
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return String.valueOf(integer);
            }
        }).doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mList.add(s);
                mAdapter.notifyDataSetChanged();
                mListView.smoothScrollToPosition(Integer.MAX_VALUE);
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                disposeConsumer();
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                disposeConsumer();
                mDisposable=disposable;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        }, new Action() {
            @Override
            public void run() throws Exception {

            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
            }
        });

    }

    private void disposeConsumer(){
        if(mDisposable!=null&&!mDisposable.isDisposed()){
            mDisposable.dispose();
        }
    }

    private void testInterval(){
        Flowable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).map(new Function<Long, String>() {
            @Override
            public String apply(@NonNull Long aLong) throws Exception {
                return String.valueOf(aLong);
            }
        }).doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) throws Exception {
                mList.clear();
                mAdapter.notifyDataSetChanged();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mList.add(s);
                mAdapter.notifyDataSetChanged();
                mListView.smoothScrollToPosition(Integer.MAX_VALUE);
            }
        });
    }
}
