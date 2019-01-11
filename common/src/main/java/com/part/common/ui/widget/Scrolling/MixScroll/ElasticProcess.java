package com.part.common.ui.widget.Scrolling.MixScroll;

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
        if (!isSupportFling&&fling) {
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
        if (!isSupportFling&&fling) {
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
        return new EmptyHeaderFooter(group.getContext(),true);
    }

    @Override
    public Refreshable getFooter(ViewGroup group) {
        return new EmptyHeaderFooter(group.getContext(),false);
    }


    private final void onHeaderVirtical(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        int scrollY = mixScrolling.getScrollYY();
        float strength = mixScrolling.getStrength();
        Refreshable header = mixScrolling.getHeader();
        int canPullSpace = header.canPullSpace();
        //下滑
        if (remain < 0) {
            canPullSpace = Math.max(0, canPullSpace + scrollY);
            if (canPullSpace != 0) {
                float virtualPull = remain / strength;
                float min = Math.min(-virtualPull, canPullSpace);
                scrolledXY[1] -= min * strength;
                mixScrolling.scrollBy(0, (int) -min);

                scrollY= mixScrolling.getScrollYY();
                mixScrolling.setPivotX(0);
                mixScrolling.setPivotY(0);
                mixScrolling.setScaleY((float) Math.min(1.5f, 1f +Math.sqrt(-scrollY)/ (float) header.canPullSpace()));
            } else {
                if (fling) {
                    mixScrolling.stopScroll();
                    mixScrolling.startAnimation();
                }
            }
        } else {
            int min = Math.min(-scrollY, remain);
            mixScrolling.scrollBy(0, min);
            scrolledXY[1] += min;

            scrollY= mixScrolling.getScrollYY();
            mixScrolling.setPivotX(0);
            mixScrolling.setPivotY(0);
            mixScrolling.setScaleY((float) Math.min(1.5f, 1f +Math.sqrt(-scrollY)/ (float) header.canPullSpace()));
        }

    }

    private final void onHeaderHoriztional(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        int scrollX = mixScrolling.getScrollXX();
        float strength = mixScrolling.getStrength();
        Refreshable header = mixScrolling.getHeader();
        int canPullSpace = header.canPullSpace();
        //下滑
        if (remain < 0) {
            canPullSpace = Math.max(0, canPullSpace + scrollX);
            if (canPullSpace != 0) {
                float virtualPull = remain / strength;
                float min = Math.min(-virtualPull, canPullSpace);
                mixScrolling.scrollBy(-(int) min, 0);
                scrolledXY[0] -= min * strength;

                scrollX= mixScrolling.getScrollXX();
                mixScrolling.setPivotX(0);
                mixScrolling.setPivotY(0);
                mixScrolling.setScaleY((float) Math.min(1.5f, 1f +Math.sqrt(-scrollX)/ (float) header.canPullSpace()));

            } else {
                if (fling) {
                    mixScrolling.stopScroll();
                    mixScrolling.startAnimation();
                }
            }
        } else {
            int min = Math.min(-scrollX, remain);
            mixScrolling.scrollBy(min, 0);
            scrolledXY[0] += min;

            scrollX= mixScrolling.getScrollXX();
            mixScrolling.setPivotX(0);
            mixScrolling.setPivotY(0);
            mixScrolling.setScaleY((float) Math.min(1.5f, 1f +Math.sqrt(-scrollX)/ (float) header.canPullSpace()));

        }
    }

    private final void onFooterVirtical(MixScrolling mixScrolling, int remain, int[] scrolledXY, boolean fling) {
        int scrollY = mixScrolling.getScrollYY();
        float strength = mixScrolling.getStrength();
        Refreshable footer = mixScrolling.getFooter();
        int canPullSpace = footer.canPullSpace();
        //下滑
        if (remain < 0) {
            int min = Math.min(scrollY, -remain);
            mixScrolling.scrollBy(0, -min);
            scrolledXY[1] -= min;

            scrollY= mixScrolling.getScrollYY();
            mixScrolling.setPivotX(0);
            mixScrolling.setPivotY(mixScrolling.getMeasuredHeight());
            mixScrolling.setScaleY((float) Math.min(1.5f, 1f +Math.sqrt(scrollY)/ (float) footer.canPullSpace()));

        } else {
            canPullSpace = Math.max(0, canPullSpace - scrollY);
            if (canPullSpace != 0) {
                float virtualPull = remain / strength;
                float min = Math.min(virtualPull, canPullSpace);
                mixScrolling.scrollBy(0, (int) min);
                scrolledXY[1] += min * strength;

                scrollY= mixScrolling.getScrollYY();
                mixScrolling.setPivotX(0);
                mixScrolling.setPivotY(mixScrolling.getMeasuredHeight());
                mixScrolling.setScaleY((float) Math.min(1.5f, 1f +Math.sqrt(scrollY)/ (float) footer.canPullSpace()));
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
        int canPullSpace = footer.canPullSpace();
        //下滑
        if (remain < 0) {
            int min = Math.min(scrollX, -remain);
            mixScrolling.scrollBy(-min, 0);
            scrolledXY[0] -= min;

            scrollX= mixScrolling.getScrollXX();
            mixScrolling.setPivotX(0);
            mixScrolling.setPivotY(mixScrolling.getMeasuredHeight());
            mixScrolling.setScaleY((float) Math.min(1.5f, 1f +Math.sqrt(scrollX)/ (float) footer.canPullSpace()));

        } else {
            canPullSpace = Math.max(0, canPullSpace - scrollX);
            if (canPullSpace != 0) {
                float virtualPull = remain / strength;
                float min = Math.min(virtualPull, canPullSpace);
                mixScrolling.scrollBy((int) min, 0);
                scrolledXY[0] += min * strength;

                scrollX= mixScrolling.getScrollXX();
                mixScrolling.setPivotX(0);
                mixScrolling.setPivotY(mixScrolling.getMeasuredHeight());
                mixScrolling.setScaleY((float) Math.min(1.5f, 1f +Math.sqrt(scrollX)/ (float) footer.canPullSpace()));

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
    private int caculateZhangli(int current, int base) {
        float signum = Math.signum(current);
        int pullrate = Math.abs(current) / base;
        if (pullrate == 0) {
            return current;
        } else if (pullrate == 1)
            return (int) (signum * base + signum * (Math.abs(current) % base) / 3);
        else if (pullrate == 2) {
            return (int) (1.333333333f * signum * base + signum * (Math.abs(current) % base) / 4);
        }
        else if (pullrate == 3) {
            return (int) (1.583333333f * signum * base + signum * (Math.abs(current) % base) / 5);
        } else {
            return (int) (1.783333333f * signum * base + signum * (Math.abs(current) % base) / 6);
        }
    }

    @Override
    public RefreshMode getMode() {
        return RefreshMode.ELASTIC;
    }
}
