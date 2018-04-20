package com.skycaster.new_hacks.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.skycaster.new_hacks.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
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
//        testInterval();
//        testConcatMap();
//        testZip();
        testFlowable();
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
        Flowable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(@NonNull Long aLong) throws Exception {
                        return String.valueOf(aLong);
                    }
                })
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mList.add(s);
                        mAdapter.notifyDataSetChanged();
                        mListView.smoothScrollToPosition(Integer.MAX_VALUE);
                    }
                });
    }

    private Subscription mSubscription;

    private void testConcatMap(){
        Observable.interval(1,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .concatMap(new Function<Long, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Long aLong) throws Exception {
                        final List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("I am value " + i+" from "+aLong);
                        }
                        return Observable.fromIterable(list).delay(10,TimeUnit.MILLISECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable=disposable;
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        updateListView(s);

                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        Observable.interval(1, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        updateListView(String.valueOf(aLong));
//                    }
//                });

//        Flowable.interval(1000,TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .concatMap(new Function<Long, Publisher<String>>() {
//                    @Override
//                    public Publisher<String> apply(@NonNull final Long aLong) throws Exception {
//                        return new Publisher<String>() {
//                            @Override
//                            public void subscribe(Subscriber<? super String> subscriber) {
//                                List<String> list=new ArrayList<String>();
//                                for(int i=0;i<3;i++){
//                                    list.add("This is a value gen from "+aLong+" "+i);
//                                }
//                                for(String s:list){
//                                    subscriber.onNext(s);
//                                }
//                                if(aLong==5){
//                                    subscriber.onComplete();
//                                }
//                            }
//                        };
//                    }
//                })
//                .doOnSubscribe(new Consumer<Subscription>() {
//                    @Override
//                    public void accept(Subscription subscription) throws Exception {
//                        mList.clear();
//                    }
//                })
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onSubscribe(Subscription subscription) {
//                        showLog("onSubscribe");
//                        mSubscription=subscription;
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        mList.add(s);
//                        mAdapter.notifyDataSetChanged();
//                        mListView.smoothScrollToPosition(Integer.MAX_VALUE);
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        showLog("onComplete");
//
//                    }
//                });
    }

    public void testZip(){
        Observable<String> ob1=Observable.just("One","Two","Three","Four").subscribeOn(Schedulers.io());
        Observable<Integer> ob2=Observable.just(1,2,3).subscribeOn(Schedulers.io());
        Observable.zip(ob1, ob2, new BiFunction<String,Integer,String>() {
                          @Override
                          public String apply(@NonNull String s, @NonNull Integer integer) throws Exception {
                              return s+integer;
                          }
                  })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        updateListView(o);
                    }
                });
    }

    private long mValue;

    public void testFlowable(){
        Flowable.create(new FlowableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Long> flowableEmitter) throws Exception {
                long l=0;
                while (!flowableEmitter.isCancelled()){
                    while (flowableEmitter.requested()==0){
                        if(flowableEmitter.isCancelled()){
                            break;
                        }
                        try {
                            Thread.sleep(1000);
                        }catch (InterruptedException e){
                            return;
                        }
                    }
                    l++;
                    flowableEmitter.onNext(l);
                    showLog("latest request count: "+flowableEmitter.requested());
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        break;
                    }
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        mSubscription=subscription;
                        mSubscription.request(1);
                    }

                    @Override
                    public void onNext(Long l) {
                        updateListView(String.valueOf(l));
                        if(mSubscription!=null){
                            mSubscription.request(1);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSubscription!=null){
            mSubscription.cancel();
            mSubscription=null;
        }
        if(mDisposable!=null){
            mDisposable.dispose();
            mDisposable=null;
        }
    }

    private void updateListView(final String msg){
        showLog("updateListView");
        mList.add(msg);
        mAdapter.notifyDataSetChanged();
        mListView.smoothScrollToPosition(Integer.MAX_VALUE);

    }

    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }


}
