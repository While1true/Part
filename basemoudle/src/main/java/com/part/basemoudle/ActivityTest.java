package com.part.basemoudle;

import android.os.Bundle;
import android.widget.TextView;

import com.part.common.ui.activity.BaseMvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * by ckckck 2019/1/10
 * <p>
 * life is short , bugs are too many!
 */
public class ActivityTest extends BaseMvpActivity<HelloPresenter> {


    @BindView(R2.id.xxx_textview)
    TextView xxxTextview;

    @Override
    protected void initData(Bundle savedInstanceState) {
        getSupportFragmentManager().beginTransaction().add(android.R.id.content,new TestFragment()).commit();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.xxx_test;
    }

    @Override
    protected void injectComponent() {
        DaggerTestComponent.builder().activityComponent(activityComponent)
                .testModule(new TestModule(this))
                .build().inject(this);
    }

    @OnClick(R2.id.xxx_textview)
    public void onViewClicked() {
        showLoading();
    }
}
