package com.pt.module_order.fragment;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.module_order.delegate.OrderDetailActDelegate;

@Route(path = ARouterPath.ORDER_DETAIL)
public class OrderDetailActivity extends ActivityPresenter<OrderDetailActDelegate> {
    @Override
    protected Class<OrderDetailActDelegate> getDelegateClass() {
        return OrderDetailActDelegate.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader();
    }

    private void initHeader() {
        setTitle("订单详情");
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
