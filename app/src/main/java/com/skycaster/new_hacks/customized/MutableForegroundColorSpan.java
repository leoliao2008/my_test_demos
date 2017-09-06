package com.skycaster.new_hacks.customized;

import android.graphics.Color;
import android.os.Build;
import android.os.Parcel;
import android.support.annotation.ColorInt;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;

/**
 * Created by 廖华凯 on 2017/9/6.
 */

public class MutableForegroundColorSpan extends ForegroundColorSpan {
    private int mColor;
    private int mAlpha=255;
    public MutableForegroundColorSpan(@ColorInt int color,int alpha) {
        super(color);
        mColor=color;
        mAlpha=alpha;
    }

    public MutableForegroundColorSpan(Parcel src) {
        super(src);
        mColor=src.readInt();
        mAlpha=src.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(mColor);
        dest.writeFloat(mAlpha);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(getForegroundColor());
    }

    @Override
    public int getForegroundColor() {
        if (Build.VERSION.SDK_INT >= 26) {
            return Color.argb(mAlpha,Color.red(mColor),Color.green(mColor),Color.blue(mColor));
        }else {
            return (mAlpha << 24) | (Color.red(mColor) << 16) | (Color.green(mColor) << 8) | Color.blue(mColor);
        }
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public float getAlpha() {
        return mAlpha;
    }

    public void setAlpha(int alpha) {
        mAlpha = alpha;
    }
}
