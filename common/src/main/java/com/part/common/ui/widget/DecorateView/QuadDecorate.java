package com.part.common.ui.widget.DecorateView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Region;
import android.support.annotation.ColorInt;
import android.view.View;

/**
 * by ckckck 2019/1/28
 * <p>
 * life is short , bugs are too many!
 */
public class QuadDecorate implements IDecorate {
    @ColorInt
    private int quarColor = 0xFFFFFFFF;
    private Paint paint;
    private Path path;
    private View view;
    private QuadMode quadMode = QuadMode.NORMAL;
    private int quarHeight;
    private int W, H;

    @Override
    public void onSizeChanged(View view, int w, int h, int oldw, int oldh) {
        if (path == null) {
            this.view = view;
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(quarColor);
            path = new Path();
        }
        W = w;
        H = h;
        generatePath();
    }

    private void generatePath() {
        path.reset();
        if (quadMode == QuadMode.NORMAL) {
            paint.setStyle(Paint.Style.FILL);
            path.moveTo(0,-1);
            path.lineTo(W,-1);
            int y = quarHeight <= 0 ? 6 * H / 7 : quarHeight;
            path.lineTo(W,y);
            path.quadTo(W / 2, H+H/7, 0, y);
            path.close();
            path.setFillType(Path.FillType.INVERSE_WINDING);
        } else {
            path.moveTo(0, H);
            path.quadTo(W / 2, quarHeight <= 0 ? 5 * H / 6 : quarHeight, W, H);
            path.close();
        }
    }

    public int getQuarColor() {
        return quarColor;
    }

    public QuadMode getQuadMode() {
        return quadMode;
    }

    public void setQuadMode(QuadMode quadMode) {
        this.quadMode = quadMode;
        if (view != null) {
            generatePath();
            view.invalidate();
        }
    }

    public int getQuarHeight() {
        return quarHeight;
    }

    public void setQuarHeight(int quarHeight) {
        this.quarHeight = quarHeight;
        if (view != null) {
            generatePath();
            view.invalidate();
        }
    }

    public void setQuarColor(@ColorInt int quarColor) {
        this.quarColor = quarColor;
        paint.setColor(quarColor);
        if (view != null) {
            view.invalidate();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(quadMode == QuadMode.NORMAL){
            canvas.drawPath(path,paint);
//            canvas.clipPath(path,Region.Op.XOR);
//            canvas.drawColor(quarColor);
        }else {
            canvas.drawPath(path, paint);
        }
    }

    public enum QuadMode {
        NORMAL, REVERSE
    }
}
