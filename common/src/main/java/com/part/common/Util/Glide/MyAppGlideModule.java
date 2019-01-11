package com.part.common.Util.Glide;

/**
 * @author ckckck   2019/1/5
 * life is short ,bugs are too many!
 */

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.part.common.Util.AppContext;

import java.io.File;
import java.io.InputStream;

/**
 * Created by 不听话的好孩子 on 2018/4/9.
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    private static int CACHE_SZIE = 104857600;
    public static File CACHE_PATH = new File(AppContext.get().getCacheDir(),"glide");
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        builder.setDiskCache(new DiskLruCacheFactory(CACHE_PATH.getAbsolutePath(),CACHE_SZIE))
                .setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565))
        ;
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //TODO 括号内换成和rxNet公用Client
        registry.replace(GlideUrl.class, InputStream.class, new MyOkHttpUrlLoader.Factory());
    }
}
