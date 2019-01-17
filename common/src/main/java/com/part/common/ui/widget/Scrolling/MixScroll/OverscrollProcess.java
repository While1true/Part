package com.part.common.ui.widget.Scrolling.MixScroll;

import android.view.ViewGroup;

import com.part.common.ui.widget.Scrolling.MixScroll.Base.RefreshMode;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.Refreshable;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.SizeUtil;
import com.part.common.util.AppContext;


/**
 * by ckckck 2019/1/2
 * <p>
 * life is short , bugs are too many!
 */
public class OverscrollProcess extends RefreshProcess {
    private boolean supportFling = false;
    private boolean fling = false;

    public OverscrollProcess() {
    }

    public OverscrollProcess(boolean supportFling) {
        this.supportFling = supportFling;
    }

    @Override
    public void onHeader(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling, boolean y) {
        this.fling=fling;
        super.onHeader(mixScrolling, remain, scrolledXY, false, y);
        stopFlingScroll(mixScrolling);
    }

    private void stopFlingScroll(MixScrolling mixScrolling) {
        if(supportFling&&fling){
            int d=mixScrolling.getScroll();
            if(Math.abs(d)>= SizeUtil.dp2px(mixScrolling.getContext(),30)){
                mixScrolling.stopScroll();
                mixScrolling.startAnimation();
            }
        }
    }

    @Override
    public void onFootor(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling, boolean y) {
        this.fling=fling;
        super.onFootor(mixScrolling, remain, scrolledXY, false, y);
        stopFlingScroll(mixScrolling);
    }

    @Override
    public Refreshable getHeader(ViewGroup group) {
        return new EmptyHeaderFooter(group.getContext(), true);
    }

    @Override
    public Refreshable getFooter(ViewGroup group) {
        return new EmptyHeaderFooter(group.getContext(), false);
    }

    @Override
    public RefreshMode getMode() {
        return RefreshMode.NORMAL_SPECIL;
    }
}
