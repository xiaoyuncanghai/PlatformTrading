package com.pt.lib_common.ui.component;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.ui.delegate.OrderCategoryActDelegate;

@Route(path = ARouterPath.ORDER_CATEGORY)
public class OrderCategoryActivity extends ActivityPresenter<OrderCategoryActDelegate> {
    @Override
    protected Class<OrderCategoryActDelegate> getDelegateClass() {
        return OrderCategoryActDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader(getIntent().getStringExtra(Constant.KEY_CATEGORY_NAME));
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
