package com.part.common.ui.widget.Scrolling.MixScroll;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.part.common.ui.widget.Scrolling.MixScroll.Base.IScrollProcess;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.OnScrollStateListener;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.RefreshMode;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.RefreshState;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.Refreshable;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.SizeUtil;
import com.part.common.ui.widget.Scrolling.ScrollDirection;
import com.part.common.ui.widget.Scrolling.Scrolling;

import java.util.ArrayList;
import java.util.List;

/**
 * by ckckck 2018/12/14
 * <p>
 * life is short , bugs are too many!
 */
public class MixScrolling extends Scrolling implements ValueAnimator.AnimatorUpdateListener {
    public static final int DURING = 250;
    public static final int MIN_DURING = 100;
    private Refreshable header, footer;
    private View scrollContent;
    private RefreshMode refreshType;
    private RefreshState refreshState = RefreshState.IDEL;
    private float strength = 3.0f;
    private ValueAnimator valueAnimator;
    private int[] backScroll = new int[2];
    private IScrollProcess iScrollProcess = new RefreshProcess();
    private int[] tempArray = new int[2];

    public MixScrolling(Context context) {
        this(context, null);
    }

    public MixScrolling(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MixScrolling(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //TODO 支持属性
    private void init() {
        valueAnimator = new ValueAnimator();
        valueAnimator.addUpdateListener(this);
        refreshType = iScrollProcess.getMode();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        getViews();
        addViewInteral(iScrollProcess);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (refreshState == RefreshState.SETTING)
            return true;
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (refreshState == RefreshState.SETTING)
            return true;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startAnimation();
                break;
            case MotionEvent.ACTION_DOWN:
                cancelAnimation();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startAnimation();
                break;
            case MotionEvent.ACTION_DOWN:
                cancelAnimation();
                break;
        }
        return super.onTouchEvent(ev);
    }

    void startAnimation() {
        if (valueAnimator.isRunning() || refreshState.ordinal() > 2)
            return;
        int scrollY = getScrollYY();
        int scrollX = getScrollXX();
        backScroll[0] = scrollX;
        backScroll[1] = scrollY;
        if (direction == ScrollDirection.XY) {

        } else {
            int scroll = getScroll();
            int absScroll = Math.abs(scroll);
            int during = 0;
            if (scroll == 0) {
                return;
            } else {
                stopScroll();
                if (scroll < 0) {
                    if (absScroll > header.getRefreshSpace()) {
                        valueAnimator.setFloatValues(scroll, -header.getRefreshSpace());
                        during = DURING * Math.abs((absScroll - header.getRefreshSpace())) / header.canPullSpace();
                    } else {
                        valueAnimator.setFloatValues(scroll, 0);
                        during = DURING * absScroll / header.canPullSpace();
                    }
                } else {
                    if (absScroll > footer.getRefreshSpace()) {
                        valueAnimator.setFloatValues(scroll, footer.getRefreshSpace());
                        during = DURING * Math.abs((absScroll - footer.getRefreshSpace())) / footer.canPullSpace();
                    } else {
                        valueAnimator.setFloatValues(scroll, 0);
                        during = DURING * absScroll / footer.canPullSpace();
                    }
                }
                valueAnimator.setDuration(Math.max(during, MIN_DURING));
                valueAnimator.start();
            }
        }
    }

    void cancelAnimation() {
        stopScroll();
        if (valueAnimator.isRunning()&&refreshState!=RefreshState.SETTING)
            valueAnimator.cancel();
    }

    /**
     * 位置是否复原
     *
     * @param positionback
     */
    public void setComplete(boolean positionback) {
        cancelAnimation();
        int scroll = getScroll();
        if (scroll == 0 || !positionback) {
            setRefreshState(RefreshState.IDEL);
        } else {
            setRefreshState(RefreshState.SETTING);
            startAnimation();
        }
    }

    public void setComplete() {
        setComplete(true);
    }

    public void setRefreshing() {
        if (refreshState == RefreshState.REFRESHING || header.getRefreshSpace() == 0) {
            return;
        }
        cancelAnimation();
        if(scrollContent instanceof RecyclerView){
            ((RecyclerView) scrollContent).scrollToPosition(0);
        }else {
            scrollContent.scrollTo(0,0);
        }
        setRefreshState(RefreshState.SETTING);
        valueAnimator.setFloatValues(direction == ScrollDirection.Y ? -getScrollYY() : -getScrollXX(), -header.getRefreshSpace());
        int during = DURING * header.getRefreshSpace() / header.canPullSpace();
        valueAnimator.setDuration(during);
        valueAnimator.start();
    }

    public final void setScrollProcess(IScrollProcess iScrollProcess) {
        this.iScrollProcess = iScrollProcess;
        refreshType = iScrollProcess.getMode();
        addViewInteral(iScrollProcess);
    }

    public IScrollProcess getiScrollProcess() {
        return iScrollProcess;
    }

    private void addViewInteral(IScrollProcess iScrollProcess) {
        Refreshable header = iScrollProcess.getHeader(this);
        Refreshable footer = iScrollProcess.getFooter(this);
        if (header != null) {
            if (this.header != null) {
                removeView(this.header.getContentView());
            }
            this.header = header;
            addView(header.getContentView(), direction == ScrollDirection.Y ? new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) : new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        if (footer != null) {
            if (this.footer != null) {
                removeView(this.footer.getContentView());
            }
            this.footer = footer;
            addView(footer.getContentView(), direction == ScrollDirection.Y ? new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) : new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    private void getViews() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof Refreshable) {
                if (((Refreshable) child).isHeader()) {
                    header = (Refreshable) child;
                    header.getContentView();
                } else {
                    footer = (Refreshable) child;
                    footer.getContentView();
                }
            } else if (child instanceof ScrollingView ||"scroll".equals(child.getTag())) {
                scrollContent = child;
            }else{
                if(scrollContent==null){
                    scrollContent=child;
                }
            }
        }
        if(scrollContent!=null){
            ViewCompat.setNestedScrollingEnabled(scrollContent,false);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (header != null) {
            header.onLayout(direction, this, changed, left, top, right, bottom);
        }
        if (footer != null) {
            footer.onLayout(direction, this, changed, left, top, right, bottom);
        }
    }

    @Override
    protected void scrollXY(int deltaX, int deltaY, int[] scrolledXY, boolean fling) {
        if (direction == ScrollDirection.Y) {
            doScroll(deltaY, scrolledXY, fling, true);
        } else {
            doScroll(deltaX, scrolledXY, fling, false);
        }
    }

    private void doScroll(int delta, int[] scrolledXY, boolean fling, boolean y) {
        int remain = delta;
        int consumed = 0;
        boolean preScroll = y ? (scrollContent.canScrollVertically(remain > 0 ? 1 : -1) && (remain > 0 ? getScrollYY() >= 0 : getScrollYY() <= 0)) : (scrollContent.canScrollHorizontally(remain > 0 ? 1 : -1) && (remain > 0 ? getScrollXX() >= 0 : getScrollXX() <= 0));
        if (preScroll) {
            consumed = delta;
            if (y) {
                scrollContent.scrollBy(0, consumed);
            } else {
                scrollContent.scrollBy(consumed, 0);
            }
            scrolledXY[1] = consumed;
        } else {
            int scrolled = y ? getScrollYY() : getScrollXX();
            //头部
            if (scrolled < 0 || (scrolled == 0 && remain < 0)) {
                iScrollProcess.onHeader(this, remain, scrolledXY, fling, y);
                scrolled = y ? getScrollYY() : getScrollXX();
                header.onPull(-scrolled, fling);
            }
            //尾部
            else {
                iScrollProcess.onFootor(this, remain, scrolledXY, fling, y);
                scrolled = y ? getScrollYY() : getScrollXX();
                footer.onPull(scrolled, fling);
            }
        }
    }

    public RefreshState getRefreshState() {
        return refreshState;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public Refreshable getHeader() {
        return header;
    }

    public Refreshable getFooter() {
        return footer;
    }

    public View getScrollContent() {
        return scrollContent;
    }

    void setRefreshState(RefreshState refreshState) {
        this.refreshState = refreshState;
        for (OnScrollStateListener listener : listeners) {
            listener.onStateChange(refreshState);
        }
        header.onStateChange(refreshState);
        footer.onStateChange(refreshState);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float animatedFraction = 1.0f - animation.getAnimatedFraction();
        float animatedValue = (float) animation.getAnimatedValue();
        if (direction == ScrollDirection.XY) {

        } else {
            int scrollDistance = direction == ScrollDirection.Y ? backScroll[1] : backScroll[0];
            int scroll = (int) animatedValue;
//            if(refreshType==RefreshMode.NORMAL) {
//              scrollTo(direction == ScrollDirection.Y ? 0 : scroll, direction == ScrollDirection.X ? 0 : scroll);
//            }else{
            scrollXY(direction == ScrollDirection.Y ? 0 : scroll - getScrollXX(), direction == ScrollDirection.Y ? scroll - getScrollYY() : 0, tempArray, false);
//            }
            if (scrollDistance > 0) {
                footer.onPull(Math.abs(scroll), false);
            } else {
                header.onPull(Math.abs(scroll), false);
            }
            if (animatedFraction == 0) {
                if (scroll != 0) {
                    setRefreshState(scrollDistance < 0 ? RefreshState.REFRESHING : RefreshState.LOADING);
                } else {
                    setRefreshState(RefreshState.IDEL);
                }
            }
        }
    }

    //头尾部出来不调用fling,调用时停止动画
    @Override
    protected boolean canFling(int vx, int vy) {
        if(refreshType!=RefreshMode.NORMAL) {
            int scroll = getScroll();
            if (scroll != 0)
                return false;
            cancelAnimation();
        }
        return super.canFling(vx, vy);
    }

    /**
     * 根据direction返回该方向的scroll
     * @return
     */
    public int getScroll() {
        return direction == ScrollDirection.X ? getScrollXX() : getScrollYY();
    }

    @Override
    public void setDirection(ScrollDirection direction) {
        if (direction == null || direction == ScrollDirection.XY) {
            throw new UnsupportedOperationException("不支持该方向");
        }
        super.setDirection(direction);
    }


    private List<OnScrollStateListener> listeners = new ArrayList<>();

    public void addListener(OnScrollStateListener listener) {
        if (listener == null || listeners.contains(listener)) {
            return;
        }
        listeners.add(listener);
    }

    public void removeListener(OnScrollStateListener listener) {
        if (listener == null || !listeners.contains(listener)) {
            return;
        }
        listeners.remove(listener);
    }

    @Override
    protected void onFlingFinished() {
        if (getScroll() != 0) {
            startAnimation();
        }
    }

    public int getScrollXX() {
        return currentX;
    }

    public int getScrollYY() {
        return currentY;
    }

    @Override
    public void scrollBy(int x, int y) {
        scrollTo(currentX + x, currentY + y);
    }

    int currentX, currentY;

    @Override
    public void scrollTo(int x, int y) {
        currentX = x;
        currentY = y;
        if (refreshType != RefreshMode.EVALUATE) {
            super.scrollTo(x, y);
        }
        for (OnScrollStateListener listener : listeners) {
            listener.onScroll(currentX, currentY);
        }
    }

    /**
     * 控制nestedRefresh
     * @param initdx
     * @param initdy
     * @return
     */
    @Override
    protected boolean needDispathNestedPreScroll(int initdx, int initdy) {
        if(refreshType==RefreshMode.NORMAL&&refreshState==RefreshState.REFRESHING){
            return true;
        }
        return getScroll()==0;
    }

    @Override
    protected int getMaxFlingDistance() {
        if(refreshType==RefreshMode.NORMAL)
            return super.getMaxFlingDistance();
     return ScrollUtil.getCanScrollDistanceToBottom(scrollContent, direction)+footer.canPullSpace();
    }

    @Override
    protected int getMinFlingDistance() {
        if(refreshType==RefreshMode.NORMAL)
            return super.getMinFlingDistance();
        return -(ScrollUtil.getCanScrollDistanceToTop(scrollContent, direction)+header.canPullSpace());
    }
}
