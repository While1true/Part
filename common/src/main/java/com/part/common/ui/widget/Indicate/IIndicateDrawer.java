package com.part.common.ui.widget.Indicate;

import android.graphics.Canvas;
import android.support.v4.view.ViewPager;

/**
 * by ckckck 2019/1/22
 * <p>
 * life is short , bugs are too many!
 */
public interface IIndicateDrawer extends ViewPager.OnPageChangeListener {
    void onDraw(Canvas canvas);
    void initCount(int current,int maxCount);
    int[] onMeasure(Indicate indicate,int widthMeasureSpec, int heightMeasureSpec);
}
