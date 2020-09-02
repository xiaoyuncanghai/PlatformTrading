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
import com.pt.module_order.adapter.Order1AllAdapter;
import com.pt.module_order.bean.data.OrderItemBean;
import com.pt.module_order.bean.json.OrderAllJsonBean;
import com.pt.module_order.bean.rquest.OrderAllRequestBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import retrofit2.converter.gson.GsonConverterFactory;

public class Order1AllFgtDelegate extends AppDelegate {

    public SmartRefreshLayout srl_order_all;
    private RecyclerView rcv_order_all;
    private ArrayList<OrderItemBean> allLit = new ArrayList<>();
    private ArrayList<OrderItemBean> allLitTemp = new ArrayList<>();
    private Order1AllAdapter order1AllAdapter;
    private int cpage = 1;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_order_all;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        srl_order_all = get(R.id.srl_order_all);
        rcv_order_all = get(R.id.rcv_order_all);
        rcv_order_all.setLayoutManager(new LinearLayoutManager(getActivity()));
        order1AllAdapter = new Order1AllAdapter(getActivity(), R.layout.item_order_all, allLit);
        rcv_order_all.setAdapter(order1AllAdapter);
        rcv_order_all.setItemAnimator(new DefaultItemAnimator());

        srl_order_all.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
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
        EasyHttp.post(HttpConstant.API_ORDER_ALL).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                    }

                    @Override
                    public void onSuccess(String s) {
                        OrderAllJsonBean jsonBean = new Gson().fromJson(s, OrderAllJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            if (jsonBean.getData() != null && jsonBean.getData().getRecords() != null
                                    && jsonBean.getData().getRecords().size() > 0) {
                                allLitTemp.clear();
                                for (OrderAllJsonBean.DataBean.RecordsBean recordsBean :
                                        jsonBean.getData().getRecords()) {
                                    OrderItemBean itemBean = new OrderItemBean();
                                    itemBean.setGoodsType(recordsBean.getGoodsType());
                                    itemBean.setId(recordsBean.getId());
                                    itemBean.setDescription(recordsBean.getDescription());
                                    itemBean.setTitle(recordsBean.getTitle());
                                    itemBean.setPrice(recordsBean.getPrice());
                                    itemBean.setOrderStatus(recordsBean.getOrderStatus());
                                    allLitTemp.add(itemBean);
                                }
                                if (srl_order_all.isRefreshing()) {
                                    allLit.clear();
                                }
                                allLit.addAll(allLitTemp);
                                order1AllAdapter.notifyItemChanged(allLit.size() - allLitTemp.size(), allLitTemp.size());
                                if (srl_order_all.isRefreshing()) {
                                    srl_order_all.finishRefresh();
                                } else if (srl_order_all.isLoading()) {
                                    srl_order_all.finishLoadmore();
                                }
                            } else {
                                if (srl_order_all.isRefreshing()) {
                                    srl_order_all.finishRefresh();
                                } else if (srl_order_all.isLoading()) {
                                    srl_order_all.finishLoadmoreWithNoMoreData();
                                }
                            }
                        } else if (jsonBean.getCode() == 401) {
                            SPHelper.putString("token", "", true);
                            SPHelper.putString("phone", "", true);
                            ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                            getActivity().finish();
                        } else if (jsonBean.getCode() == 500) {
                            Snackbar.make(srl_order_all, "服务端返回数据有误", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
