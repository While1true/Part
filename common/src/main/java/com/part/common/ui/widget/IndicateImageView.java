package com.part.common.ui.widget;

/**
 * @author ckckck  ${data}
 * life is short ,bugs are too many!
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;


/**
 * Created by vange on 2017/9/21.
 */

public class IndicateImageView extends android.support.v7.widget.AppCompatImageView {
    int indicateColor=0xffff4070, indicateTextColor=0xffffffff, indicate,max=99;
    float indicateRadius=dp2px(8), indicatesize=dp2px(9);
    Paint paint;

    public IndicateImageView(Context context) {
        this(context, null);
    }

    public IndicateImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicateImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    public float dp2px(float v) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v, getResources().getDisplayMetrics());
    }

    RectF rect = new RectF();
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
        int width = getWidth();
        /**
         * 大于最大值画一个圆
         */
        if(indicate>max){
            canvas.drawCircle(width-indicateRadius,indicateRadius,indicateRadius/2,paint);
            return;
        }
        /**
         * 右上角
         * draw背景
         */
        paint.setTextSize(indicatesize);
        float v = paint.measureText(indicate + "");
        rect.set(width - v - indicateRadius * 2, 0, width, indicateRadius * 2);
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
        canvas.drawText(indicate + "",width - (indicateRadius+v), baseLineY, paint);

    }


    public int getIndicateColor() {
        return indicateColor;
    }

    public void setIndicateColor(int indicateColor) {
        this.indicateColor = indicateColor;
        invalidate();
    }

    public int getIndicateTextColor() {
        return indicateTextColor;
    }

    public void setIndicateTextColor(int indicateTextColor) {
        this.indicateTextColor = indicateTextColor;
        invalidate();
    }

    public int getIndicate() {
        return indicate;
    }

    public void setIndicate(int indicate) {
        this.indicate = indicate;
        invalidate();
    }

    public float getIndicateRadius() {
        return indicateRadius;
    }

    public void setIndicateRadius(float indicateRadius) {
        this.indicateRadius = indicateRadius;
        invalidate();
    }

    public float getIndicatesize() {
        return indicatesize;
    }

    public void setIndicatesize(float indicatesize) {
        this.indicatesize = indicatesize;
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }
}