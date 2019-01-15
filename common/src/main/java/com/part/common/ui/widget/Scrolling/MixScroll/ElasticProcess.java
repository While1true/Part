package com.part.common.ui.widget.Scrolling.MixScroll;

import android.view.View;
import android.view.ViewGroup;

import com.part.common.ui.widget.Scrolling.MixScroll.Base.IScrollProcess;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.RefreshMode;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.Refreshable;


/**
 * by ckckck 2018/12/27
 * <p>
 * life is short , bugs are too many!
 */
public class ElasticProcess implements IScrollProcess {
    private boolean isSupportFling = false;

    public ElasticProcess() {
    }

    public ElasticProcess(boolean isSupportFling) {
        this.isSupportFling = isSupportFling;
    }

    @Override
    public final void onHeader(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling, boolean y) {
        if (!isSupportFling && fling) {
            return;
        }
        if (y) {
            onHeaderVirtical(mixScrolling, remain, scrolledXY, fling);
        } else {
            onHeaderHoriztional(mixScrolling, remain, scrolledXY, fling);
        }
    }

    @Override
    public final void onFootor(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling, boolean y) {
        if (!isSupportFling && fling) {
            return;
        }
        if (y) {
            onFooterVirtical(mixScrolling, remain, scrolledXY, fling);
        } else {
            onFooterHoriztional(mixScrolling, remain, scrolledXY, fling);
        }
    }

    @Override
    public Refreshable getHeader(ViewGroup group) {
        return new EmptyHeaderFooter(group.getContext(), true);
    }

    @Override
    public Refreshable getFooter(ViewGroup group) {
        return new EmptyHeaderFooter(group.getContext(), false);
    }


    private final void onHeaderVirtical(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        int scrollY = mixScrolling.getScrollYY();
        float strength = mixScrolling.getStrength();
        Refreshable header = mixScrolling.getHeader();
        View scrollContent = mixScrolling.getScrollContent();
        int canPullSpace = header.canPullSpace();

        //下滑
        if (remain < 0) {
            canPullSpace = Math.max(0, canPullSpace + scrollY);
            if (canPullSpace != 0) {
                float virtualPull = remain / strength;
                float min = Math.min(-virtualPull, canPullSpace);
                scrolledXY[1] -= min * strength;
                mixScrolling.scrollBy(0, (int) -min);

                scrollY = mixScrolling.getScrollYY();
                scrollContent.setPivotX(0);
                scrollContent.setPivotY(0);
                scrollContent.setScaleY(Math.min(1.5f, 1f + caculateZhangli(Math.abs(scrollY), mixScrolling.getMeasuredHeight()/6) / mixScrolling.getMeasuredHeight()/2));
            } else {
                if (fling) {
                    mixScrolling.stopScroll();
                    mixScrolling.startAnimation();
                }
            }
        } else {
            float min = Math.min(-scrollY, remain);
            mixScrolling.scrollBy(0, (int) min);
            scrolledXY[1] += min;

            scrollY = mixScrolling.getScrollYY();
            scrollContent.setPivotX(0);
            scrollContent.setPivotY(0);
            scrollContent.setScaleY(Math.min(1.5f, 1f + caculateZhangli(Math.abs(scrollY), mixScrolling.getMeasuredHeight()/6) / mixScrolling.getMeasuredHeight()/2));
        }

    }

    private final void onHeaderHoriztional(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        int scrollX = mixScrolling.getScrollXX();
        float strength = mixScrolling.getStrength();
        Refreshable header = mixScrolling.getHeader();
        View scrollContent = mixScrolling.getScrollContent();
        int canPullSpace = header.canPullSpace();
        //下滑
        if (remain < 0) {
            canPullSpace = Math.max(0, canPullSpace + scrollX);
            if (canPullSpace != 0) {
                float virtualPull = remain / strength;
                float min = Math.min(-virtualPull, canPullSpace);
                mixScrolling.scrollBy(-(int) min, 0);
                scrolledXY[0] -= min * strength;

                scrollX = mixScrolling.getScrollXX();
                scrollContent.setPivotX(0);
                scrollContent.setPivotY(0);
                scrollContent.setScaleX(Math.min(1.5f, 1f + caculateZhangli(Math.abs(scrollX), mixScrolling.getMeasuredHeight()/6) / mixScrolling.getMeasuredWidth()/2));

            } else {
                if (fling) {
                    mixScrolling.stopScroll();
                    mixScrolling.startAnimation();
                }
            }
        } else {
            float min = Math.min(-scrollX, remain);
            mixScrolling.scrollBy((int) min, 0);
            scrolledXY[0] += min;

            scrollX = mixScrolling.getScrollXX();
            scrollContent.setPivotX(0);
            scrollContent.setPivotY(0);
            scrollContent.setScaleX(Math.min(1.5f, 1f + caculateZhangli(Math.abs(scrollX), mixScrolling.getMeasuredWidth()/2) / mixScrolling.getMeasuredWidth()/2));

        }
    }

