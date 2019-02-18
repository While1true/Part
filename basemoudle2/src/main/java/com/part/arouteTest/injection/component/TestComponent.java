package com.part.arouteTest.injection.component;

import com.part.arouteTest.ui.activity.ActivityTest;
import com.part.arouteTest.ui.fragment.TestFragment;
import com.part.arouteTest.injection.module.TestModule;
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
    void inject(ActivityTest activityTest);
    void inject(TestFragment testFragment);
}
