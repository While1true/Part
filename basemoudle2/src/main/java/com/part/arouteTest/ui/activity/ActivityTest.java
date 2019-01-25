package com.part.arouteTest.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.arouteTest.injection.component.DaggerTestComponent;
import com.part.arouteTest.injection.module.TestModule;
import com.part.arouteTest.mvp.presenter.HelloPresenter;
import com.part.arouteTest.ui.fragment.TestFragment;
import com.part.common.ui.activity.BaseMvpActivity;

/**
 * by ckckck 2019/1/10
 * <p>
 * life is short , bugs are too many!
 */
@Route(path = "/xtestx/main2")
public class ActivityTest extends BaseMvpActivity<HelloPresenter> {



    @Override
    protected void initData(Bundle savedInstanceState) {
//        getSupportFragmentManager().beginTransaction().add(android.R.id.content, new TestFragment()).commit();
    }

    @Override
    protected void initView() {
        getSupportFragmentManager().beginTransaction().add(android.R.id.content,new TestFragment()).commit();
    }


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void injectComponent() {
        DaggerTestComponent.builder().activityComponent(activityComponent)
                .testModule(new TestModule(this))
                .build().inject(this);
    }


}
