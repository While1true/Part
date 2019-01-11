package com.part.common.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.part.common.R;
import com.part.common.dagger.Component.ActivityComponent;
import com.part.common.dagger.Component.DaggerActivityComponent;
import com.part.common.dagger.module.ActivityModule;
import com.part.common.dagger.module.ContextModule;
import com.part.common.mvp.IStateView;
import com.part.common.ui.dialog.LoadingDialog;
import com.part.common.ui.widget.StateLayout;

import javax.inject.Inject;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public abstract class BaseMvpFragment<T> extends BaseFragment implements IStateView {
    @Inject
    protected T mPresenter;

    @Inject
    LoadingDialog loadingDialog;

    @Inject
    StateLayout stateLayout;

    protected ActivityComponent activityComponent;

    @Override
    protected void initInjection() {
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(getActivity()))
                .contextModule(new ContextModule(getActivity().getApplication()))
                .build();
        injectComponent();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return stateLayout.setContent(super.onCreateView(inflater, container, savedInstanceState));
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showEmpty() {
        stateLayout.showView(R.layout.lib_layout_state);
        View stateView = stateLayout.getStateView(R.layout.lib_layout_state);
        ImageView imageView=stateView.findViewById(R.id.lib_img);
        TextView textView=stateView.findViewById(R.id.lib_tv);
        imageView.setImageResource(R.drawable.lib_drawable_empty);
        textView.setText("没有相关数据");
    }

    @Override
    public void showError() {
        stateLayout.showView(R.layout.lib_layout_state);
        View stateView = stateLayout.getStateView(R.layout.lib_layout_state);
        ImageView imageView=stateView.findViewById(R.id.lib_img);
        TextView textView=stateView.findViewById(R.id.lib_tv);
        imageView.setImageResource(R.drawable.lib_drawable_error);
        textView.setText("偶尔也会掉链子的");
    }

    @Override
    public void showContent() {
        stateLayout.showContent();
    }
    protected abstract void injectComponent();
}
