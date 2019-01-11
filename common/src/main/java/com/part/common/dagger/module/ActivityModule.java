package com.part.common.dagger.module;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.part.common.dagger.annotation.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
@Module
public class ActivityModule {
    FragmentActivity activity;

    public ActivityModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @ActivityScope
    @Provides
    Lifecycle getLifecycle() {
        return activity.getLifecycle();
    }

    @ActivityScope
    @Provides
    Activity getActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    FragmentManager getFragmentManager() {
        return activity.getSupportFragmentManager();
    }
}
