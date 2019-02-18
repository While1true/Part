package com.common.common.util;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;

/**
 * 2018/2/13
 * By Liux
 * lx0758@qq.com
 */

public class ActivityStarter {
    private static final String TAG = "ActivityStarter";

    public static void startActivityForResult(FragmentActivity activity, Intent intent, Callback callback) {
        synchronized (activity) {
            innerFragmentForResult(activity, intent, callback);
        }
    }

    public static void startActivityForResult(Fragment fragment, Intent intent, Callback callback) {
        startActivityForResult(fragment.getActivity(), intent, callback);
    }

    private static void innerFragmentForResult(FragmentActivity activity, Intent intent, Callback callback) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        InnerFragment fragment = (InnerFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new InnerFragment();
            fragmentManager
                    .beginTransaction()
                    .add(fragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        fragment.startActivityForCallback(intent, callback);
    }

    public static class InnerFragment extends Fragment {

        private SparseArray<Callback> callbackSparseArray = new SparseArray<>();

        public void startActivityForCallback(Intent intent, Callback callback) {
            int requestCode = getRequestCode();
            startActivityForResult(intent, requestCode);
            callbackSparseArray.put(requestCode, callback);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            Callback callback = callbackSparseArray.get(requestCode);
            callbackSparseArray.remove(requestCode);
            if (callback != null) {
                callback.onActivityResult(resultCode, data);
            }
        }

        /**
         * 获取一个随机请求码
         * @return
         */
        private int getRequestCode() {
            return (int) (System.currentTimeMillis() & 0xFFFF);
        }
    }

    public interface Callback {

        void onActivityResult(int resultCode, Intent data);
    }
}