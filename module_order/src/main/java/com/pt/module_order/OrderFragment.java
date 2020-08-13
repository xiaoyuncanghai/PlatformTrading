package com.pt.module_order;

import android.os.Bundle;

import com.pt.lib_common.themvp.presenter.FragmentPresenter;

public class OrderFragment extends FragmentPresenter<OrderFragmentDelegate> {


    @Override
    public void initData() {

    }

    public static OrderFragment newInstance() {
        Bundle args = new Bundle();
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Class<OrderFragmentDelegate> getDelegateClass() {
        return OrderFragmentDelegate.class;
    }
}
