package com.common.common.ui.widget.ViewPager.Indicate;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.common.common.ui.widget.Scrolling.MixScroll.Base.SizeUtil;

/**
 * by ckckck 2019/1/22
 * <p>
 * life is short , bugs are too many!
 */
public class NumberIndicate implements IIndicateDrawer {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Indicate indicate;
    private int[] size = new int[2];
    RectF rectF = new RectF();
    int current, maxCount;

    int RADIUS;

    public NumberIndicate() {
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPaint.setColor(indicate.getResources().getColor(android.R.color.darker_gray)&0x80FFFFFF);
        canvas.drawCircle(RADIUS, RADIUS, RADIUS, mPaint);


        /**
         * 计算基线位置
         */
        String text = (current+1) + "/" + maxCount;
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        float baseLineY = RADIUS - (fm.ascent - (fm.ascent - fm.descent) / 2);
        float v = mPaint.measureText(text);
        mPaint.setColor(indicate.getResources().getColor(android.R.color.white));
        canvas.drawText(text, RADIUS - v / 2, baseLineY, mPaint);
    }

    @Override
    public void initCount(int current, int maxCount) {
        this.current = current;
        this.maxCount = maxCount;
    }

    @Override
    public int[] onMeasure(Indicate indicate, int widthMeasureSpec, int heightMeasureSpec) {
        this.indicate = indicate;
        mPaint.setTextSize(SizeUtil.dp2px(indicate.getContext(), 17));
        int width = (int) mPaint.measureText(maxCount + "/" + maxCount)+SizeUtil.dp2px(indicate.getContext(), 6);
        RADIUS = width / 2 ;
        size[0] = width;
        size[1] = width;
        return size;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        current = i % maxCount;
        indicate.invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
