package com.part.basemoudle;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.part.common.Util.ToastUtil;
import com.part.common.ui.activity.BaseMvpActivity;
import com.part.common.ui.widget.Scrolling.MixScroll.ElasticProcess;
import com.part.common.ui.widget.Scrolling.MixScroll.MixScrolling;
import com.part.common.ui.widget.Scrolling.ScrollDirection;

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
    @BindView(R2.id.mixscroll)
    MixScrolling mixscroll;

    @Override
    protected void initData(Bundle savedInstanceState) {
//        getSupportFragmentManager().beginTransaction().add(android.R.id.content, new TestFragment()).commit();
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        findViewById(R.id.ccc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToaste("xcccccccccccc");
            }
        });
        mixscroll.setScrollProcess(new ElasticProcess());
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

    @OnClick({R2.id.xxx_textview})
    public void onViewClicked() {

        showLoading();
    }
}