    private final void onFooterVirtical(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        int scrollY = mixScrolling.getScrollYY();
        float strength = mixScrolling.getStrength();
        Refreshable footer = mixScrolling.getFooter();
        View scrollContent = mixScrolling.getScrollContent();
        int canPullSpace = footer.canPullSpace();

        //下滑
        if (remain < 0) {
            float min = Math.min(scrollY, -remain);
            mixScrolling.scrollBy(0, (int) -min);
            scrolledXY[1] -= min;
            scrollY = mixScrolling.getScrollYY();
            scrollContent.setPivotX(0);
            scrollContent.setPivotY(scrollContent.getMeasuredHeight());
            scrollContent.setScaleY(Math.min(1.5f, 1f + caculateZhangli(Math.abs(scrollY), mixScrolling.getMeasuredHeight()/6) / mixScrolling.getMeasuredHeight()/2));

        } else {
            canPullSpace = Math.max(0, canPullSpace - scrollY);
            if (canPullSpace != 0) {
                float virtualPull = remain / strength;
                float min = Math.min(virtualPull, canPullSpace);
                mixScrolling.scrollBy(0, (int) min);
                scrolledXY[1] += min * strength;

                scrollY = mixScrolling.getScrollYY();
                scrollContent.setPivotX(0);
                scrollContent.setPivotY(scrollContent.getMeasuredHeight());
                scrollContent.setScaleY(1.2f);
                scrollContent.setScaleY(Math.min(1.5f, 1f + caculateZhangli(Math.abs(scrollY), mixScrolling.getMeasuredHeight()/6) / mixScrolling.getMeasuredHeight()/2));
            } else {
                if (fling) {
                    mixScrolling.stopScroll();
                    mixScrolling.startAnimation();
                }
            }
        }

    }

    private final void onFooterHoriztional(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        int scrollX = mixScrolling.getScrollXX();
        float strength = mixScrolling.getStrength();
        Refreshable footer = mixScrolling.getFooter();
        View scrollContent = mixScrolling.getScrollContent();
        int canPullSpace = footer.canPullSpace();
        //下滑
        if (remain < 0) {
            float min = Math.min(scrollX, -remain);
            mixScrolling.scrollBy((int) -min, 0);
            scrolledXY[0] -= min;

            scrollX = mixScrolling.getScrollXX();
            scrollContent.setPivotX(scrollContent.getMeasuredWidth());
            scrollContent.setPivotY(0);
            scrollContent.setScaleX(Math.min(1.5f, 1f + caculateZhangli(Math.abs(scrollX), mixScrolling.getMeasuredHeight()/6) / mixScrolling.getMeasuredWidth()/2));

        } else {
            canPullSpace = Math.max(0, canPullSpace - scrollX);
            if (canPullSpace != 0) {
                float virtualPull = remain / strength;
                float min = Math.min(virtualPull, canPullSpace);
                mixScrolling.scrollBy((int) min, 0);
                scrolledXY[0] += min * strength;

                scrollX = mixScrolling.getScrollXX();
                scrollContent.setPivotX(scrollContent.getMeasuredWidth());
                scrollContent.setPivotY(0);
                scrollContent.setScaleX(Math.min(1.5f, 1f + caculateZhangli(Math.abs(scrollX), mixScrolling.getMeasuredHeight()/6) / mixScrolling.getMeasuredWidth()/2));

            } else {
                if (fling) {
                    mixScrolling.stopScroll();
                    mixScrolling.startAnimation();
                }
            }
        }


    }

    /**
     * 1+1/2+1/3+……+1/n=lnn+R
     * R为欧拉常数,约为0.5772.
     * 1 1/2 1/3 1/4 1/5
     *
     * @param current
     * @param base
     * @return
     */
    private float caculateZhangli(int current, int base) {
        float signum = Math.signum(current);
        int pullrate = Math.abs(current) / base;
        if (pullrate == 0) {
            return current;
        } else if (pullrate == 1)
            return (signum * base + signum * (Math.abs(current) % base) / 3);
        else if (pullrate == 2) {
            return (1.333333333f * signum * base + signum * (Math.abs(current) % base) / 4);
        } else if (pullrate == 3) {
            return (1.583333333f * signum * base + signum * (Math.abs(current) % base) / 5);
        } else {
            return (1.783333333f * signum * base + signum * (Math.abs(current) % base) / 6);
        }
    }

    @Override
    public RefreshMode getMode() {
        return RefreshMode.ELASTIC;
    }
}
