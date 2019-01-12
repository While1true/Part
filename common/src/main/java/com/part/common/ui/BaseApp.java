package com.part.common.ui;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.v4.widget.NestedScrollView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.part.common.BuildConfig;
import com.part.common.Util.AppContext;
import com.part.common.Util.AutoScreenUtils;
import com.part.common.Util.ToastUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

import cn.jpush.android.api.JPushInterface;


/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public abstract class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if(getPackageName().equals(getCurrentProcessName(this))) {

            Logger.addLogAdapter(new AndroidLogAdapter());

            AppContext.init(this);

            ARouter.init(this);
            if(BuildConfig.DEBUG){
                ARouter.openDebug();
                ARouter.openLog();
            }

            AutoScreenUtils.AdjustDensity(this);
            ToastUtil.init();

            UMConfigure.init(this,BuildConfig.UMengKey,BuildConfig.UMengChannel , UMConfigure.DEVICE_TYPE_PHONE, null);
            UMConfigure.setLogEnabled(BuildConfig.DEBUG);

            JPushInterface.setDebugMode(BuildConfig.DEBUG);
            JPushInterface.init(this);
            init();

        }

    }
    protected abstract void init();

    /**
     * 获取当前进程名
     */
    private static String getCurrentProcessName(Application application) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) application.getApplicationContext().getSystemService
                (Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }

}
