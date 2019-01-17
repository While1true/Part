package com.part.basemoudle.ui.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.part.basemoudle.DaggerTestComponent;
import com.part.basemoudle.R;
import com.part.basemoudle.injection.module.TestModule;
import com.part.basemoudle.mvp.presenter.HelloPresenter;
import com.part.common.ui.fragment.BaseMvpFragment;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public class TestFragment extends BaseMvpFragment<HelloPresenter> {
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
        TextView tv=rootView.findViewById(R.id.xxx_textview);
        tv.setText(mPresenter.sayHello());
        showError();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.xxx_test;
    }
}
