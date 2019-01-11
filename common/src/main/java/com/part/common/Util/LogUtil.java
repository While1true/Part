package com.part.common.Util;

import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public class LogUtil {
    /**
     * 注意：打包的时候记得设置isDebug为false
     */

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Logger.t(tag).e(msg + "");
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Logger.t(tag).d(msg + "");
        }
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Logger.t(tag).i(msg + "");
        }
    }

    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Logger.t(tag).v(msg + "");
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Logger.t(tag).w(msg + "");
        }
    }

    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.e(msg + "");
        }
    }

    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.d(msg + "");
        }
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.i(msg + "");
        }
    }

    public static void v(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.v(msg + "");
        }
    }

    public static void w(String msg) {
        if (BuildConfig.DEBUG) {
            Logger.w(msg + "");
        }
    }

    public static void json(String json) {
        if (BuildConfig.DEBUG) {
            Logger.json(json);
        }
    }

    public static void xml(String xml) {
        if (BuildConfig.DEBUG) {
            Logger.xml(xml);
        }
    }

    public static void wtf(String wtf) {
        if (BuildConfig.DEBUG) {
            Logger.wtf(wtf);
        }
    }
}
