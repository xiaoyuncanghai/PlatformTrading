package com.pt.module_near;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.module_near.delegate.AllPersonSearchActDelegete;

@Route(path = ARouterPath.ALL_SEARCH_PATH)
public class AllPersonSearchActivity extends ActivityPresenter<AllPersonSearchActDelegete> {
    @Override
    protected Class<AllPersonSearchActDelegete> getDelegateClass() {
        return AllPersonSearchActDelegete.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader();
    }

    private void initHeader() {
        setTitle("大家都在搜");
        setLeft("\ue605");
        if (tv_left!=null){
            tv_left.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLeftClick(View v) {
        super.onLeftClick(v);
        finish();
    }
}
