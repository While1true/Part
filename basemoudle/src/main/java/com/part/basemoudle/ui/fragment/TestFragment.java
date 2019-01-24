package com.part.basemoudle.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.part.basemoudle.R;
import com.part.basemoudle.R2;
import com.part.basemoudle.injection.component.DaggerTestComponent;
import com.part.basemoudle.injection.module.TestModule;
import com.part.basemoudle.mvp.presenter.HelloPresenter;
import com.part.common.ui.fragment.BaseMvpFragment;
import com.part.common.ui.widget.Adpater.Base.Holder;
import com.part.common.ui.widget.Adpater.Impliment.MixAdapter;
import com.part.common.ui.widget.Adpater.Impliment.PositionHolder;
import com.part.common.ui.widget.Scrolling.MixScroll.MixScrolling;
import com.part.common.ui.widget.Scrolling.MixScroll.NestedRefreshProcess;
import com.part.common.ui.widget.Scrolling.MixScroll.SimpleHeaderFooter;
import com.part.common.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public class TestFragment extends BaseMvpFragment<HelloPresenter> {
    @BindView(R2.id.recyclerview)
    RecyclerView recyclerview;
    Unbinder unbinder;
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
        MixScrolling mixScrolling=rootView.findViewById(R.id.mixscroll);
        mixScrolling.setScrollProcess(new NestedRefreshProcess(new SimpleHeaderFooter(getContext()),new SimpleHeaderFooter(getContext(),false), null));
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.page_item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
