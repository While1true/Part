package com.part.basemoudle.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
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
import com.part.basemoudle.ui.fragment.TestFragment;
import com.part.basemoudle.ui.fragment.TestFragment2;
import com.part.common.ui.widget.RotateImage.RotateImageView;
import com.part.common.ui.widget.ViewPager.AlphaTransformer;
import com.part.common.ui.widget.ViewPager.Indicate.Indicate;
import com.part.common.ui.widget.ViewPager.LoopFragmentPagerAdapter;
import com.part.common.ui.widget.ViewPager.ViewPagerScroller;
import com.part.common.util.boxing.FixedBoxing;
import com.part.common.ui.activity.BaseMvpActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * by ckckck 2019/1/10
 * <p>
 * life is short , bugs are too many!
 */
@Route(path = "/testx/main")
public class ActivityTest extends BaseMvpActivity<HelloPresenter> {


    @BindView(R2.id.viewpagr)
    ViewPager viewPager;
    @BindView(R2.id.viewpagrT)
    ViewPager viewpagrT;
    @BindView(R2.id.indicate)
    Indicate indicate;
    @BindView(R2.id.rotateImg)
    RotateImageView rotateImageView;

    @Override
    protected void initData(Bundle savedInstanceState) {
//        getSupportFragmentManager().beginTransaction().add(android.R.id.content, new TestFragment()).commit();
    }

    @Override
    protected void initView() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ButterKnife.bind(this);

        FixedBoxing.init();

        CollapsingToolbarLayout layout = findViewById(R.id.collaps);
        layout.setTitle("测试无疑问");
        //通过CollapsingToolbarLayout修改字体颜色
        layout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        layout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        AppBarLayout appbar = findViewById(R.id.appbar);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        LoopFragmentPagerAdapter adapter = new LoopFragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getActualCount() {
                return 4;
            }

            @Override
            public Fragment getActualItem(int position) {
                return new TestFragment2();
            }

            @Override
            public CharSequence getActualPagerTitle(int position) {
                return position + "";
            }
        };
        adapter.setAutoSwitch(true).setLoop(true).addLifeOwner(this);
        viewpagrT.setAdapter(adapter);
        viewpagrT.setPageTransformer(false, new AlphaTransformer());
        new ViewPagerScroller(this).setupViewPager(viewpagrT);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
//                if(i==0)
                return new TestFragment();
//                else return new TestFragment2();
            }

            @Override
            public int getCount() {
                return 4;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return "title" + position;
            }
        });

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        indicate.setUpViewPager(viewpagrT);

        rotateImageView.setAdapter(new RotateImageView.ViewAdapter<String>(Arrays.asList(new String[]{"cc","vv"})) {
            @Override
            public View getView(int i, String s, View conventView, ViewGroup viewGroup) {
                if(conventView==null){
                    conventView=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tt,viewGroup,false);
                }
                return conventView;
            }
        });
    }

    private void re() {
        Authorizer.with(this).requestRuntime(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
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


}
