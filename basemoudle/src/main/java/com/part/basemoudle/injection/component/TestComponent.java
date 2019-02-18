package com.part.basemoudle.injection.component;

import com.part.basemoudle.ui.activity.ActivityTest;
import com.part.basemoudle.ui.activity.FloorTestActivity;
import com.part.basemoudle.ui.fragment.TestFragment;
import com.part.basemoudle.injection.module.TestModule;
import com.part.basemoudle.ui.fragment.TestFragment2;
import com.common.common.dagger.component.ActivityComponent;
import com.common.common.dagger.annotation.ComponentScope;

import dagger.Component;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
@ComponentScope
@Component(modules = {TestModule.class},dependencies = {ActivityComponent.class})
public interface TestComponent {
    void inject(FloorTestActivity activity);
    void inject(ActivityTest activityTest);
    void inject(TestFragment testFragment);
    void inject(TestFragment2 testFragment);
}
