package com.part.common.util;

/**
 * @author ckckck   2019/1/5
 * life is short ,bugs are too many!
 */
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.part.common.R;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.SizeUtil;

/**
 * Created by 不听话的好孩子 on 2018/4/8.
 */

public class ToastUtil {
    @ColorInt
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");

    @ColorInt
    private static final int ERROR_COLOR = Color.parseColor("#FD4C5B");

    @ColorInt
    private static final int INFO_COLOR = Color.parseColor("#3F51B5");

    @ColorInt
    private static final int SUCCESS_COLOR = Color.parseColor("#388E3C");

    @ColorInt
    private static final int WARNING_COLOR = Color.parseColor("#FFA900");

    private static Toast toast;
    private static TextView textView;
    private static ImageView imageView;
    private static LinearLayout linearLayout;
    private static Object object = new Object();

    private static Toast get() {
        if (toast == null) {
            synchronized (object) {
                if (toast == null) {
                    toast = new Toast(AppContext.get());
//                    toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, 0);
                    textView = new TextView(AppContext.get());
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17f);
                    textView.setTextColor(DEFAULT_TEXT_COLOR);
                    textView.setGravity(Gravity.CENTER);
                    ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textView.setLayoutParams(layoutParams);
                    textView.setPadding(SizeUtil.dp2px(AppContext.get(),6),0,0,0);
                    imageView=new ImageView(AppContext.get());
                    imageView.setLayoutParams(new ViewGroup.MarginLayoutParams(SizeUtil.dp2px(AppContext.get(),24), SizeUtil.dp2px(AppContext.get(),24)));
                    linearLayout=new LinearLayout(AppContext.get());
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setGravity(Gravity.CENTER);
                    linearLayout.addView(imageView);
                    linearLayout.addView(textView);
                    toast.setView(linearLayout);
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
        interalShow(text,null,getDrawable(R.drawable.lib_toast_frame),during);
    }

    /**
     * 显示toast
     *
     * @param text
     */
    public static void showToast(CharSequence text) {
        showToast(text,Toast.LENGTH_SHORT);
    }

    /**
     * 在主线程初始化
     * 不然在子线程调用崩溃
     */
    public static void init() {
        get();
    }

    public static void showSuccessToast(CharSequence charSequence){
        showSuccessToast(charSequence,Toast.LENGTH_SHORT);
    }
    public static void showSuccessToast(CharSequence charSequence,int during){
        interalShow(charSequence,getDrawable(R.drawable.lib_toast_ok),tint9PatchDrawableFrame(SUCCESS_COLOR),during);
    }

    public static void showErrorToast(CharSequence charSequence){
        showErrorToast(charSequence,Toast.LENGTH_SHORT);
    }
    public static void showErrorToast(CharSequence charSequence,int during){
        interalShow(charSequence,getDrawable(R.drawable.lib_toast_error),tint9PatchDrawableFrame(ERROR_COLOR),during);
    }

    public static void showWarnToast(CharSequence charSequence){
        showWarnToast(charSequence,Toast.LENGTH_SHORT);
    }
    public static void showWarnToast(CharSequence charSequence,int during){
        interalShow(charSequence,getDrawable(R.drawable.lib_toast_info),tint9PatchDrawableFrame(WARNING_COLOR),during);
    }
    public static void showInfoToast(CharSequence charSequence){
        showInfoToast(charSequence,Toast.LENGTH_SHORT);
    }
    public static void showInfoToast(CharSequence charSequence,int during){
        interalShow(charSequence,getDrawable(R.drawable.lib_toast_info),tint9PatchDrawableFrame(INFO_COLOR),during);
    }
    private static void interalShow(CharSequence charSequence,Drawable drawable,Drawable bgdrawable,int during){
        get().setDuration(during);
        textView.setText(charSequence);
        setBackground(bgdrawable);
        if(drawable!=null) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageDrawable(drawable);
        }else{
            imageView.setVisibility(View.GONE);
        }
        get().show();
    }
    private static final Drawable tint9PatchDrawableFrame(@ColorInt int tintColor) {
        final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(R.drawable.lib_toast_frame);
        toastDrawable.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        return toastDrawable;
    }
    private static final Drawable getDrawable(@DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return AppContext.get().getDrawable(id);
        } else {
            return AppContext.get().getResources().getDrawable(id);
        }
    }
    public static final void setBackground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.setBackground(drawable);
        } else {
            linearLayout.setBackgroundDrawable(drawable);
        }
    }

}