package com.part.common.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public abstract class BaseFragment extends Fragment {
    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjection();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(getLayoutId(),container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData(savedInstanceState);
    }

    protected abstract void initInjection();

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract int getLayoutId();
}
