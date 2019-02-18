package com.common.common.ui.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import javax.inject.Inject;

/**
 * by ckckck 2019/1/11
 * <p>
 * life is short , bugs are too many!
 */
public class StateLayout extends FrameLayout {
    SparseArray<View> views = new SparseArray<>();
    private int contentViewID = -1;

    @Inject
    public StateLayout(@NonNull Context context) {
        this(context, null);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public View setContent(View view) {
        if (views.get(contentViewID) != null) {
            views.remove(contentViewID);
            removeView(views.get(contentViewID));
        }
        this.contentViewID = 123456;
        views.put(contentViewID, view);
        addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        return this;
    }

    public View setContent(@LayoutRes int contentViewID) {
        if (views.get(contentViewID) != null) {
            views.remove(contentViewID);
            removeView(views.get(contentViewID));
        }
        this.contentViewID = contentViewID;
        showView(contentViewID);
        return this;
    }

    public void showContent() {
        if (contentViewID != -1) {
            showView(contentViewID);
        }
    }

    public View getStateView(@LayoutRes int viewID) {
        return views.get(viewID);
    }

    public void showView(@LayoutRes int emptyId) {
        View view = views.get(emptyId);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(emptyId, this, false);
            addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            views.put(emptyId, view);
        }
        int size = views.size();
        for (int i = 0; i < size; i++) {
            if (views.valueAt(i) != view) {
                views.valueAt(i).setVisibility(GONE);
            }
        }
    }
}
