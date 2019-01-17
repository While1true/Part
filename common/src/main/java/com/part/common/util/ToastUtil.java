package com.part.common.util;

/**
 * @author ckckck   2019/1/5
 * life is short ,bugs are too many!
 */
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 不听话的好孩子 on 2018/4/8.
 */

public class ToastUtil {
    private static Toast toast;
    private static TextView textView;
    private static Object object = new Object();

    private static Toast get() {
        if (toast == null) {
            synchronized (object) {
                if (toast == null) {
                    toast = new Toast(AppContext.get());
                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
                    textView = new TextView(AppContext.get());
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f);
                    textView.setBackgroundColor(0xFF79C4A0);
                    textView.setTextColor(0xffffffff);
                    textView.setGravity(Gravity.CENTER);
                    int padding = SizeUtils.dp2px(15f);
                    textView.setPadding(padding, padding, padding, padding);
                    ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(layoutParams);
                    toast.setView(textView);
                }
            }
        }
        return toast;
    }

    /**
     * 显示toast
     *
     * @param text
     * @param during
     */
    public static void showToast(CharSequence text, int during) {
        textView.setText(text);
        get().setDuration(during);
        get().show();
    }

    /**
     * 显示toast
     *
     * @param text
     */
    public static void showToast(CharSequence text) {
        textView.setText(text);
        get().setDuration(Toast.LENGTH_SHORT);
        get().show();
    }

    /**
     * 在主线程初始化
     * 不然在子线程调用崩溃
     */
    public static void init() {
        get();
    }


}