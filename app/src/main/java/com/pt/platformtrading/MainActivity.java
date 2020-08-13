package com.pt.platformtrading;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.platformtrading.delegate.MainActDelegate;

@Route(path= ARouterPath.MAIN_PATH)
public class MainActivity extends ActivityPresenter<MainActDelegate> {

    @Override
    protected Class<MainActDelegate> getDelegateClass() {
        return MainActDelegate.class;
    }
}