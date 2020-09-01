package com.pt.lib_common.ui.component;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.ui.delegate.LoginActDelegate;

@Route(path= ARouterPath.PHONE_LOGIN_PATH)
public class LoginActivity extends ActivityPresenter<LoginActDelegate> {
    @Override
    protected Class<LoginActDelegate> getDelegateClass() {
        return LoginActDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader();
    }

    public void initHeader() {
        setTitle("登录");
    }
}
