package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 廖华凯 on 2018/4/26.
 */

public class GetTangeView extends View {
    private int mViewWidth;
    private int mViewHeight;
    private Path mPathOuterRing;
    private Path mPathInnerRing;
    private Path mDragTrace;
    private Paint mPaint;
    private float mRadius;
    private float mCtrX;
    private float mCtrY;
    private float mCtrRadius;
    private Paint mCtrPaint;
    private float mMargin=25;
    private boolean isDraggable;
    private PathMeasure mPathMeasure;
    private float mPathMeasureLength;


    public GetTangeView(Context context) {
        this(context,null);
    }

    public GetTangeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GetTangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(5);

        mCtrPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mCtrPaint.setStyle(Paint.Style.FILL);
        mCtrPaint.setColor(Color.RED);

        mPathOuterRing =new Path();
        mPathInnerRing=new Path();
        mDragTrace =new Path();
        mPathMeasure=new PathMeasure();

    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                showLog("Down");
                float dx = event.getX();
                float dy = event.getY();
                RectF downRectF=new RectF(mCtrX-mCtrRadius,mCtrY-mCtrRadius,mCtrX+mCtrRadius,mCtrY+mCtrRadius);
                if(downRectF.contains(dx,dy)){
                    isDraggable =true;
                }else {
                    showLog("no contain downRect left ="+downRectF.left+" top="+downRectF.top+" right="+downRectF.right+" bottom="+downRectF.bottom);
                    showLog("vs: dx="+dx+" dy="+dy);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(isDraggable){
                    showLog("Move");
                    float mx = event.getX();
                    float my = event.getY();
                    Path path=new Path();
                    path.addCircle(mx,my,mCtrRadius, Path.Direction.CW);
                    Path temp=new Path();
                    if(temp.op(mDragTrace,path, Path.Op.INTERSECT)){
                        RectF b=new RectF();
                        temp.computeBounds(b,true);
                        float centerX = b.centerX();
                        float centerY = b.centerY();
                        double radian = getRadian(mViewWidth>>1,mViewHeight>>1,centerX,centerY);
                        float [] pos=new float[2];
                        mPathMeasure.getPosTan((float) (mPathMeasureLength/2/Math.PI*radian),pos,null);
                        mCtrX=pos[0];
                        mCtrY=pos[1];
                        postInvalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                isDraggable=false;
                showLog("Up");
                break;
            default:
                break;
        }
        return true;
    }

    private double getRadian(int cx, int cy, float x, float y) {
        //// TODO: 2018/4/26
        return 0;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int defaultWidth=400;
        int defaultHeight=400;
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        ViewGroup.LayoutParams params = getLayoutParams();
        if(params.width== ViewGroup.LayoutParams.WRAP_CONTENT&&params.height==ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(defaultWidth,defaultHeight);
        }else if(params.width== ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(defaultWidth,specHeight);
        }else if(params.height== ViewGroup.LayoutParams.WRAP_CONTENT){
            setMeasuredDimension(specWidth,defaultHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth=w;
        mViewHeight=h;

        if(mViewWidth<mViewHeight){
            mRadius=mViewWidth>>1;
        }else {
            mRadius=mViewHeight>>1;
        }
        mCtrRadius=mRadius/10;
        mRadius=mRadius-mMargin;
        mPathOuterRing.addCircle(mViewWidth>>1,mViewHeight>>1,mRadius, Path.Direction.CW);
        mPathInnerRing.addCircle(mViewWidth>>1,mViewHeight>>1,mRadius-mCtrRadius,Path.Direction.CW);
        mDragTrace.op(mPathOuterRing,mPathInnerRing, Path.Op.INTERSECT);
        mCtrX=mViewWidth>>1;
        mCtrY=mCtrRadius;
        mPathMeasure.setPath(mPathOuterRing,false);
        mPathMeasureLength = mPathMeasure.getLength();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPathOuterRing,mPaint);
        canvas.drawCircle(mCtrX,mCtrY,mCtrRadius,mCtrPaint);
    }
    private Disposable mDisposable;

    public void animCircling(){
        Observable.create(new ObservableOnSubscribe<Float>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Float> observableEmitter) throws Exception {
                float i=0;
                while (true){
                    i+=0.001;
                    observableEmitter.onNext(i);
                    if(i>=1){
                        i=0;
                    }
                    if(mDisposable!=null&&mDisposable.isDisposed()){
                        break;
                    }
                    try {
                        Thread.sleep(50);
                    }catch (Exception e){
                        return;
                    }

                }
                observableEmitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Float, Float>() {
                    @Override
                    public Float apply(@NonNull Float aFloat) throws Exception {
                        return mPathMeasureLength*aFloat;
                    }
                })
                .subscribe(new Observer<Float>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        mDisposable=disposable;
                    }

                    @Override
                    public void onNext(@NonNull Float aFloat) {
                        float []pos=new float[2];
                        mPathMeasure.getPosTan(aFloat,pos,null);
                        mCtrX=pos[0];
                        mCtrY=pos[1];
                        invalidate();
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
                        mDisposable=null;

                    }
                });
    }

    public void stopAnim(){
        if(mDisposable!=null){
            mDisposable.dispose();
        }
    }



    private void showLog(String msg){
        Log.e(getClass().getSimpleName(),msg);
    }
}
