package com.common.common.ui.widget.DecorateView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;
import android.view.View;

/**
 * by ckckck 2019/1/29
 * <p>
 * life is short , bugs are too many!
 */
public class IndicateDecorate implements IDecorate {
    private int indicateColor = 0xffff4070, indicateTextColor = 0xffffffff, indicate, max = 99;
    private float indicateRadius = 0, indicatesize = 0;
    private Paint paint;
    private RectF rect = new RectF();
    private int W, H;
    private View view;

    @Override
    public void onSizeChanged(View view, int w, int h, int oldw, int oldh) {
        W = w;
        H = h;
        this.view = view;
        indicateRadius = dp2px(view, 8);
        indicatesize = dp2px(view, 9);
    }

    public float dp2px(View view, float v) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v, view.getResources().getDisplayMetrics());
    }

    @Override
    public void onDraw(Canvas canvas) {
        /**
         * 等于0不画
         */
        if (indicate == 0) {
            return;
        }
        if (paint == null) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        paint.setColor(indicateColor);
        /**
         * 大于最大值画一个圆
         */
        if (indicate > max) {
            canvas.drawCircle(W - indicateRadius, indicateRadius, indicateRadius / 2, paint);
            return;
        }
        /**
         * 右上角
         * draw背景
         */
        paint.setTextSize(indicatesize);
        float v = paint.measureText(indicate + "");
        rect.set(W - v - indicateRadius * 2, 0, W, indicateRadius * 2);
        canvas.drawRoundRect(rect, indicateRadius, indicateRadius, paint);
        /**
         * 画数字
         */
        paint.setColor(indicateTextColor);
        /**
         * 计算基线位置
         */
        Paint.FontMetrics fm = paint.getFontMetrics();
        float baseLineY = indicateRadius - (fm.ascent - (fm.ascent - fm.descent) / 2);
        canvas.drawText(indicate + "", W - (indicateRadius + v), baseLineY, paint);
    }

    public int getIndicateColor() {
        return indicateColor;
    }

    public void setIndicateColor(int indicateColor) {
        this.indicateColor = indicateColor;
        if (view != null) {
        }
    }

    public int getIndicateTextColor() {
        return indicateTextColor;
    }

    public void setIndicateTextColor(int indicateTextColor) {
        this.indicateTextColor = indicateTextColor;
        if (view != null) {
            view.invalidate();
        }
    }

    public int getIndicate() {
        return indicate;
    }

    public void setIndicate(int indicate) {
        this.indicate = indicate;
        if (view != null) {
            view.invalidate();
        }
    }

    public float getIndicateRadius() {
        return indicateRadius;
    }

    public void setIndicateRadius(float indicateRadius) {
        this.indicateRadius = indicateRadius;
        if (view != null) {
            view.invalidate();
        }
    }

    public float getIndicatesize() {
        return indicatesize;
    }

    public void setIndicatesize(float indicatesize) {
        this.indicatesize = indicatesize;
        if (view != null) {
            view.invalidate();
        }
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        if (view != null) {
            view.invalidate();
        }
    }
}
