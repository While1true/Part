package com.part.basemoudle;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liux.android.permission.Authorizer;
import com.liux.android.permission.Continue;
import com.liux.android.permission.OnContinueListener;
import com.liux.android.permission.runtime.OnRuntimePermissionListener;
import com.part.common.Util.ToastUtil;
import com.part.common.ui.activity.BaseMvpActivity;
import com.part.common.ui.widget.Scrolling.MixScroll.ElasticProcess;
import com.part.common.ui.widget.Scrolling.MixScroll.MixScrolling;
import com.part.common.ui.widget.Scrolling.MixScroll.NestedRefreshProcess;
import com.part.common.ui.widget.Scrolling.MixScroll.RefreshProcess;
import com.part.common.ui.widget.Scrolling.MixScroll.SimpleHeaderFooter;
import com.part.common.ui.widget.Scrolling.ScrollDirection;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

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
        re();

        ButterKnife.bind(this);
        findViewById(R.id.ccc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re();
                ToastUtil.showToaste("xccccccccccccddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
            }
        });
        mixscroll.setScrollProcess(new RefreshProcess(new SimpleHeaderFooter(this, true), new SimpleHeaderFooter(this, false)));
    }

    private void re() {
        Authorizer.with(this).requestRuntime(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .listener(new OnContinueListener() {
                    @Override
                    public void onContinue(final Continue aContinue) {
                        new AlertDialog.Builder(ActivityTest.this).setTitle("dfdd")
                                .setPositiveButton("ccc", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        aContinue.onContinue();
                                    }
                                }).setNegativeButton("dff", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                aContinue.onContinue();
                            }
                        })
                                .create().show();
                    }
                })
                .listener(new OnRuntimePermissionListener() {
                    @Override
                    public void onRuntimePermission(List<String> allow, List<String> reject, List<String> prohibit) {
                        new ShareAction(ActivityTest.this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.SINA, SHARE_MEDIA.SMS, SHARE_MEDIA.EMAIL)
                                .setCallback(new UMShareListener() {
                                    @Override
                                    public void onStart(SHARE_MEDIA share_media) {

                                    }

                                    @Override
                                    public void onResult(SHARE_MEDIA share_media) {

                                    }

                                    @Override
                                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {

                                    }

                                    @Override
                                    public void onCancel(SHARE_MEDIA share_media) {

                                    }
                                })
                                .withText("--------").open();
                    }
                }).request();
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
