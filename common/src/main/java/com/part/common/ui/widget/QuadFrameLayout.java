package com.part.common.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * by ckckck 2019/1/21
 * <p>
 * life is short , bugs are too many!
 */
public class QuadFrameLayout extends FrameLayout {
    @ColorInt
    private int quarColor=0xFFFFFFFF;
    private int quarHeight;
    private Paint paint;
    private Path path;
    public QuadFrameLayout(Context context) {
        this(context,null);
    }

    public QuadFrameLayout(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QuadFrameLayout(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(quarColor);
        path=new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        path.reset();
        path.moveTo(0,h);
        path.quadTo(w/2,quarHeight<=0?5*h/6:quarHeight,w,h);
        path.close();
    }

    public int getQuarColor() {
        return quarColor;
    }

    public void setQuarColor(@ ColorInt int quarColor) {
        this.quarColor = quarColor;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawPath(path,paint);
    }
}
