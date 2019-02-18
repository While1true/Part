package com.common.common.ui.activity;

import com.common.common.dagger.component.ActivityComponent;
import com.common.common.dagger.component.DaggerActivityComponent;
import com.common.common.dagger.module.ActivityModule;
import com.common.common.dagger.module.ContextModule;
import com.common.common.mvp.IPresenter;
import com.common.common.mvp.IView;
import com.common.common.ui.dialog.LoadingDialog;

import javax.inject.Inject;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public abstract class BaseMvpActivity<T extends IPresenter> extends BaseActivity implements IView {
    @Inject
    protected T mPresenter;

    @Inject
    LoadingDialog loadingDialog;

    protected ActivityComponent activityComponent;

    @Override
    protected void initInjection() {
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .contextModule(new ContextModule(getApplication()))
                .build();
        injectComponent();
    }
    protected abstract void injectComponent();
    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }
}
