package com.part.common.Util.Rx;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by vange on 2017/9/13.
 */

public abstract class LifeObserver<T> implements Observer<T>, LifecycleObserver {
    private static final String TAG = "MyObserver";
    Disposable d;

    protected LifeObserver(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }


    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        Log.i(getClass().getSimpleName(),e.getMessage());
    }

    @Override
    public void onComplete() {

    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void dispose(){
        if (d != null && !d.isDisposed())
            d.isDisposed();
    }

}

