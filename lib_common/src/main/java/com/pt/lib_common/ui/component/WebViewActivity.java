package com.pt.lib_common.ui.component;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.ui.delegate.WebViewActDelegate;

@Route(path = ARouterPath.WEB_VIEW_PATH)
public class WebViewActivity extends ActivityPresenter<WebViewActDelegate> {
    @Override
    protected Class<WebViewActDelegate> getDelegateClass() {
        return WebViewActDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader();

    }

    private void initHeader() {
        setTitle("隐私条约");
        setLeft("\ue605");
    }

    @Override
    public void onLeftClick(View v) {
        super.onLeftClick(v);
        finish();
    }

    @Override
    public String getSimpleName() {
        return "内部浏览器";
    }
}
