package com.part.common.ui.widget.Scrolling.MixScroll;

import android.view.View;

import com.part.common.ui.widget.Scrolling.ScrollDirection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * by ckckck 2019/1/18
 * <p>
 * life is short , bugs are too many!
 */
public class ScrollUtil {

    public static int getCanScrollDistanceToBottom(View scrollview, ScrollDirection direction){
       int scrollDistance=0;

        if(direction==ScrollDirection.X){
            Method computeHorizontalScrollRange = getMethod(scrollview.getClass(), "computeHorizontalScrollRange");
            Method computeHorizontalScrollOffset = getMethod(scrollview.getClass(), "computeHorizontalScrollOffset");
            Method computeHorizontalScrollExtent = getMethod(scrollview.getClass(), "computeHorizontalScrollExtent");
            try {
                scrollDistance= (int) computeHorizontalScrollRange.invoke(scrollview)-(int) computeHorizontalScrollOffset.invoke(scrollview)-(int) computeHorizontalScrollExtent.invoke(scrollview);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }else {
            Method computeVerticalScrollRange = getMethod(scrollview.getClass(), "computeVerticalScrollRange");
            Method computeVerticalScrollOffset = getMethod(scrollview.getClass(), "computeVerticalScrollOffset");
            Method computeVerticalScrollExtent = getMethod(scrollview.getClass(), "computeVerticalScrollExtent");
            try {
                scrollDistance= (int) computeVerticalScrollRange.invoke(scrollview)-(int) computeVerticalScrollOffset.invoke(scrollview)-(int) computeVerticalScrollExtent.invoke(scrollview);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return scrollDistance;
    }
    public static int getCanScrollDistanceToTop(View scrollview, ScrollDirection direction){
        int scrollDistance=0;

        if(direction==ScrollDirection.X){
            Method computeHorizontalScrollOffset = getMethod(scrollview.getClass(), "computeHorizontalScrollOffset");
            try {
                scrollDistance=(int) computeHorizontalScrollOffset.invoke(scrollview);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }else {
            Method computeVerticalScrollOffset = getMethod(scrollview.getClass(), "computeVerticalScrollOffset");
            try {
                scrollDistance=(int) computeVerticalScrollOffset.invoke(scrollview);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return scrollDistance;
    }
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... ParamsType) {
        Method method = null;
        while (clazz != null) {
            try {
                method = clazz.getDeclaredMethod(methodName, ParamsType);
                method.setAccessible(true);
                return method;
            } catch (Exception e) {
            }
            clazz = clazz.getSuperclass();
        }
        return method;
    }
}
