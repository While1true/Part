package com.part.common.ui.widget.ViewPager;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by vange on 2017/12/6.
 * <p>
 * 有
 */

public abstract class LoopFragmentPagerAdapter extends FragmentStatePagerAdapter implements LifecycleObserver {
    /**
     * 用来启动动画 设置循环
     */
    private boolean isfirst = true;

    /**
     * 触摸的时候停止切换
     */
    private boolean touched = false;

    /**
     * 切换间隔
     */
    private int switchPeriod = 5000;

    /**
     * 自动切换
     */
    private boolean autoSwitch = true;

    /**
     * 是否需要无限循环
     */
    private boolean loop = true;

    /**
     * post runnable
     */
    private Runnable action;

    private int BIGCOUNT = 1000000;


    private boolean reverse;
    private ViewGroup container;

    public LoopFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return getActualItem(position % getActualCount());
    }

    @Override
    public void startUpdate(final ViewGroup container) {
        super.startUpdate(container);
        if(this.container==null) {
            this.container = container;
            onResume();
        }
    }

    /**
     * 反射设置一个很大的位置达到伪无限循环
     *
     * @param container
     * @throws Exception
     */
    protected void doSettCurrenPager(ViewGroup container) throws Exception {
        Field mCurItem = ViewPager.class.getDeclaredField("mCurItem");
        mCurItem.setAccessible(true);
        mCurItem.setInt(container, 1000000 - (BIGCOUNT % getActualCount()));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getActualPagerTitle(position % getActualCount());
    }

    @Override
    public int getCount() {
        return loop ? Integer.MAX_VALUE : getActualCount();
    }


    /**
     * 执行循环切换
     *
     * @param viewPager
     */
    @SuppressLint("ClickableViewAccessibility")
    private void startLoop(final ViewPager viewPager) {
        usePost(viewPager);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        if (!touched) {
                            touched = true;
                            viewPager.removeCallbacks(action);
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        touched = false;
                        viewPager.postDelayed(action, switchPeriod);
                        break;
                }
                return false;
            }
        });

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        if(container==null) {
            return;
        }
        if (loop && isfirst) {
            isfirst = false;
            try {
                doSettCurrenPager(container);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (autoSwitch) {
           startLoop((ViewPager) container);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        container.removeCallbacks(action);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestory() {
        container.removeCallbacks(action);
        action = null;
        container.setOnTouchListener(null);
        container = null;
    }


    /**
     * 用View.post作循环切换
     *
     * @param container
     */
    private void usePost(final ViewPager container) {
        if (action == null) {
            action = new Runnable() {
                @Override
                public void run() {
                    container.setCurrentItem(caculatItem(container));
                    container.postDelayed(this, switchPeriod);
                }
            };
        }

        container.postDelayed(action, switchPeriod);
    }

    private int caculatItem(ViewPager viewPager) {
        int nextItem = !reverse ? (viewPager.getCurrentItem() + 1) : (viewPager.getCurrentItem() - 1);
        if (!loop) {
            if (nextItem >= getActualCount()) {
                reverse = true;
                nextItem = viewPager.getCurrentItem() - 1;
            }
            if (nextItem < 0) {
                reverse = false;
                nextItem = 1;
            }
        }
        return nextItem;
    }

    public boolean isLoop() {
        return loop;
    }

    public LoopFragmentPagerAdapter addLifeOwner(LifecycleOwner owner) {
        owner.getLifecycle().addObserver(this);
        return this;
    }

    public LoopFragmentPagerAdapter setLoop(boolean loop) {
        this.loop = loop;
        return this;
    }

    public int getSwitchPeriod() {
        return switchPeriod;
    }

    public LoopFragmentPagerAdapter setSwitchPeriod(int switchPeriod) {
        this.switchPeriod = switchPeriod;
        return this;
    }

    public boolean isAutoSwitch() {
        return autoSwitch;
    }

    public LoopFragmentPagerAdapter setAutoSwitch(boolean autoSwitch) {
        this.autoSwitch = autoSwitch;
        return this;
    }

    public abstract int getActualCount();

    public abstract Fragment getActualItem(int position);

    public abstract CharSequence getActualPagerTitle(int position);
}
