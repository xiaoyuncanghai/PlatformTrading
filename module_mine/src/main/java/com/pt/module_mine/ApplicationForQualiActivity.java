package com.pt.module_mine;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.module_mine.delegate.ApplicationForQualiActDelegate;

@Route(path = ARouterPath.APPLICATION_QUALI)
public class ApplicationForQualiActivity extends ActivityPresenter<ApplicationForQualiActDelegate> {
    @Override
    protected Class<ApplicationForQualiActDelegate> getDelegateClass() {
        return ApplicationForQualiActDelegate.class;
    }
}
