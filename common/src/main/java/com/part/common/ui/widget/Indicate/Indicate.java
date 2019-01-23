package com.part.common.ui.widget.Indicate;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * by ckckck 2019/1/22
 * <p>
 * life is short , bugs are too many!
 */
public class Indicate extends View {
    IIndicateDrawer drawer = new NumberIndicate();

    public Indicate(Context context) {
        this(context, null);
    }

    public Indicate(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Indicate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] ints = drawer.onMeasure(this, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(ints[0], ints[1]);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawer.onDraw(canvas);
    }

    public void setDrawer(IIndicateDrawer drawer) {
        this.drawer = drawer;
    }

    public void setUpViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(drawer);
        int currentItem = viewPager.getCurrentItem();
        int count = viewPager.getAdapter().getCount();
        drawer.initCount(currentItem, count);
    }
}
