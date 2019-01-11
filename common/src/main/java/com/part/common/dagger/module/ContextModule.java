package com.part.common.dagger.module;

import android.app.Application;
import android.content.Context;


import dagger.Module;
import dagger.Provides;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
@Module
public class ContextModule {
    Context context;
    public ContextModule(Application context){
        this.context=context;
    }
    @Provides
    Context AppContext(){
        return context;
    }
}