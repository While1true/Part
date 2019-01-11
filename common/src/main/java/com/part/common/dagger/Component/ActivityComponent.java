package com.part.common.dagger.Component;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.part.common.dagger.annotation.ActivityScope;
import com.part.common.dagger.module.ActivityModule;
import com.part.common.dagger.module.ContextModule;

import dagger.Component;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
@ActivityScope
@Component(modules = {ActivityModule.class,ContextModule.class})
public interface ActivityComponent {
    Context getContext();

    Lifecycle getLifecycle();

    Activity getActivity();

    FragmentManager getFragmentManager();
}
