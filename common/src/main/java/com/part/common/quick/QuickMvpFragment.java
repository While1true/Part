package com.part.common.quick;

import com.part.common.mvp.IPresenter;
import com.part.common.ui.fragment.BaseMvpFragment;

/**
 * by ckckck 2019/1/25
 * <p>
 * life is short , bugs are too many!
 */
public abstract class QuickMvpFragment<T extends IPresenter> extends BaseMvpFragment<T> {


    abstract void getPageData(int pageNum,int pageSize);
}
