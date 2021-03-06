package com.pt.module_order.delegate;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.bean.databean.FundSideItem;
import com.pt.lib_common.bean.jsonbean.OrderOpratelJsonBean;
import com.pt.lib_common.bean.requestBean.OrderCancelRequestBean;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.SPHelper;
import com.pt.module_order.R;
import com.pt.module_order.adapter.Order2BuyAdapter;
import com.pt.module_order.bean.data.OrderItemBean;
import com.pt.module_order.bean.json.ApplyFunderJsonBean;
import com.pt.module_order.bean.json.OrderBuyJsonBean;
import com.pt.module_order.bean.rquest.ApplyFunderRequestBean;
import com.pt.module_order.bean.rquest.OrderAllRequestBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import retrofit2.converter.gson.GsonConverterFactory;

public class Order2BuyFgtDelegate extends AppDelegate {

    public SmartRefreshLayout srl_order_buy;
    private RecyclerView rcv_order_buy;
    private ArrayList<OrderItemBean> buyList = new ArrayList<>();
    private ArrayList<OrderItemBean> buyListTemp = new ArrayList<>();
    private int cpage = 1;
    private Order2BuyAdapter order2BuyAdapter;
    int current_position = -1;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_order_buy;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        srl_order_buy = get(R.id.srl_order_buy);
        rcv_order_buy = get(R.id.rcv_order_buy);

        rcv_order_buy.setLayoutManager(new LinearLayoutManager(getActivity()));
        order2BuyAdapter = new Order2BuyAdapter(getActivity(), R.layout.item_order_buy, buyList, 1);
        rcv_order_buy.setAdapter(order2BuyAdapter);
        rcv_order_buy.setItemAnimator(new DefaultItemAnimator());
        srl_order_buy.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
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
        order2BuyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(ARouterPath.ORDER_DETAIL)
                        .withString(Constant.KEY_ORDER_ID, buyList.get(position).getId())
                        .withInt(Constant.ORDER_USER_TYPE, 1)
                        .navigation();
            }
        });

        order2BuyAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.order_buy_item_confirm) {
                    current_position = position;
                    ARouter.getInstance().build(ARouterPath.FUND_SIDE)
                            .navigation(getActivity(), Constant.KEY_APPLY_MONEY_FROM_ORDER_LIST);
                }

                if (view.getId() == R.id.order_buy_item_cancel) {
                    //取消订单
                    requestCancel(buyList.get(position).getId(), 1, position);
                }
            }
        });
    }

    private void requestCancel(String order_id, int user_type, final int position) {
        OrderCancelRequestBean requestBean = new OrderCancelRequestBean();
        requestBean.setId(order_id);
        requestBean.setOrderSource(user_type);
        EasyHttp.post(HttpConstant.API_CANCEL_ORDER).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                    }

                    @Override
                    public void onSuccess(String s) {
                        OrderOpratelJsonBean jsonBean = new Gson().fromJson(s, OrderOpratelJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            buyList.get(position).setOrderStatus(-10);
                            buyList.get(position).setOrderStatusDes("已取消");
                            order2BuyAdapter.notifyItemChanged(position);
                        }
                    }
                });

    }

    private void requestList() {
        OrderAllRequestBean requestBean = new OrderAllRequestBean();
        requestBean.setCurrent(cpage);
        EasyHttp.post(HttpConstant.API_ORDER_BUY).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        LogUtils.d("s = " + s);
                        OrderBuyJsonBean jsonBean = new Gson().fromJson(s, OrderBuyJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            if (jsonBean.getData() != null && jsonBean.getData().getRecords() != null
                                    && jsonBean.getData().getRecords().size() > 0) {
                                buyListTemp.clear();
                                for (OrderBuyJsonBean.DataBean.RecordsBean recordsBean : jsonBean.getData().getRecords()) {
                                    OrderItemBean itemBean = new OrderItemBean();
                                    itemBean.setId(recordsBean.getId());
                                    itemBean.setPic(recordsBean.getPic1Url());
                                    itemBean.setTitle(recordsBean.getTitle());
                                    itemBean.setDescription(recordsBean.getDescription());
                                    itemBean.setCreate_time(recordsBean.getCreateTime());
                                    itemBean.setPrice(recordsBean.getPrice());
                                    itemBean.setOrderStatus(recordsBean.getOrderStatus());
                                    itemBean.setOrderStatusDes(recordsBean.getOrderStatusDes());
                                    buyListTemp.add(itemBean);
                                }
                                if (srl_order_buy.isRefreshing()) {
                                    buyList.clear();
                                }
                                buyList.addAll(buyListTemp);
                                order2BuyAdapter.notifyDataSetChanged();
                                if (srl_order_buy.isRefreshing()) {
                                    srl_order_buy.finishRefresh();
                                } else if (srl_order_buy.isLoading()) {
                                    srl_order_buy.finishLoadmore();
                                }
                            } else {
                                if (srl_order_buy.isRefreshing()) {
                                    srl_order_buy.finishRefresh();
                                } else if (srl_order_buy.isLoading()) {
                                    srl_order_buy.finishLoadmoreWithNoMoreData();
                                }
                            }
                        } else if (jsonBean.getCode() == 401) {
                            SPHelper.putString("token", "", true);
                            SPHelper.putString("phone", "", true);
                            ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                            getActivity().finish();
                        } else if (jsonBean.getCode() == 500) {
                            Snackbar.make(srl_order_buy, "服务端返回数据有误", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void refreshApplyMoneyItemData(FundSideItem item) {
        if (current_position != -1) {
            ApplyFunderRequestBean requestBean = new ApplyFunderRequestBean();
            requestBean.setFunderPhone(item.getPhone());
            requestBean.setOrderId(buyList.get(current_position).getId());
            EasyHttp.post(HttpConstant.API_APPLY_FUNDER).headers("Content-Type", "application/json")
                    .addConverterFactory(GsonConverterFactory.create())
                    .upObject(requestBean)
                    .execute(new SimpleCallBack<String>() {
                        @Override
                        public void onError(ApiException e) {
                        }

                        @Override
                        public void onSuccess(String s) {
                            ApplyFunderJsonBean jsonBean = new Gson().fromJson(s, ApplyFunderJsonBean.class);
                            if (jsonBean.getCode() == 0) {
                                buyList.get(current_position).setOrderStatus(10);
                                buyList.get(current_position).setOrderStatusDes("待资金方确认");
                                order2BuyAdapter.notifyItemChanged(current_position);
                            }
                        }
                    });
        }
    }
}
