package com.part.common.Util;

/**
 * @author ckckck   2019/1/5
 * life is short ,bugs are too many!
 */

import android.util.TypedValue;

public class SizeUtils {
    public static int dp2px(float value){
        return (int) (0.5f+ TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,
                AppContext.get().getResources().getDisplayMetrics()));
    }
}