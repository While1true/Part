package com.part.common.ui.widget.Scrolling.MixScroll;

import android.view.ViewGroup;

import com.part.common.ui.widget.Scrolling.MixScroll.Base.Refreshable;


/**
 * by ckckck 2019/1/2
 * <p>
 * life is short , bugs are too many!
 */
public class OverscrollProcess extends RefreshProcess {
    @Override
    public Refreshable getHeader(ViewGroup group) {
        return new EmptyHeaderFooter(group.getContext(),true);
    }
    @Override
    public Refreshable getFooter(ViewGroup group) {
        return new EmptyHeaderFooter(group.getContext(),false);
    }
}
