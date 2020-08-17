package com.pt.module_homepage.delegate;

import android.os.Bundle;

import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.module_homepage.R;

public class HomePageFragmentDelegate extends AppDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        initView();
    }

    private void initView() {

    }
}
