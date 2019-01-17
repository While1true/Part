package com.part.basemoudle.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.liux.android.boxing.OnCancelListener;
import com.liux.android.boxing.OnMultiSelectListener;
import com.liux.android.permission.Authorizer;
import com.liux.android.permission.Continue;
import com.liux.android.permission.OnContinueListener;
import com.liux.android.permission.runtime.OnRuntimePermissionListener;
import com.part.basemoudle.R;
import com.part.basemoudle.R2;
import com.part.basemoudle.injection.component.DaggerTestComponent;
import com.part.basemoudle.injection.module.TestModule;
import com.part.basemoudle.mvp.presenter.HelloPresenter;
import com.part.common.ui.widget.Scrolling.MixScroll.OverscrollProcess;
import com.part.common.util.boxing.FixedBoxing;
import com.part.common.util.ToastUtil;
import com.part.common.ui.activity.BaseMvpActivity;
import com.part.common.ui.widget.Scrolling.MixScroll.MixScrolling;
import com.part.common.ui.widget.Scrolling.MixScroll.RefreshProcess;
import com.part.common.ui.widget.Scrolling.MixScroll.SimpleHeaderFooter;
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
        ButterKnife.bind(this);
        findViewById(R.id.ccc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showWarnToast("成功");
            }
        });
        findViewById(R.id.ccc2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("normal toast");
            }
        });
        FixedBoxing.init();
        mixscroll.setScrollProcess(new OverscrollProcess(true));
    }

    private void re() {
        Authorizer.with(this).requestRuntime(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
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
                        FixedBoxing.with(ActivityTest.this)
                                .multipleSelect(5)
                                .useCamera(true)
                                .listener(new OnCancelListener() {
                                    @Override
                                    public void onCancel() {

                                    }
                                })
                                .listener(new OnMultiSelectListener() {
                                    @Override
                                    public void onMultiSelect(List<ImageMedia> imageMedias) {

                                    }
                                }).start();
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


//        Net.creatService(IService.class).getData("0").compose(RxSchedulers.compose())
//                .subscribe(new LifeObserver<Object>(this,true) {
//                    @Override
//                    public void onNext(Object s) {
//                        ToastUtil.showToast(s.toString());
//                    }
//                });
    }
}
