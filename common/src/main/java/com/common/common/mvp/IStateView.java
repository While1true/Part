package com.common.common.mvp;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public interface IStateView extends IView {
    void showEmpty();
    void showError();
    void showContent();
}
