package com.part.basemoudle.ui.activity;

import android.content.Context;
import android.util.AttributeSet;

import com.part.common.ui.widget.Scrolling.MixScroll.Base.SizeUtil;
import com.part.common.ui.widget.Scrolling.MixScroll.SimpleHeaderFooter;
import com.part.common.ui.widget.Scrolling.ScrollDirection;
import com.part.common.util.AppContext;

/**
 * by ckckck 2019/1/24
 * <p>
 * life is short , bugs are too many!
 */
public class FloorHeader extends SimpleHeaderFooter {
    public FloorHeader(Context context) {
        super(context);
    }

    public FloorHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloorHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FloorHeader(Context context, boolean isHeader) {
        super(context, isHeader);
    }

    public FloorHeader(Context context, boolean isHeader, ScrollDirection direction) {
        super(context, isHeader, direction);
    }

    @Override
    public int secondFloorSpace() {
        return AppContext.get().getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public int canSecondFloorSpace() {
        return canRefreshSpace()+SizeUtil.dp2px(AppContext.get(),20);
    }
}
