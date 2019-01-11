package com.part.common.ui.widget.Scrolling.MixScroll;


import android.view.ViewGroup;

import com.part.common.ui.widget.Scrolling.MixScroll.Base.IScrollProcess;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.RefreshMode;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.RefreshState;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.Refreshable;


/**
 * by ckckck 2018/12/27
 * <p>
 * life is short , bugs are too many!
 */
public class RefreshAutoLoadingProcess implements IScrollProcess {
    private Refreshable header,footer;
    private boolean isnomore=false;
    private MixScrolling mixScrolling;
    public RefreshAutoLoadingProcess() {
    }

    public RefreshAutoLoadingProcess(Refreshable header, Refreshable footer) {
        this.header = header;
        this.footer = footer;
    }
    @Override
    public void onHeader(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling, boolean y) {
        if (y) {
            onHeaderVirtical(mixScrolling, remain, scrolledXY, fling);
        } else {
            onHeaderHoriztional(mixScrolling, remain, scrolledXY, fling);
        }
    }

    @Override
    public void onFootor(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling, boolean y) {
        if (y) {
            onFooterVirtical(mixScrolling, remain, scrolledXY, fling);
        } else {
            onFooterHoriztional(mixScrolling, remain, scrolledXY, fling);
        }
    }

    @Override
    public Refreshable getHeader(ViewGroup group) {
        return header;
    }

    @Override
    public Refreshable getFooter(ViewGroup group) {
        return footer;
    }

    @Override
    public RefreshMode getMode() {
        return RefreshMode.NORMAL;
    }


    private void onHeaderVirtical(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        if(mixScrolling.getRefreshState() == RefreshState.LOADING)
            return;
        boolean refreshing = mixScrolling.getRefreshState() == RefreshState.REFRESHING;
        if (!refreshing && fling) {
            return;
        }
        int scrollY = mixScrolling.getScrollY();
        float strength = mixScrolling.getStrength();
        Refreshable header = mixScrolling.getHeader();
        int canPullSpace = (fling||refreshing)?header.getRefreshSpace():header.canPullSpace();
        //下滑
        if (remain < 0) {
            canPullSpace=Math.max(0,canPullSpace+scrollY);
            if(canPullSpace!=0) {
                float virtualPull = refreshing ? remain : remain / strength;
                float min = Math.min(-virtualPull, canPullSpace);
                mixScrolling.scrollBy(0, -(int) min);
                scrolledXY[1] -= refreshing ? min : min * strength;
            }
        } else {
            int min = Math.min(-scrollY, remain);
            mixScrolling.scrollBy(0, min);
            scrolledXY[1] += min;
        }

    }

    private void onHeaderHoriztional(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        if(mixScrolling.getRefreshState() == RefreshState.LOADING)
            return;
        boolean refreshing = mixScrolling.getRefreshState() == RefreshState.REFRESHING;
        if (!refreshing && fling) {
            return;
        }
        int scrollX = mixScrolling.getScrollX();
        float strength = mixScrolling.getStrength();
        Refreshable header = mixScrolling.getHeader();
        int canPullSpace = (fling||refreshing)?header.getRefreshSpace():header.canPullSpace();
        //下滑
        if (remain < 0) {
            canPullSpace=Math.max(0,canPullSpace+scrollX);
            if(canPullSpace!=0) {
                float virtualPull = refreshing ? remain : remain / strength;
                float min = Math.min(-virtualPull, canPullSpace);
                mixScrolling.scrollBy(-(int) min,0);
                scrolledXY[0] -= refreshing ? min : min * strength;
            }
        } else {
            int min = Math.min(-scrollX, remain);
            mixScrolling.scrollBy( min,0);
            scrolledXY[0] += min;
        }
    }
    private void onFooterVirtical(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        if(mixScrolling.getRefreshState() == RefreshState.REFRESHING)
            return;
        this.mixScrolling=mixScrolling;
        boolean loading = mixScrolling.getRefreshState() == RefreshState.LOADING;
        int scrollY = mixScrolling.getScrollY();
        float strength = mixScrolling.getStrength();
        Refreshable footer = mixScrolling.getFooter();
        int canPullSpace = (fling||loading||isnomore)?footer.getRefreshSpace():footer.canPullSpace();
        //下滑
        if (remain < 0) {
            int min = Math.min(scrollY, -remain);
            mixScrolling.scrollBy(0, -min);
            scrolledXY[1] -= min;

        } else {
            canPullSpace=Math.max(0,canPullSpace-scrollY);
            if(canPullSpace!=0) {
                float virtualPull = (isnomore||loading)?remain:remain/strength;
                float min = Math.min(virtualPull, canPullSpace);
                mixScrolling.scrollBy(0, (int) min);
                scrolledXY[1] +=(isnomore||loading)?min:min*strength;
            }else {
                if(fling) {
                    mixScrolling.stopScroll();
                    if(!isnomore) {
                        mixScrolling.setRefreshState(RefreshState.LOADING);
                    }
                }
            }
        }

    }

    private void onFooterHoriztional(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        if(mixScrolling.getRefreshState() == RefreshState.REFRESHING)
            return;
        this.mixScrolling=mixScrolling;
        boolean loading = mixScrolling.getRefreshState() == RefreshState.LOADING;
        int scrollX = mixScrolling.getScrollX();
        float strength = mixScrolling.getStrength();
        Refreshable footer = mixScrolling.getFooter();
        int canPullSpace = (fling||loading||isnomore)?footer.getRefreshSpace():footer.canPullSpace();
        //下滑
        if (remain < 0) {
            int min = Math.min(scrollX, -remain);
            mixScrolling.scrollBy( -min,0);
            scrolledXY[0] -= min;

        } else {
            canPullSpace=Math.max(0,canPullSpace-scrollX);
            if(canPullSpace!=0) {
                float virtualPull =(isnomore||loading)?remain:remain/strength;
                float min = Math.min(virtualPull, canPullSpace);
                mixScrolling.scrollBy( (int) min,0);
                scrolledXY[0] += (isnomore||loading)?min:min*strength;
            }else {
                if(fling) {
                    mixScrolling.stopScroll();
                    if(!isnomore) {
                        mixScrolling.setRefreshState(RefreshState.LOADING);
                    }
                }
            }
        }
    }

    public boolean isIsnomore() {
        return isnomore;
    }

    public void setIsnomore(boolean isnomore,String info) {
        this.isnomore = isnomore;
        Refreshable footer = mixScrolling.getFooter();
        if(footer instanceof SimpleHeaderFooter){
            ((SimpleHeaderFooter) footer).setIsnomore(isnomore,info);
        }
    }
}
