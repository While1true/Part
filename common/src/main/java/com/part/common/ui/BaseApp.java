package com.part.common.ui;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.datemodule.greendao.DaoSession;
import com.datemodule.greendao.ParentDao;
import com.datemodule.greendao.StudentDao;
import com.liux.android.util.AppUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.part.common.BuildConfig;
import com.part.common.util.AppContext;
import com.part.common.util.AutoScreenUtils;
import com.part.common.util.MigrationHelper;
import com.part.common.util.ToastUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;


/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public abstract class BaseApp extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        if (AppUtil.isMainProcess(this)) {

            Logger.addLogAdapter(new AndroidLogAdapter());

            AppContext.init(this);

            if (BuildConfig.DEBUG) {
                ARouter.openDebug();
                ARouter.openLog();
            }
            ARouter.init(this);


            AutoScreenUtils.AdjustDensity(this);
            ToastUtil.init();

            UMConfigure.init(this, BuildConfig.UMengKey, BuildConfig.UMengChannel, UMConfigure.DEVICE_TYPE_PHONE, null);
            UMConfigure.setLogEnabled(BuildConfig.DEBUG);

            if (BuildConfig.needUMengShare) {
                PlatformConfig.setWeixin(BuildConfig.WXKey, BuildConfig.WXSecret);
                //豆瓣RENREN平台目前只能在服务器端配置
                PlatformConfig.setSinaWeibo(BuildConfig.WBKey, BuildConfig.WBSecret, BuildConfig.WBCallBack);
                PlatformConfig.setQQZone(BuildConfig.QQKey, BuildConfig.QQSecret);
            }
            JPushInterface.setDebugMode(BuildConfig.DEBUG);
            JPushInterface.init(this);

            daoSession=MigrationHelper.init(this, ParentDao.class,StudentDao.class);

            init();

        }

    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    protected abstract void init();

}
