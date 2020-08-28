package com.pt.lib_common.ui.component;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.ui.delegate.SplashActDelegate;

@Route(path= ARouterPath.SPLASH_PATH)
public class SplashActivity extends ActivityPresenter<SplashActDelegate> {
    @Override
    protected Class<SplashActDelegate> getDelegateClass() {
        return SplashActDelegate.class;
    }
}
