package com.part.common.ui.widget.Scrolling.MixScroll.Base;


import com.part.common.ui.widget.Scrolling.ScrollDirection;

/**
 * @author ckckck  ${data}
 * life is short ,bugs are too many!
 */
public class XmlAttr {
    private boolean isHeader=true;
    private ScrollDirection scrollDirection=ScrollDirection.Y;

    public XmlAttr() {
    }

    public XmlAttr(boolean isHeader, ScrollDirection scrollDirection) {
        this.isHeader = isHeader;
        this.scrollDirection = scrollDirection;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public ScrollDirection getScrollDirection() {
        return scrollDirection;
    }

    public void setScrollDirection(ScrollDirection scrollDirection) {
        this.scrollDirection = scrollDirection;
    }
}
