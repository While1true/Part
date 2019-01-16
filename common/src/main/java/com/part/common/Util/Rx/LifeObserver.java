package com.part.common.Util.Rx;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.net.ParseException;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.part.common.Util.LogUtil;
import com.part.common.Util.ToastUtil;
import com.part.common.mvp.IView;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by ckckck on 2017/9/13.
 */

public abstract class LifeObserver<T> implements Observer<T>, LifecycleObserver {
    private static final String TAG = "MyObserver";
    private Disposable d;
    private IView iView;
    private boolean showLoading = false;

    protected LifeObserver(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
        if (lifecycleOwner instanceof IView) {
            iView = (IView) lifecycleOwner;
        }
    }

    protected LifeObserver(LifecycleOwner lifecycleOwner, boolean showLoading) {
        this(lifecycleOwner);
        this.showLoading = showLoading;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        if (iView != null && showLoading) {
            iView.showLoading();
        }
    }


    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable e) {
        if (iView != null && showLoading) {
            iView.hideLoading();
        }
        String message = e.getMessage();
        LogUtil.e(message);
        onException(e, message);
    }

    private void onException(Throwable e, String message) {
        if (e instanceof HttpException) {
            message = "网络问题";
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            message = "连接超时";
        } else if (e instanceof InterruptedIOException) {
            message = "连接异常";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof IllegalStateException) {
            message = "解析异常";
        }
        if (!TextUtils.isEmpty(message)) {
            ToastUtil.showToast(message);
        }
    }

    @Override
    public void onComplete() {
        if (iView != null&&showLoading) {
            iView.hideLoading();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void dispose() {
        iView = null;
        if (d != null && !d.isDisposed())
            d.isDisposed();
    }

}

