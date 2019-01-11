package com.part.common.ui.widget.Scrolling.MixScroll.Base;

import android.content.Context;
import android.util.TypedValue;

/**
 * @author ckckck  ${data}
 * life is short ,bugs are too many!
 */
public class SizeUtil {
    public static int dp2px(Context context, int dp){
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics())+0.5f);
    }
}
