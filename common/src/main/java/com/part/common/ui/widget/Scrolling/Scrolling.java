package com.part.common.ui.widget.Scrolling;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import static android.support.v4.view.ViewCompat.TYPE_TOUCH;
import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

/**
 * by ckckck 2018/12/13
 * <p> 支持多指手势 参考nestedScrollview
 * life is short , bugs are too many!
 */
public abstract class Scrolling extends FrameLayout implements NestedScrollingChild2 {
    public static final String TAG = "Scrolling";
    private static final int SCROLL_STAT_IDLE = 0;
    private static final int SCROLL_STAT_SCROLLING = 1;

    private NestedScrollingChildHelper mChildHelper;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];
    private final int[] mNestedOffsets = new int[2];
    private final int[] mInterralScrollOffset = new int[2];

    private VelocityTracker mVelocityTracker;
    private ViewFlinger mViewFlinger = new ViewFlinger();
    private OverScroller mScroller;

    private int mScrollState = SCROLL_STAT_IDLE;
    protected ScrollDirection direction = ScrollDirection.Y;

    private int mMaxFlingVelocity;
    private int mMinFlingVelocity;
    private int mTouchSlop;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mLastX;
    private int mLastY;
    private boolean mIsDraging;
    private int mActivePointerId = INVALID_POINTER;

    public Scrolling(Context context) {
        this(context, null);
    }

    public Scrolling(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Scrolling(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mChildHelper = new NestedScrollingChildHelper(this);
        mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        setNestedScrollingEnabled(true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        final int actionIndex = ev.getActionIndex();
        System.out.println(action+"--"+mIsDraging);
        if ((action == MotionEvent.ACTION_MOVE) && mIsDraging) {
            return true;
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                final int index = ev.findPointerIndex(mActivePointerId);
                if (index < 0) {
                    // If we don't have a valid id, the touch down wasn't on content.
                    return false;
                }

                if (!mIsDraging) {
                    int dx = (int) (ev.getX(index)+0.5f);
                    int dy = (int) (ev.getY(index)+0.5f);
                    float absY = Math.abs(mLastY - dy);
                    float absX = Math.abs(mLastX - dx);
                    float offset = direction == ScrollDirection.Y ? absY : absX;
                    if (/*(direction == ScrollDirection.Y ? absY > absX : absY < absX) && */offset > mTouchSlop) {
                        // Start scrolling!
                        mLastX = dx;
                        mLastY = dy;
                        mIsDraging=true;
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
                mActivePointerId = ev.getPointerId(actionIndex);
                mInitialTouchX = mLastX = (int) (ev.getX(actionIndex) + 0.5f);
                mInitialTouchY = mLastY = (int) (ev.getY(actionIndex) + 0.5f);
                break;
            case MotionEvent.ACTION_DOWN:
                if (getScrollState() == SCROLL_STAT_SCROLLING) {
                    stopScroll();
                }
                mIsDraging = false;
                // Clear the nested offsets
                mNestedOffsets[0] = mNestedOffsets[1] = 0;
                mActivePointerId = ev.getPointerId(0);
                mLastX = (int) (ev.getX() + 0.5f);
                mLastY = (int) (ev.getY() + 0.5f);
                startNestedScroll(getAxis(), TYPE_TOUCH);
                break;
            case MotionEvent.ACTION_UP: {
                mIsDraging = false;
                mActivePointerId = INVALID_POINTER;
                mVelocityTracker.clear();
                stopNestedScroll(TYPE_TOUCH);
            }
            break;

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER;
                mVelocityTracker.clear();
                mIsDraging = false;
                stopNestedScroll(TYPE_TOUCH);
                setScrollState(SCROLL_STAT_IDLE);
            }
            case MotionEvent.ACTION_POINTER_UP:
                onPointerUp(ev);
                break;
        }
        return mIsDraging;
    }

    private void onPointerUp(MotionEvent e) {
        final int actionIndex = e.getActionIndex();
        if (e.getPointerId(actionIndex) == mActivePointerId) {
            // Pick a new pointer to pick up the slack.
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mActivePointerId = e.getPointerId(newIndex);
            mInitialTouchX = mLastX = (int) (e.getX(newIndex) + 0.5f);
            mInitialTouchY = mLastY = (int) (e.getY(newIndex) + 0.5f);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final MotionEvent vtev = MotionEvent.obtain(ev);
        final int action = ev.getActionMasked();
        final int actionIndex = ev.getActionIndex();

        if (action == MotionEvent.ACTION_DOWN) {
            mNestedOffsets[0] = mNestedOffsets[1] = 0;
        }
        vtev.offsetLocation(mNestedOffsets[0], mNestedOffsets[1]);
        boolean eventAddedToVelocityTracker = false;
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        boolean canScrollHorizontally = (direction == ScrollDirection.X) || (direction == ScrollDirection.XY);
        boolean canScrollVertically = (direction == ScrollDirection.Y) || (direction == ScrollDirection.XY);
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                final int index = ev.findPointerIndex(mActivePointerId);
                if (index < 0) {
                    Log.e(TAG, "Error processing scroll; pointer index for id "
                            + mActivePointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }

                final int x = (int) (ev.getX(index) + 0.5f);
                final int y = (int) (ev.getY(index) + 0.5f);
                int dx = mLastX - x;
                int dy = mLastY - y;

                final int initdx=dx;
                final int initdy=dy;

                // nested pre scroll
                if (dispatchNestedPreScroll(dx, dy, mScrollConsumed, mScrollOffset, ViewCompat.TYPE_TOUCH)) {
                    dx -= mScrollConsumed[0];
                    dy -= mScrollConsumed[1];
                    vtev.offsetLocation(mScrollOffset[0], mScrollOffset[1]);
                    // Updated the nested offsets
                    mNestedOffsets[0] += mScrollOffset[0];
                    mNestedOffsets[1] += mScrollOffset[1];
                }
                if (!mIsDraging) {
                    if (canScrollHorizontally && Math.abs(dx) > mTouchSlop) {
                        if (dx > 0) {
                            dx -= mTouchSlop;
                        } else {
                            dx += mTouchSlop;
                        }
                        mIsDraging = true;
                    }
                    if (canScrollVertically && Math.abs(dy) > mTouchSlop) {
                        if (dy > 0) {
                            dy -= mTouchSlop;
                        } else {
                            dy += mTouchSlop;
                        }
                        mIsDraging = true;
                    }
                }
                if (mIsDraging) {
                    mLastX = x - mScrollOffset[0];
                    mLastY = y - mScrollOffset[1];
                }

                // internal scroll
                mInterralScrollOffset[0] = 0;
                mInterralScrollOffset[1] = 0;
                scrollXY(dx, dy, mInterralScrollOffset, false);

                dx -= mInterralScrollOffset[0];
                dy -= mInterralScrollOffset[1];
                if (dispatchNestedScroll(initdx-dx, initdy-dy, dx,dy,mScrollOffset, ViewCompat.TYPE_TOUCH)) {
                    mLastX -= mScrollOffset[0];
                    mLastY -= mScrollOffset[1];
                    vtev.offsetLocation(mScrollOffset[0], mScrollOffset[1]);
                    mNestedOffsets[0] += mScrollOffset[0];
                    mNestedOffsets[1] += mScrollOffset[1];
                }
                if (mInterralScrollOffset[0] != 0 && mInterralScrollOffset[1] != 0) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if (getScrollState() == SCROLL_STAT_SCROLLING) {
                    stopScroll();
                }
                mActivePointerId = ev.getPointerId(0);
                mInitialTouchX = mLastX = (int) (ev.getX() + 0.5f);
                mInitialTouchY = mLastY = (int) (ev.getY() + 0.5f);
                mActivePointerId = ev.getPointerId(0);
                startNestedScroll(getAxis(), TYPE_TOUCH);
                break;
            case MotionEvent.ACTION_UP:
                mActivePointerId = INVALID_POINTER;
                eventAddedToVelocityTracker = true;
                mVelocityTracker.addMovement(vtev);
                mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                onFlingXY(direction == ScrollDirection.Y ? 0 : (int) -mVelocityTracker.getXVelocity(), direction == ScrollDirection.X ? 0 : (int) -mVelocityTracker.getYVelocity());
                mVelocityTracker.clear();
                stopNestedScroll(ViewCompat.TYPE_TOUCH);
                break;
            case MotionEvent.ACTION_CANCEL:
                mActivePointerId = INVALID_POINTER;
                mVelocityTracker.clear();
                stopNestedScroll(ViewCompat.TYPE_TOUCH);
                setScrollState(SCROLL_STAT_IDLE);
                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                mActivePointerId = ev.getPointerId(actionIndex);
                mInitialTouchX = mLastX = (int) (ev.getX(actionIndex) + 0.5f);
                mInitialTouchY = mLastY = (int) (ev.getY(actionIndex) + 0.5f);
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                onPointerUp(ev);
                break;
        }
        if (!eventAddedToVelocityTracker) {
            mVelocityTracker.addMovement(vtev);
        }
        vtev.recycle();
        return true;
    }

    private int getAxis() {
        int axis = ViewCompat.SCROLL_AXIS_NONE;
        if (direction == ScrollDirection.Y) {
            axis |= ViewCompat.SCROLL_AXIS_VERTICAL;
        } else if (direction == ScrollDirection.X) {
            axis |= ViewCompat.SCROLL_AXIS_HORIZONTAL;
        } else {
            axis |= ViewCompat.SCROLL_AXIS_HORIZONTAL;
            axis |= ViewCompat.SCROLL_AXIS_VERTICAL;
        }
        return axis;
    }

    private void onFlingXY(int vx, int vy) {
        if (direction == ScrollDirection.Y || Math.abs(vx) < mMinFlingVelocity) {
            vx = 0;
        }
        if (direction == ScrollDirection.X || Math.abs(vy) < mMinFlingVelocity) {
            vy = 0;
        }
        if (vx == 0 && vy == 0) {
            return;
        }
        if (!dispatchNestedPreFling(vx, vy)) {
//            dispatchNestedFling(vx, vy, true);
            if (!canFling(vx, vy)) {
                return;
            }
            if (mScroller == null) {
                mScroller = new OverScroller(getContext(), sQuinticInterpolator);
            }
            mScroller.fling(0, 0, vx, vy, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            startNestedScroll(getAxis(), ViewCompat.TYPE_NON_TOUCH);
            setScrollState(SCROLL_STAT_SCROLLING);

            mViewFlinger.reset();
            ViewCompat.postOnAnimation(this, mViewFlinger);
        }
    }

    protected boolean canFling(int vx, int vy) {
        return true;
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return mChildHelper.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        mChildHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return mChildHelper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopScroll();
    }

    private synchronized void setScrollState(int state) {
        mScrollState = state;
    }

    private synchronized int getScrollState() {
        return mScrollState;
    }

    public void stopScroll() {
        if (!mViewFlinger.isFinish())
            mViewFlinger.stop();
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return true;
    }


    class ViewFlinger implements Runnable {
        private int mLastY = 0;
        private int mLastX = 0;
        int[] scrolledXY = new int[2];

        public void reset() {
            mLastY = 0;
            mLastX = 0;
        }

        @Override
        public void run() {
            if (getScrollState() == SCROLL_STAT_IDLE) {
                return;
            }

            if (mScroller.isFinished() || !mScroller.computeScrollOffset()) {
                setScrollState(SCROLL_STAT_IDLE);
                onFlingFinished();
            } else {
                int curX = mScroller.getCurrX();
                int curY = mScroller.getCurrY();
                int deltaX = curX - mLastX;
                int deltaY = curY - mLastY;
                mLastX = curX;
                mLastY = curY;

                // nested pre scroll
                if (dispatchNestedPreScroll(deltaX, deltaY, mScrollConsumed, mScrollOffset, ViewCompat.TYPE_NON_TOUCH)) {
                    deltaX -= mScrollConsumed[0];
                    deltaY -= mScrollConsumed[1];
                }

                // internal scroll
                scrolledXY[0] = 0;
                scrolledXY[1] = 0;
                scrollXY(deltaX, deltaY, scrolledXY, true);
                deltaX -= scrolledXY[0];
                deltaY -= scrolledXY[1];

                // nested scroll
                dispatchNestedScroll(mScrollConsumed[0] + scrolledXY[0], mScrollConsumed[1] + scrolledXY[1], deltaX, deltaY, mScrollOffset, ViewCompat.TYPE_NON_TOUCH);

                ViewCompat.postOnAnimation(Scrolling.this, this);
            }
        }

        public boolean isFinish() {
            return mScroller == null || mScroller.isFinished();
        }

        public void stop() {
            removeCallbacks(this);
            mScroller.abortAnimation();
            setScrollState(SCROLL_STAT_IDLE);
        }
    }

    protected abstract void scrollXY(int deltaX, int deltaY, int[] scrolledXY, boolean fling);

    /**
     * fling 没被停止的完成
     */
    protected void onFlingFinished() {
    }

    public ScrollDirection getDirection() {
        return direction;
    }

    public void setDirection(ScrollDirection direction) {
        this.direction = direction;
    }

    static final Interpolator sQuinticInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

}
