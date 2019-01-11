package com.part.common.ui.widget.Scrolling.MixScroll.Base;

/**
 * @author ckckck  ${data}
 * life is short ,bugs are too many!
 */
public abstract class RefreshListener implements OnScrollStateListener {
    @Override
    public final void onStateChange(RefreshState state) {
        if(state==RefreshState.REFRESHING){
            onRefresh();
        }else if(state==RefreshState.LOADING){
            onLoading();
        }
    }

    @Override
    public final void onScroll(int scrollX, int scrollY) {

    }
   public abstract void onRefresh();

   public abstract void onLoading();
}
