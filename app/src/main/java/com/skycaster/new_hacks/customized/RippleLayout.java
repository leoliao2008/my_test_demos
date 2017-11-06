package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by 廖华凯 on 2017/10/25.
 */

public class RippleLayout extends FrameLayout {

    private final int HORIZONTAL_BLOCKS_COUNT=200;
    private final int VERTICAL_BLOCKS_COUNT=200;
    private final int VERTICS_COUNT=(HORIZONTAL_BLOCKS_COUNT+1)*(VERTICAL_BLOCKS_COUNT+1)*2;
    private float mVertics[]=new float[VERTICS_COUNT];

    public RippleLayout(@NonNull Context context) {
        this(context,null);
    }

    public RippleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RippleLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public RippleLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Bitmap viewCache = getViewCache();
        if(viewCache==null){
            return;
        }


    }

    private Bitmap getViewCache(){
        Bitmap bitmap=null;
        setDrawingCacheEnabled(true);
        buildDrawingCache(true);
        Bitmap drawingCache = getDrawingCache();
        if(drawingCache!=null){
            bitmap=Bitmap.createBitmap(drawingCache);
        }
        setDrawingCacheEnabled(false);
        return bitmap;
    }


}
