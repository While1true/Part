package com.part.common.ui.widget.DecorateView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * by ckckck 2019/1/21
 * <p>
 * life is short , bugs are too many!
 */
public class DecorateView extends FrameLayout {
    private IDecorate iDecorate=new QuadDecorate();

    public DecorateView(Context context) {
        this(context,null);
    }

    public DecorateView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DecorateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        iDecorate.onSizeChanged(this,w, h, oldw, oldh);
    }

    public IDecorate getDecorate() {
        return iDecorate;
    }

    public void setDecorate(IDecorate iDecorate) {
        this.iDecorate = iDecorate;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        iDecorate.onDraw(canvas);
    }
}
