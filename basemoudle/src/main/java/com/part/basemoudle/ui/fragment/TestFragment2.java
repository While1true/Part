package com.part.basemoudle.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.part.basemoudle.R;
import com.part.basemoudle.injection.component.DaggerTestComponent;
import com.part.basemoudle.injection.module.TestModule;
import com.part.basemoudle.mvp.presenter.HelloPresenter;
import com.part.common.ui.fragment.BaseMvpFragment;
import com.part.common.util.ToastUtil;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public class TestFragment2 extends BaseMvpFragment<HelloPresenter> {
    @Override
    protected void injectComponent() {
        DaggerTestComponent.builder().activityComponent(activityComponent)
                .testModule(new TestModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/testx/floor").navigation();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.tt;
    }

}
