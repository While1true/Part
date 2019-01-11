package com.part.common.ui.widget.Scrolling.MixScroll.Base;

import android.view.View;

import com.part.common.ui.widget.Scrolling.ScrollDirection;
import com.part.common.ui.widget.Scrolling.Scrolling;


/**
 * by ckckck 2018/12/28
 * <p>
 * life is short , bugs are too many!
 */
public interface Refreshable {

    void onLayout(ScrollDirection direction, Scrolling parent, boolean changed, int left, int top, int right, int bottom);

    boolean isHeader();

    View getContentView();

    void onPull(float pull, boolean fling);

    void onStateChange(RefreshState state);

    int canPullSpace();

    int getRefreshSpace();
}
