package com.pt.module_order.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.pt.lib_common.themvp.presenter.FragmentPresenter;
import com.pt.module_order.delegate.Order2BuyFgtDelegate;

public class Order2BuyFragment extends FragmentPresenter<Order2BuyFgtDelegate> {
    @Override
    public void initData() {

    }

    @Override
    protected Class<Order2BuyFgtDelegate> getDelegateClass() {
        return Order2BuyFgtDelegate.class;
    }

    public static Order2BuyFragment newInstance() {
        Bundle args = new Bundle();
        Order2BuyFragment fragment = new Order2BuyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        viewDelegate.srl_order_buy.autoRefresh();
    }
}
