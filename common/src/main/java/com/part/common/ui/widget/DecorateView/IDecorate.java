package com.part.common.ui.widget.DecorateView;

import android.graphics.Canvas;
import android.view.View;

/**
 * by ckckck 2019/1/28
 * <p>
 * life is short , bugs are too many!
 */
public interface IDecorate {
    void onSizeChanged(View view, int w, int h, int oldw, int oldh);
    void onDraw(Canvas canvas);
}
