package com.part.common.util.boxing;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.liux.android.boxing.BoxingUcrop;
import com.liux.android.boxing.Boxinger;

/**
 * by ckckck 2019/1/17
 * <p>
 * life is short , bugs are too many!
 */
public class FixedBoxing extends Boxinger {
    public static void init() {
        BoxingCrop.getInstance().init(new BoxingUcrop());
        BoxingMediaLoader.getInstance().init(new FixedBoxingGlideLoader());
    }
}
