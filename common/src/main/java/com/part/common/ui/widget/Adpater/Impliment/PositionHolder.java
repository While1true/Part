package com.part.common.ui.widget.Adpater.Impliment;


import com.part.common.ui.widget.Adpater.Base.Holder;
import com.part.common.ui.widget.Adpater.Base.ItemHolder;

/**
 * Created by ck on 2017/10/21.
 */

public abstract class PositionHolder extends ItemHolder<Object> {
    @Override
    public void onBind(Holder holder, Object item, int position) {
        onBind(holder,position);
    }

    @Override
    public boolean istype(Object item, int position) {
        return istype(position);
    }

    public abstract void onBind(Holder holder, int position);

    public abstract boolean istype(int position);
}
