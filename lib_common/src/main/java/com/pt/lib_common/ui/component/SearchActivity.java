package com.pt.lib_common.ui.component;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.ui.delegate.SearchActDelegate;

@Route(path = ARouterPath.SEARCH_PATH)
public class SearchActivity extends ActivityPresenter<SearchActDelegate> {
    @Override
    protected Class<SearchActDelegate> getDelegateClass() {
        return SearchActDelegate.class;
    }
}
