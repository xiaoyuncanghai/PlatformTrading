package com.pt.lib_common.ui.component;

import android.os.Bundle;
import android.view.View;

import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.ui.delegate.OrderCategoryActDelegate;

public class OrderCategoryActivity extends ActivityPresenter<OrderCategoryActDelegate> {
    @Override
    protected Class<OrderCategoryActDelegate> getDelegateClass() {
        return OrderCategoryActDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取intent
        initHeader("");
    }

    private void initHeader(String header) {
        setTitle(header);
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
