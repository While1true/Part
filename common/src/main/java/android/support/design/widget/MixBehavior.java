package android.support.design.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import com.part.common.ui.widget.Scrolling.MixScroll.Base.RefreshState;
import com.part.common.ui.widget.Scrolling.MixScroll.MixScrolling;


/**
 * by ckckck 2019/1/18
 * <p>mixScroll 必须为 scrollviewbehavior的直接层级view才支持
 * life is short , bugs are too many!
 */
public class MixBehavior extends AppBarLayout.Behavior implements ViewPager.OnPageChangeListener {

    private static final int INVALID_POINTER = -1;
    int[] temp = new int[2];
    private boolean mIsBeingDragged;
    private int mActivePointerId = INVALID_POINTER;
    private int mLastMotionY;
    private int mTouchSlop = -1;
    private VelocityTracker mVelocityTracker;
    private Runnable mFlingRunnable;
    OverScroller mScroller;
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
        if (mTouchSlop < 0) {
            mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        findMixScrollInCoordinatorLayout(parent, child);
        handleAnimation(ev);
        final int action = ev.getAction();

        // Shortcut since we're being dragged
        if (action == MotionEvent.ACTION_MOVE && mIsBeingDragged) {
            return true;
        }

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                mIsBeingDragged = false;
                final int x = (int) ev.getX();
                final int y = (int) ev.getY();
                if (canDragView(child) && parent.isPointInChildBounds(child, x, y)) {
                    mLastMotionY = y;
                    mActivePointerId = ev.getPointerId(0);
                    ensureVelocityTracker();
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int activePointerId = mActivePointerId;
                if (activePointerId == INVALID_POINTER) {
                    // If we don't have a valid id, the touch down wasn't on content.
                    break;
                }
                final int pointerIndex = ev.findPointerIndex(activePointerId);
                if (pointerIndex == -1) {
                    break;
                }

                final int y = (int) ev.getY(pointerIndex);
                final int yDiff = Math.abs(y - mLastMotionY);
                if (yDiff > mTouchSlop) {
                    mIsBeingDragged = true;
                    mLastMotionY = y;
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            }
        }

        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(ev);
        }

        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, AppBarLayout child, MotionEvent ev) {
        if (mTouchSlop < 0) {
            mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        handleAnimation(ev);
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                final int x = (int) ev.getX();
                final int y = (int) ev.getY();

                if (parent.isPointInChildBounds(child, x, y) && canDragView(child)) {
                    mLastMotionY = y;
                    mActivePointerId = ev.getPointerId(0);
                    ensureVelocityTracker();
                } else {
                    return false;
                }
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
                if (activePointerIndex == -1) {
                    return false;
                }

                final int y = (int) ev.getY(activePointerIndex);
                int dy = mLastMotionY - y;

                if (!mIsBeingDragged && Math.abs(dy) > mTouchSlop) {
                    mIsBeingDragged = true;
                    if (dy > 0) {
                        dy -= mTouchSlop;
                    } else {
                        dy += mTouchSlop;
                    }
                }

                if (mIsBeingDragged) {
                    mLastMotionY = y;
                    // We're being dragged so scroll the ABL
                    int scroll =0;
                    if(mixScrolling.getScroll()==0||mixScrolling.getRefreshState()==RefreshState.REFRESHING) {
                        scroll = scroll(parent, child, dy, getMaxDragOffset(child), 0);
                    }
                    if(dy-scroll!=0) {
                        mixScrolling.scrollXY(0, dy-scroll, temp, false);
                    }
                }
                break;
            }

            case MotionEvent.ACTION_UP:
                if (mVelocityTracker != null) {
                    mVelocityTracker.addMovement(ev);
                    mVelocityTracker.computeCurrentVelocity(1000);
                    float yvel = mVelocityTracker.getYVelocity(mActivePointerId);
                    flingX(parent, child, -getScrollRangeForDragFling(child)-300000, 0, yvel);
                }
                // $FALLTHROUGH
            case MotionEvent.ACTION_CANCEL: {
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                break;
            }
        }

        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(ev);
        }

        return true;
    }
    final boolean flingX(CoordinatorLayout coordinatorLayout, AppBarLayout layout, int minOffset,
                        int maxOffset, float velocityY) {
        if (mFlingRunnable != null) {
            layout.removeCallbacks(mFlingRunnable);
            mFlingRunnable = null;
        }

        if (mScroller == null) {
            mScroller = new OverScroller(layout.getContext());
        }
        flingLastY=getTopAndBottomOffset();
        mScroller.fling(
                0,flingLastY, // curr
                0, Math.round(velocityY), // velocity.
                0, 0, // x
                minOffset, maxOffset); // y

        if (mScroller.computeScrollOffset()) {
            mFlingRunnable = new FlingRunnable(coordinatorLayout, layout);
            ViewCompat.postOnAnimation(layout, mFlingRunnable);
            return true;
        } else {
            onFlingFinished(coordinatorLayout, layout);
            return false;
        }
    }
    private int flingLastY;
    private class FlingRunnable implements Runnable {
        private final CoordinatorLayout mParent;
        private final AppBarLayout mLayout;

        FlingRunnable(CoordinatorLayout parent, AppBarLayout layout) {
            mParent = parent;
            mLayout = layout;
        }

        @Override
        public void run() {
            if (mLayout != null && mScroller != null) {
                if (mScroller.computeScrollOffset()) {
                    int currY = mScroller.getCurrY();
                    int dy=flingLastY-currY;
                    int scroll = scroll(mParent, mLayout, dy, getMaxDragOffset(mLayout), 0);
                    if(mixScrolling!=null&&dy-scroll!=0){
                        temp[1]=0;
                        mixScrolling.scrollXY(0,dy-scroll,temp,true);
                        if(temp[1]==0){
                            mScroller.forceFinished(true);
                            onFlingFinished(mParent, mLayout);
                            return;
                        }
                    }

                    flingLastY=currY;
                    // Post ourselves so that we run on the next animation
                    ViewCompat.postOnAnimation(mLayout, this);
                } else {
                    onFlingFinished(mParent, mLayout);
                }
            }
        }
    }
    private void ensureVelocityTracker() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void handleAnimation(MotionEvent ev) {
        if (mixScrolling == null)
            return;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mixScrolling.startAnimation();
                break;
            case MotionEvent.ACTION_DOWN:
                mixScrolling.cancelAnimation();
                if(mScroller!=null) {
                    mScroller.forceFinished(true);
                }
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
