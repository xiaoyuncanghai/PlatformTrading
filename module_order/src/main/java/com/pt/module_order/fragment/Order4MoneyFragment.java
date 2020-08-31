package com.pt.module_order.fragment;

import android.os.Bundle;

import com.pt.lib_common.themvp.presenter.FragmentPresenter;
import com.pt.module_order.delegate.Order4MoneyFgtDelegate;

public class Order4MoneyFragment extends FragmentPresenter<Order4MoneyFgtDelegate> {
    @Override
    public void initData() {

    }

    @Override
    protected Class<Order4MoneyFgtDelegate> getDelegateClass() {
        return Order4MoneyFgtDelegate.class;
    }

    public static Order4MoneyFragment newInstance() {
        Bundle args = new Bundle();
        Order4MoneyFragment fragment = new Order4MoneyFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
