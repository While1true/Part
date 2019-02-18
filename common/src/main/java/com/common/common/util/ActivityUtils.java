package com.common.common.util;

/**
 * @author ckckck   2019/1/5
 * life is short ,bugs are too many!
 */

import android.os.Build;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 不听话的好孩子 on 2018/2/28.
 */

public class ActivityUtils {
    private static List<FragmentActivity> activities = new ArrayList<>(20);

    public static void addActivity(FragmentActivity activity) {
        if (!activities.contains(activity)) {
            activities.add(activity);
        }
    }

    public static void remove(FragmentActivity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
        }
    }

    public static FragmentActivity getTopActivity() {
        if (activities.isEmpty())
            return null;
        FragmentActivity fragmentActivity = activities.get(activities.size() - 1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (fragmentActivity.isDestroyed()) {
                activities.remove(fragmentActivity);
                return getTopActivity();
            }
        }
        return fragmentActivity;
    }

    public static void finishAll() {
        for (FragmentActivity activity : activities) {
            activity.finish();
        }
        activities.clear();
    }

    public static FragmentActivity get(int index) {
        return activities.get(index);
    }
}