package com.part.common.ui.widget.Scrolling.MixScroll.Base;


import android.view.ViewGroup;

import com.part.common.ui.widget.Scrolling.MixScroll.MixScrolling;


/**
 * by ckckck 2018/12/27
 * <p>
 * life is short , bugs are too many!
 */
public interface IScrollProcess {
    void onHeader(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling, boolean y);
    void onFootor(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling, boolean y);
    Refreshable getHeader(ViewGroup parent);
    Refreshable getFooter(ViewGroup parent);
    RefreshMode getMode();
}
