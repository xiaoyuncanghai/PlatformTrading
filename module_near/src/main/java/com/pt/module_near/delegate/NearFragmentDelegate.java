package com.pt.module_near.delegate;

import android.os.Bundle;

import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.module_near.R;

public class NearFragmentDelegate extends AppDelegate {
    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_near;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);

    }
}
