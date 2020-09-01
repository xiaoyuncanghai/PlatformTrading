package com.pt.module_order.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.pt.lib_common.themvp.presenter.FragmentPresenter;
import com.pt.module_order.delegate.Order1AllFgtDelegate;

public class Order1AllFragment extends FragmentPresenter<Order1AllFgtDelegate> {
    @Override
    public void initData() {
    }

    public static Order1AllFragment newInstance() {
        Bundle args = new Bundle();
        Order1AllFragment fragment = new Order1AllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected Class<Order1AllFgtDelegate> getDelegateClass() {
        return Order1AllFgtDelegate.class;
    }

    /**
     * 懒加载
     * @param savedInstanceState
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        viewDelegate.srl_order_all.autoRefresh();
    }
}
