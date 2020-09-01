package com.pt.module_order.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.pt.lib_common.themvp.presenter.FragmentPresenter;
import com.pt.module_order.delegate.Order3SaleFgtDelegate;

public class Order3SaleFragment extends FragmentPresenter<Order3SaleFgtDelegate> {
    @Override
    public void initData() {

    }

    @Override
    protected Class<Order3SaleFgtDelegate> getDelegateClass() {
        return Order3SaleFgtDelegate.class;
    }

    public static Order3SaleFragment newInstance() {
        Bundle args = new Bundle();
        Order3SaleFragment fragment = new Order3SaleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        viewDelegate.srl_order_sale.autoRefresh();
    }
}
