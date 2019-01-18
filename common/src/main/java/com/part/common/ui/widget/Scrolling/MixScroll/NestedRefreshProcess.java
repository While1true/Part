package com.part.common.ui.widget.Scrolling.MixScroll;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
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
public class NestedRefreshProcess implements IScrollProcess {
    private Refreshable header, footer;
    private AppBarLayoutState state = AppBarLayoutState.EXPANDED;
    private AppBarLayout appBarLayout;
    AppBarLayout.OnOffsetChangedListener listener = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (i == 0) {
                state = AppBarLayoutState.EXPANDED;
            } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                state = AppBarLayoutState.COLLAPSED;
            } else {
                state = AppBarLayoutState.MIDDLE;
            }
        }
    };

    public NestedRefreshProcess(AppBarLayout layout) {
       this(null,null,layout);
    }


    public NestedRefreshProcess(Refreshable header, Refreshable footer,AppBarLayout layout) {
        this.header = header;
        this.footer = footer;
        appBarLayout=layout;
        if(appBarLayout!=null){
            appBarLayout.addOnOffsetChangedListener(listener);
        }
    }

    @Override
    public void onHeader(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling, boolean y) {
        if(appBarLayout==null){
            setupAppbarListener(mixScrolling);
        }
        if (y) {
            onHeaderVirtical(mixScrolling, remain, scrolledXY, fling);
        } else {
            onHeaderHoriztional(mixScrolling, remain, scrolledXY, fling);
        }
    }


    @Override
    public void onFootor(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling, boolean y) {
        if(appBarLayout==null){
            setupAppbarListener(mixScrolling);
        }
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


    private void onHeaderVirtical(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        boolean refreshing = mixScrolling.getRefreshState() == RefreshState.REFRESHING;
        if (mixScrolling.getRefreshState() == RefreshState.LOADING || (remain < 0 && state != AppBarLayoutState.EXPANDED && !refreshing))
            return;
        //正在刷新时先 上滑dispach
        if (refreshing && state != AppBarLayoutState.COLLAPSED && remain > 0) {
            return;
        }
        if (!refreshing && fling) {
            return;
        }
        int scrollY = mixScrolling.getScrollY();
        float strength = mixScrolling.getStrength();
        Refreshable header = mixScrolling.getHeader();
        int canPullSpace = (fling || refreshing) ? header.getRefreshSpace() : header.canPullSpace();
        //下滑
        if (remain < 0) {
            canPullSpace = Math.max(0, canPullSpace + scrollY);
            if (canPullSpace != 0) {
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
        boolean refreshing = mixScrolling.getRefreshState() == RefreshState.REFRESHING;
        if (mixScrolling.getRefreshState() == RefreshState.LOADING || (remain < 0 && state != AppBarLayoutState.EXPANDED && !refreshing))
            return;

        if (refreshing && state != AppBarLayoutState.COLLAPSED && remain > 0) {
            return;
        }
        if (!refreshing && fling) {
            return;
        }
        int scrollX = mixScrolling.getScrollX();
        float strength = mixScrolling.getStrength();
        Refreshable header = mixScrolling.getHeader();
        int canPullSpace = (fling || refreshing) ? header.getRefreshSpace() : header.canPullSpace();
        //下滑
        if (remain < 0) {
            canPullSpace = Math.max(0, canPullSpace + scrollX);
            if (canPullSpace != 0) {
                float virtualPull = refreshing ? remain : remain / strength;
                float min = Math.min(-virtualPull, canPullSpace);
                mixScrolling.scrollBy(-(int) min, 0);
                scrolledXY[0] -= refreshing ? min : min * strength;
            }
        } else {
            int min = Math.min(-scrollX, remain);
            mixScrolling.scrollBy(min, 0);
            scrolledXY[0] += min;
        }
    }

    private void onFooterVirtical(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        if (mixScrolling.getRefreshState() == RefreshState.REFRESHING)
            return;
        boolean loading = mixScrolling.getRefreshState() == RefreshState.LOADING;
        if (!loading && fling) {
            return;
        }
        int scrollY = mixScrolling.getScrollY();
        float strength = mixScrolling.getStrength();
        Refreshable footer = mixScrolling.getFooter();
        int canPullSpace = (fling || loading) ? footer.getRefreshSpace() : footer.canPullSpace();
        //下滑
        if (remain < 0) {
            int min = Math.min(scrollY, -remain);
            mixScrolling.scrollBy(0, -min);
            scrolledXY[1] -= min;

        } else {
            canPullSpace = Math.max(0, canPullSpace - scrollY);
            if (canPullSpace != 0) {
                float virtualPull = loading ? remain : remain / strength;
                float min = Math.min(virtualPull, canPullSpace);
                mixScrolling.scrollBy(0, (int) min);
                scrolledXY[1] += loading ? min : min * strength;
            }
        }

    }

    private void onFooterHoriztional(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        if (mixScrolling.getRefreshState() == RefreshState.REFRESHING)
            return;
        boolean loading = mixScrolling.getRefreshState() == RefreshState.LOADING;
        if (!loading && fling) {
            return;
        }
        int scrollX = mixScrolling.getScrollX();
        float strength = mixScrolling.getStrength();
        Refreshable footer = mixScrolling.getFooter();
        int canPullSpace = (fling || loading) ? footer.getRefreshSpace() : footer.canPullSpace();
        //下滑
        if (remain < 0) {
            int min = Math.min(scrollX, -remain);
            mixScrolling.scrollBy(-min, 0);
            scrolledXY[0] -= min;

        } else {
            canPullSpace = Math.max(0, canPullSpace - scrollX);
            if (canPullSpace != 0) {
                float virtualPull = loading ? remain : remain / strength;
                float min = Math.min(virtualPull, canPullSpace);
                mixScrolling.scrollBy((int) min, 0);
                scrolledXY[0] += loading ? min : min * strength;
            }
        }


    }
    private void setupAppbarListener(MixScrolling mixScrolling){
        ViewGroup parent= (ViewGroup) mixScrolling.getParent();
        while (!(parent instanceof CoordinatorLayout)&&parent!=null){
            parent= (ViewGroup) parent.getParent();
        }
        if(parent!=null && parent instanceof CoordinatorLayout){
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                if(child instanceof AppBarLayout){
                    appBarLayout= (AppBarLayout) child;
                    appBarLayout.addOnOffsetChangedListener(listener);
                    break;
                }
            }
        }
    }
    @Override
    public RefreshMode getMode() {
        return RefreshMode.NORMAL;
    }

    public enum AppBarLayoutState {
        COLLAPSED, EXPANDED, MIDDLE
    }
}
