package com.common.common.ui.widget.Scrolling.MixScroll.Base;

import android.view.View;

import com.common.common.ui.widget.Scrolling.ScrollDirection;
import com.common.common.ui.widget.Scrolling.Scrolling;


/**
 * by ckckck 2018/12/28
 * <p>
 * life is short , bugs are too many!
 */
public interface Refreshable {

    /**
     * 布局方式
     * @param direction
     * @param parent
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    void onLayout(ScrollDirection direction, Scrolling parent, boolean changed, int left, int top, int right, int bottom);

    /**
     * 是头部还是尾部
     * @return
     */
    boolean isHeader();

    /**
     * view实体
     * @return
     */
    View getContentView();

    /**
     * 下拉距离监听
     * @param pull
     * @param fling
     */
    void onPull(float pull, boolean fling);

    /**
     * 状态监听
     * @param state
     */
    void onStateChange(RefreshState state);


    /**
     * 可下拉的距离
     * @return
     */
    int canPullSpace();


    /**
     * 刷新的临界点
     * @return
     */
    int canRefreshSpace();

    /**
     * 二楼的停滞点
     * @return
     */
    int secondFloorSpace();

    /**
     * 进入二楼的临界值,大于canRefreshSpace()刷新临界值才生效
     * @return
     */
    int canSecondFloorSpace();
}
