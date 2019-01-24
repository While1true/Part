package com.part.common.ui.widget.ViewPager.Indicate;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.part.common.ui.widget.Scrolling.MixScroll.Base.SizeUtil;

/**
 * by ckckck 2019/1/22
 * <p>
 * life is short , bugs are too many!
 */
public class RoundRectIndicate implements IIndicateDrawer {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Indicate indicate;
    private int[] size = new int[2];
    int WIDTH = 9;
    int HEIGHT = 4;
    RectF rectF = new RectF();
    int current, maxCount;

    int WIDTHPX;
    int HEIGHTPX;

    public RoundRectIndicate() {
    }

    @Override
    public void onDraw(Canvas canvas) {
        mPaint.setColor(indicate.getResources().getColor(android.R.color.darker_gray));
        int gap = WIDTHPX / 3;
        for (int i = 0; i < maxCount; i++) {
            rectF.set(gap * (i + 1)+WIDTHPX*i, 0, gap * (i + 1) + WIDTHPX*(i+1), HEIGHTPX);
            canvas.drawRoundRect(rectF, HEIGHTPX / 2, HEIGHTPX / 2, mPaint);
        }
        mPaint.setColor(indicate.getResources().getColor(android.R.color.white));
        rectF.set(gap * (current + 1)+WIDTHPX*current, 0, gap * (current + 1) + + WIDTHPX*(current+1), HEIGHTPX);
        canvas.drawRoundRect(rectF, HEIGHTPX / 2, HEIGHTPX / 2, mPaint);
    }

    @Override
    public void initCount(int current, int maxCount) {
        this.current = current;
        this.maxCount = maxCount;
    }

    @Override
    public int[] onMeasure(Indicate indicate, int widthMeasureSpec, int heightMeasureSpec) {
        this.indicate = indicate;
        WIDTHPX = SizeUtil.dp2px(indicate.getContext(), WIDTH);
        HEIGHTPX = SizeUtil.dp2px(indicate.getContext(), HEIGHT);
        size[0] = SizeUtil.dp2px(indicate.getContext(), maxCount * WIDTH + (maxCount + 1) * WIDTH / 3);
        size[1] = SizeUtil.dp2px(indicate.getContext(), HEIGHT);
        return size;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        current = i%maxCount;
        indicate.invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
