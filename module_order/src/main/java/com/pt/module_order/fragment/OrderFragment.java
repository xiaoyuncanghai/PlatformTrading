package com.pt.module_order.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.pt.lib_common.themvp.presenter.FragmentPresenter;
import com.pt.module_order.delegate.OrderFragmentDelegate;

public class OrderFragment extends FragmentPresenter<OrderFragmentDelegate> {
    @Override
    public void initData() { }

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initHeader();
        viewDelegate.initVp(getActivity().getSupportFragmentManager());
    }

    private void initHeader() {
        setTitle("订单");
    }
}
