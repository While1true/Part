package com.part.common.ui.widget;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.part.common.ui.widget.Scrolling.MixScroll.MixScrolling;

/**
 * by ckckck 2019/1/14
 * <p>
 * life is short , bugs are too many!
 */
public class FixedAppBarBehavior extends AppBarLayout.Behavior{
    int[]scrolls=new int[2];
    public FixedAppBarBehavior() {
        super();
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        if(target instanceof MixScrolling && ((MixScrolling) target).getScrollYY()!=0){
            ((MixScrolling) target).scrollXY(dxUnconsumed,dyUnconsumed,scrolls,false);
        }else {
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        }

    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if(target instanceof MixScrolling && ((MixScrolling) target).getScrollYY()!=0){
            ((MixScrolling) target).scrollXY(dx,dy,consumed,false);
        }else {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        }
    }
}
