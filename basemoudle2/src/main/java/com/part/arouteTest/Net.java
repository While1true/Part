package com.part.arouteTest;

import com.common.common.util.rx.Net.RetrofitHttpManger;

import okhttp3.OkHttpClient;

/**
 * by ckckck 2019/1/16
 * <p>
 * life is short , bugs are too many!
 */
public class Net {
    public static <T> T creatService(Class<T> service) {
        return H.retrofitHttpManger.get().create(service);
    }

    public static OkHttpClient getClient() {
        return H.retrofitHttpManger.getClient();
    }

    private static class H {
        private static RetrofitHttpManger retrofitHttpManger = new RetrofitHttpManger.Builder().setBaseUrl("http://wanandroid.com/")
                .Builder();
    }
}
