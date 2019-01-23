package com.part.common.ui.widget.Scrolling.MixScroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.part.common.ui.widget.Scrolling.MixScroll.Base.RefreshState;
import com.part.common.ui.widget.Scrolling.ScrollDirection;

import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

/**
 * by ckckck 2019/1/18
 * <p>mixScroll 必须为 scrollviewbehavior的直接层级view才支持
 * life is short , bugs are too many!
 */
public class MixBehavior extends AppBarLayout.Behavior implements ViewPager.OnPageChangeListener {
    public static final String TAG = "MixBehavior";
    private final int[] mInterralScrollOffset = new int[2];
    private int mLastX;
    private int mLastY;
    private boolean mIsDraging;
    private int mActivePointerId = INVALID_POINTER;
    private int mTouchSlop = new ViewConfiguration().getScaledTouchSlop();
    private MixScrolling mixScrolling;
    private ViewPager viewPager;

    public MixBehavior() {
        super();
    }

    public MixBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        findMixScrollInCoordinatorLayout(parent, child);
        if (mixScrolling != null && mixScrolling.getRefreshState() != RefreshState.REFRESHING && onTouch(ev, child))
            return true;
        return super.onTouchEvent(parent, child, ev);
    }

    private boolean onTouch(MotionEvent ev, AppBarLayout appBarLayout) {
        final int action = ev.getActionMasked();
        final int actionIndex = ev.getActionIndex();
        ScrollDirection direction = mixScrolling.getDirection();
        handleAnimation(ev);
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                final int index = ev.findPointerIndex(mActivePointerId);
                if (index < 0) {
                    Log.e(TAG, "Error processing scroll; pointer index for id "
                            + mActivePointerId + " not found. Did any MotionEvents get skipped?");
                    return true;
                }
                int x = (int) (ev.getX(index) + 0.5f);
                int y = (int) (ev.getY(index) + 0.5f);
                int dx = mLastX - x;
                int dy = mLastY - y;
                float offset = direction == ScrollDirection.Y ? dy : dx;

                if (!canScroll((int) offset)) {
                    return false;
                }
                if (!mIsDraging && Math.abs(offset) > mTouchSlop) {
                    // Start scrolling!
                    mLastX = x;
                    mLastY = y;
                    mIsDraging = true;
                }
                if (mIsDraging) {
                    // internal scroll
                    mLastX = x;
                    mLastY = y;
                    mInterralScrollOffset[0] = 0;
                    mInterralScrollOffset[1] = 0;
                    mixScrolling.scrollXY(dx, dy, mInterralScrollOffset, false);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                mIsDraging = false;
                mActivePointerId = ev.getPointerId(0);
                mLastX = (int) (ev.getX() + 0.5f);
                mLastY = (int) (ev.getY() + 0.5f);
                mActivePointerId = ev.getPointerId(0);
                break;
            case MotionEvent.ACTION_UP:
                mActivePointerId = INVALID_POINTER;
                mIsDraging = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                mIsDraging = false;
                mActivePointerId = INVALID_POINTER;
                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                mActivePointerId = ev.getPointerId(actionIndex);
                mLastX = (int) (ev.getX(actionIndex) + 0.5f);
                mLastY = (int) (ev.getY(actionIndex) + 0.5f);
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                onPointerUp(ev);
                break;
        }
        return true;
    }

    private void handleAnimation(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mixScrolling.startAnimation();
                break;
            case MotionEvent.ACTION_DOWN:
                mixScrolling.cancelAnimation();
                break;
        }
    }

    private void findMixScrollInCoordinatorLayout(CoordinatorLayout parent, AppBarLayout appBarLayout) {
        if (mixScrolling == null) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childTemp = parent.getChildAt(i);
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) childTemp.getLayoutParams();
                CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
                if (behavior instanceof AppBarLayout.ScrollingViewBehavior) {
                    if (childTemp instanceof MixScrolling) {
                        mixScrolling = (MixScrolling) childTemp;
                        break;
                    } else if (childTemp instanceof ViewPager) {
                        viewPager = (ViewPager) childTemp;
                        viewPager.addOnPageChangeListener(this);
                        View child = viewPager.getChildAt(viewPager.getCurrentItem());
                        if (child instanceof MixScrolling) {
                            mixScrolling = (MixScrolling) child;
                        } else {
                            findDirectNestMixscroll(child);
                        }
                    } else {
                        findDirectNestMixscroll(childTemp);
                    }
                }

            }
        }
    }

    private void findDirectNestMixscroll(View childTemp) {
        if (childTemp instanceof ViewGroup) {
            int childCount1 = ((ViewGroup) childTemp).getChildCount();
            for (int i1 = 0; i1 < childCount1; i1++) {
                View child = ((ViewGroup) childTemp).getChildAt(i1);
                if (child instanceof MixScrolling) {
                    mixScrolling = (MixScrolling) child;
                    break;
                }
            }
        }
    }

    public boolean onInterceptTouch(MotionEvent ev, AppBarLayout appBarLayout) {
        ScrollDirection direction = mixScrolling.getDirection();
        final int action = ev.getActionMasked();
        final int actionIndex = ev.getActionIndex();
        if ((action == MotionEvent.ACTION_MOVE) && mIsDraging) {
            return true;
        }
        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                final int index = ev.findPointerIndex(mActivePointerId);
                if (index < 0) {
                    // If we don't have a valid id, the touch down wasn't on content.
                    return false;
                }

                if (!mIsDraging) {
                    int dx = (int) (ev.getX(index) + 0.5f);
                    int dy = (int) (ev.getY(index) + 0.5f);
                    float offY = mLastY - dy;
                    float offX = mLastX - dx;
                    float offset = direction == ScrollDirection.Y ? offY : offX;
                    if (Math.abs(offset) > mTouchSlop && canScroll((int) offset)) {
                        // Start scrolling!
                        mLastX = dx;
                        mLastY = dy;
                        mIsDraging = true;
                        appBarLayout.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
                mActivePointerId = ev.getPointerId(actionIndex);
                mLastX = (int) (ev.getX(actionIndex) + 0.5f);
                mLastY = (int) (ev.getY(actionIndex) + 0.5f);
                break;
            case MotionEvent.ACTION_DOWN:
                mIsDraging = false;
                // Clear the nested offsets
                mActivePointerId = ev.getPointerId(0);
                mLastX = (int) (ev.getX() + 0.5f);
                mLastY = (int) (ev.getY() + 0.5f);
                break;
            case MotionEvent.ACTION_UP: {
                mIsDraging = false;
                mActivePointerId = INVALID_POINTER;
            }
            break;

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER;
                mIsDraging = false;
            }
            case MotionEvent.ACTION_POINTER_UP:
                onPointerUp(ev);
                break;
        }
        return mIsDraging;
    }

    private boolean canScroll(int dy) {
        return mixScrolling.getScroll() != 0 || (mixScrolling.getScroll() == 0 && dy < 0);
    }

    private void onPointerUp(MotionEvent e) {
        final int actionIndex = e.getActionIndex();
        if (e.getPointerId(actionIndex) == mActivePointerId) {
            // Pick a new pointer to pick up the slack.
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mActivePointerId = e.getPointerId(newIndex);
            mLastX = (int) (e.getX(newIndex) + 0.5f);
            mLastY = (int) (e.getY(newIndex) + 0.5f);
        }
    }

    public void setUpMixscroll(MixScrolling mixscroll) {
        this.mixScrolling = mixscroll;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (viewPager != null) {
            View child = viewPager.getChildAt(i);
            if (child instanceof MixScrolling) {
                mixScrolling = (MixScrolling) child;
            } else {
                findDirectNestMixscroll(child);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

}
