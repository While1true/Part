package com.part.basemoudle.ui.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * by ckckck 2019/1/18
 * <p>
 * life is short , bugs are too many!
 */
public class ViewPagerX extends ViewPager {


    public ViewPagerX(@NonNull Context context) {
        super(context);
    }

    public ViewPagerX(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}
