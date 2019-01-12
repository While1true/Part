package com.part.basemoudle;

import com.part.common.dagger.Component.ActivityComponent;
import com.part.common.dagger.annotation.ComponentScope;

import dagger.Component;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
@ComponentScope
@Component(modules = {TestModule.class},dependencies = {ActivityComponent.class})
public interface TestComponent {
    void inject(ActivityTest activityTest);
    void inject(TestFragment testFragment);
}
