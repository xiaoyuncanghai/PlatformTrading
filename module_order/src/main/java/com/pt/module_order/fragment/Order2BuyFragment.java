package com.pt.module_order.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.bean.databean.FundSideItem;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.presenter.FragmentPresenter;
import com.pt.module_order.bean.json.ApplyFunderJsonBean;
import com.pt.module_order.bean.rquest.ApplyFunderRequestBean;
import com.pt.module_order.delegate.Order2BuyFgtDelegate;

import retrofit2.converter.gson.GsonConverterFactory;

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

    public void refreshData(FundSideItem item) {
        viewDelegate.refreshApplyMoneyItemData(item);
    }
}
