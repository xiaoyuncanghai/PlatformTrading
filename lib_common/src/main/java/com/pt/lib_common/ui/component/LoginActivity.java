package com.pt.lib_common.ui.component;

import android.os.Bundle;
import android.view.View;

import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.ui.delegate.LoginActDelegate;

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

    private void initHeader() {
        setTitle("登录");
    }
}
