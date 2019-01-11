package com.part.common.Util;

/**
 * @author ckckck   2019/1/5
 * life is short ,bugs are too many!
 */
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 不听话的好孩子 on 2018/4/8.
 */

public class SaveUtil {

    private SaveUtil() {
    }

    public static void save(String key, Object obj) {
        String simpleName = obj.getClass().getSimpleName();
        SharedPreferences.Editor edit = SharePref.sharedPreferences.edit();
        if ("Integer".equals(simpleName)) {
            edit.putInt(key, (Integer) obj);
        } else if ("Boolean".equals(simpleName)) {
            edit.putBoolean(key, (Boolean) obj);
        } else if ("String".equals(simpleName)) {
            edit.putString(key, (String) obj);
        } else if ("Float".equals(simpleName)) {
            edit.putFloat(key, (Float) obj);
        } else if ("Long".equals(simpleName)) {
            edit.putLong(key, (Long) obj);
        } else {
            throw new UnsupportedOperationException("不支持该类型");
        }
        edit.apply();
    }

    public SharedPreferences get() {
        return SharePref.sharedPreferences;
    }

    private static class SharePref {
        private static SharedPreferences sharedPreferences = AppContext.get().getSharedPreferences("xxx", Context.MODE_PRIVATE);
    }
}