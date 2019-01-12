package com.part.common.ui.widget.Scrolling.MixScroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.part.common.ui.widget.Scrolling.MixScroll.Base.RefreshState;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.Refreshable;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.SizeUtil;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.ViewControlUtil;
import com.part.common.ui.widget.Scrolling.MixScroll.Base.XmlAttr;
import com.part.common.ui.widget.Scrolling.ScrollDirection;
import com.part.common.ui.widget.Scrolling.Scrolling;


/**
 * by ckckck 2018/12/28
 * <p>
 * life is short , bugs are too many!
 */
public class EmptyHeaderFooter extends View implements Refreshable {
    boolean isHeader=true;
    public EmptyHeaderFooter(Context context) {
        super(context);
    }

    public EmptyHeaderFooter(Context context, boolean isHeader) {
        this(context);
        this.isHeader=isHeader;
    }

    public EmptyHeaderFooter(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyHeaderFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(attrs!=null){
            XmlAttr xmlAttr = ViewControlUtil.getXmlAttr(attrs);
            isHeader=xmlAttr.isHeader();
        }
    }


    @Override
    public void onLayout(ScrollDirection direction, Scrolling parent, boolean changed, int left, int top, int right, int bottom) {
      if(changed)
       ViewControlUtil.onLayout(direction,this,parent,bottom);
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }


    @Override
    public View getContentView() {
        return this;
    }

    @Override
    public void onPull(float pull, boolean fling) {

    }

    @Override
    public void onStateChange(RefreshState state) {

    }

    @Override
    public int canPullSpace() {
        return SizeUtil.dp2px(getContext(),200);
    }

    @Override
    public int getRefreshSpace() {
        return 0;
    }

}
