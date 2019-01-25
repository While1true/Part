package com.aroutmodule;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.datemodule.Entry.Student;
import com.datemodule.greendao.DaoSession;
import com.part.common.dagger.component.DaggerActivityComponent;
import com.part.common.dagger.module.ActivityModule;
import com.part.common.dagger.module.ContextModule;
import com.part.common.ui.activity.BaseActivity;

import java.util.Random;

import javax.inject.Inject;

/**
 * @author ckckck   2019/1/19
 * life is short ,bugs are too many!
 */
@Route(path = "/module2/ac")
public class Ac extends BaseActivity {
    @Inject
    DaoSession daoSession;

    @Override
    protected void initInjection() {
        DaggerAcComponent.builder().activityComponent(DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).contextModule(new ContextModule(getApplication())).build())
                .build().inject(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        daoSession.getStudentDao().insert(new Student(null,new Random().nextInt(Integer.MAX_VALUE)+""));
    }

    @Override
    protected void initView() {

    }

    public void hello(View view) {
//        startActivity(new Intent(this,SC.class));
        ARouter.getInstance().build("/testx/main").navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.xxx2_hello_world;
    }
}
