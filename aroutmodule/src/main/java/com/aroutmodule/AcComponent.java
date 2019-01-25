package com.aroutmodule;

import com.part.common.dagger.component.ActivityComponent;
import com.part.common.dagger.annotation.ComponentScope;

import dagger.Component;

/**
 * @author ckckck   2019/1/19
 * life is short ,bugs are too many!
 */
@ComponentScope
@Component(dependencies = {ActivityComponent.class})
public interface AcComponent {
    void inject(Ac ac);
}
