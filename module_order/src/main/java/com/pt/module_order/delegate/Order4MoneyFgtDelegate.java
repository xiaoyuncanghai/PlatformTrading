package com.pt.module_order.delegate;

import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.SPHelper;
import com.pt.module_order.R;
import com.pt.module_order.adapter.Order2BuyAdapter;
import com.pt.module_order.bean.data.OrderItemBean;
import com.pt.module_order.bean.json.OrderBuyJsonBean;
import com.pt.module_order.bean.rquest.OrderAllRequestBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import retrofit2.converter.gson.GsonConverterFactory;

public class Order4MoneyFgtDelegate extends AppDelegate {
    public SmartRefreshLayout srl_order_money;
    private RecyclerView rcv_order_money;
    private ArrayList<OrderItemBean> moneyList = new ArrayList<>();
    private ArrayList<OrderItemBean> moneyListTemp = new ArrayList<>();
    private int cpage = 1;
    private Order2BuyAdapter order2BuyAdapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_order_buy;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        srl_order_money = get(R.id.srl_order_buy);
        rcv_order_money = get(R.id.rcv_order_buy);
        rcv_order_money.setLayoutManager(new LinearLayoutManager(getActivity()));
        order2BuyAdapter = new Order2BuyAdapter(getActivity(), R.layout.item_order_buy, moneyList);
        rcv_order_money.setAdapter(order2BuyAdapter);
        rcv_order_money.setItemAnimator(new DefaultItemAnimator());
        srl_order_money.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                cpage++;
                requestList();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                cpage = 1;
                requestList();
            }
        });
    }

    private void requestList() {
        OrderAllRequestBean requestBean = new OrderAllRequestBean();
        requestBean.setCurrent(cpage);
        EasyHttp.post(HttpConstant.API_ORDER_MONEY).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        OrderBuyJsonBean jsonBean = new Gson().fromJson(s, OrderBuyJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            if (jsonBean.getData() != null && jsonBean.getData().getRecords() != null
                                    && jsonBean.getData().getRecords().size() > 0) {
                                moneyListTemp.clear();
                                for (OrderBuyJsonBean.DataBean.RecordsBean recordsBean : jsonBean.getData().getRecords()) {
                                    OrderItemBean itemBean = new OrderItemBean();
                                    itemBean.setGoodsType(recordsBean.getGoodsType());
                                    itemBean.setId(recordsBean.getId());
                                    itemBean.setDescription(recordsBean.getDescription());
                                    itemBean.setTitle(recordsBean.getTitle());
                                    itemBean.setPrice(recordsBean.getPrice());
                                    itemBean.setOrderStatus(recordsBean.getOrderStatus());
                                    moneyListTemp.add(itemBean);
                                }
                                if (srl_order_money.isRefreshing()) {
                                    moneyList.clear();
                                }
                                moneyList.addAll(moneyListTemp);
                                order2BuyAdapter.notifyItemChanged(moneyList.size() - moneyListTemp.size(),
                                        moneyListTemp.size());
                                if (srl_order_money.isRefreshing()) {
                                    srl_order_money.finishRefresh();
                                } else if (srl_order_money.isLoading()) {
                                    srl_order_money.finishLoadmore();
                                }
                            } else {
                                if (srl_order_money.isRefreshing()) {
                                    srl_order_money.finishRefresh();
                                } else if (srl_order_money.isLoading()) {
                                    srl_order_money.finishLoadmoreWithNoMoreData();
                                }
                            }
                        } else if (jsonBean.getCode() == 401) {
                            SPHelper.putString("token", "", true);
                            SPHelper.putString("phone", "", true);
                            ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                        } else if (jsonBean.getCode() == 500) {
                            Snackbar.make(srl_order_money, "服务端返回数据有误", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
