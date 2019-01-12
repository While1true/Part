package com.part.basemoudle;

import com.part.common.mvp.IView;

import dagger.Module;
import dagger.Provides;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
@Module
public class TestModule {
    IView iView;
    public TestModule(IView iView){
        this.iView=iView;
    }

    @Provides
    IView getView(){
        return iView;
    }

}
