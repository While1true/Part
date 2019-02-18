package com.common.common.ui.widget.Scrolling.MixScroll.Base;

/**
 * by ckckck 2018/12/28
 * <p>
 * life is short , bugs are too many!
 */
public interface OnScrollStateListener {
    void onStateChange(RefreshState state);

    void onScroll(int scrollX, int scrollY);
}
