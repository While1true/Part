package com.part.basemoudle.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.part.basemoudle.R;
import com.part.basemoudle.R2;
import com.part.basemoudle.injection.component.DaggerTestComponent;
import com.part.basemoudle.injection.module.TestModule;
import com.part.basemoudle.mvp.presenter.HelloPresenter;
import com.part.common.ui.activity.BaseMvpActivity;
import com.part.common.ui.widget.Adpater.Base.Holder;
import com.part.common.ui.widget.Adpater.Impliment.MixAdapter;
import com.part.common.ui.widget.Adpater.Impliment.PositionHolder;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.RefreshState;
import com.part.common.ui.widget.Scrolling.MixScroll.MixScrolling;
import com.part.common.ui.widget.Scrolling.MixScroll.RefreshProcess;
import com.part.common.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * by ckckck 2019/1/10
 * <p>
 * life is short , bugs are too many!
 */
@Route(path = "/testx/floor")
public class FloorTestActivity extends BaseMvpActivity<HelloPresenter> {

    @BindView(R2.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R2.id.image)
    ImageView image;
    @BindView(R2.id.floor)
    FrameLayout floor;
    @BindView(R2.id.mixscroll)
    MixScrolling mixScrolling;

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        mixScrolling.setScrollProcess(new RefreshProcess(new FloorHeader(this),new FloorHeader(this,false)));
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) floor.getLayoutParams();
        layoutParams.topMargin=-getResources().getDisplayMetrics().heightPixels;
        layoutParams.height=getResources().getDisplayMetrics().heightPixels;
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new MixAdapter(50)
                .addLifeOwener(this)
                .addType(android.R.layout.simple_list_item_1, new PositionHolder() {
                    @Override
                    public void onBind(Holder holder, final int position) {
                        holder.setText(android.R.id.text1,"ccccccccccccccccc");
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.showSuccessToast(position+"--------");
                            }
                        });
                    }

                    @Override
                    public boolean istype(int position) {
                        return true;
                    }
                }));
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showSuccessToast("---zzzzzzzzzzzzz-----");
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.floor;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(mixScrolling.getRefreshState()==RefreshState.SECONDFLOOR){
                mixScrolling.closeFloor();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void injectComponent() {
        DaggerTestComponent.builder().activityComponent(activityComponent)
                .testModule(new TestModule(this))
                .build().inject(this);
    }

}
