package com.part.common.ui.widget.Adpater.Impliment;///*

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;


import com.part.common.ui.widget.Adpater.Base.BaseAdapter;
import com.part.common.ui.widget.Adpater.Base.Holder;
import com.part.common.ui.widget.Adpater.Base.ItemHolder;

import java.util.List;

public class MixAdapter extends BaseAdapter implements LifecycleObserver {
    public MixAdapter(List<?> list) {
        super(list);
    }

    public MixAdapter(int count) {
        super(count);
    }

    public MixAdapter() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return Holder.createViewHolder(InflateView(Holdersid.get(viewType).getLayout(), parent));
    }

    public MixAdapter addLifeOwener(LifecycleOwner owner) {
        owner.getLifecycle().addObserver(this);
        return  this;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    protected void onDestory() {
        super.onDestory();
        Holdersid.clear();
    }

    protected int getType(int position) {
        return getMutilType(list == null ? null : list.get(position), position);
    }


    @Override
    protected void onBindView(Holder holder, Object t, int positon) {
        Holdersid.get(holder.getItemViewType()).onBind(holder, t, positon);
    }

    /**
     * 如果是GridLayoutManager 返回给getspanLookup
     *
     * @param itemViewType
     * @param position
     * @return
     */
    @Override
    public int setIfGridLayoutManagerSpan(int itemViewType, int position, int spanCount) {
        return Holdersid.get(itemViewType).gridSpanSize(list == null ? null : list.get(position), position);
    }

    /**
     * StaggedLayoutManager 是否全屏时调用
     *
     * @param itemViewType
     * @return
     */
    @Override
    public boolean setIfStaggedLayoutManagerFullspan(int itemViewType) {
        return Holdersid.get(itemViewType).isfull();
    }

    private SparseArray<ItemHolder> Holdersid = new SparseArray<>(3);

    public MixAdapter addType(int layoutid, ItemHolder<?> itemholder) {
        Holdersid.put(Holdersid.size(), itemholder.setLayout(layoutid));
        return this;
    }

    public MixAdapter addType(ItemHolder<?> itemholder) {
        if (itemholder.getLayout() == 0) {
            throw new NullPointerException("layoutid not defined");
        }
        Holdersid.put(Holdersid.size(), itemholder);
        return this;
    }

    protected int getMutilType(Object item, int position) {

        for (int i = 0; i < Holdersid.size(); i++) {
            if (Holdersid.valueAt(i).istype(item, position)) {
                return Holdersid.keyAt(i);
            }
        }

        return -1;
    }
}
