package com.part.common.ui.widget.Scrolling.MixScroll.Base;

import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.part.common.ui.widget.Scrolling.ScrollDirection;


/**
 * @author ckckck  ${data}
 * life is short ,bugs are too many!
 */
public class ViewControlUtil {
 public static void onLayout(ScrollDirection direction, Refreshable target, ViewGroup parent, int bottom){
     if (target.isHeader()) {
         if (direction == ScrollDirection.Y) {
             target.getContentView().layout(0, -target.getContentView().getMeasuredHeight(), parent.getMeasuredWidth(), 0);
         } else {
             target.getContentView().layout(-target.getContentView().getMeasuredWidth(), 0, 0, parent.getMeasuredHeight());
         }
     } else {
         if (direction == ScrollDirection.Y) {
             target.getContentView().layout(0, bottom, parent.getMeasuredWidth(), bottom + target.getContentView().getMeasuredHeight());
         } else {
             target.getContentView().layout(parent.getMeasuredWidth(), 0, target.getContentView().getMeasuredWidth() + parent.getMeasuredWidth(), parent.getMeasuredHeight());
         }
     }
 }

 public static XmlAttr getXmlAttr(AttributeSet attrs){
     XmlAttr xmlAttr=new XmlAttr();
     if(attrs==null)
         return xmlAttr;
     int attributeCount = attrs.getAttributeCount();
     for (int i = 0; i < attributeCount; i++) {
         if(attrs.getAttributeName(i).equals("layout_gravity")){
             int gravity = attrs.getAttributeIntValue(i, 0);
             if(gravity==0)
                 break;
             if(gravity==Gravity.TOP||gravity==Gravity.LEFT){
                 xmlAttr.setHeader(true);
             }else if(gravity==Gravity.BOTTOM||gravity==Gravity.RIGHT){
                 xmlAttr.setHeader(false);
             }

             if(gravity==Gravity.TOP||gravity==Gravity.BOTTOM){
                 xmlAttr.setScrollDirection(ScrollDirection.Y);
             }else if(gravity==Gravity.LEFT||gravity==Gravity.RIGHT){
                 xmlAttr.setScrollDirection(ScrollDirection.X);
             }
            break;
         }
     }
     return xmlAttr;
 }
}
