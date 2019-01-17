package com.part.basemoudle.mvp.presenter;

import com.part.common.mvp.IPresenter;
import com.part.common.mvp.IView;

import javax.inject.Inject;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public class HelloPresenter implements IPresenter {
    @Inject
    protected IView iView;
    @Inject
    HelloPresenter(){}

    public String sayHello(){
        iView.showLoading();
        return "xxxxxxxxxxxx";
    }
}
