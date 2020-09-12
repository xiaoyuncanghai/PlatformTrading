package com.pt.module_order.delegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
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
import com.pt.module_order.bean.json.ApplyFunderJsonBean;
import com.pt.module_order.bean.json.OrderDetailJsonBean;
import com.pt.module_order.bean.rquest.ApplyFunderRequestBean;
import com.pt.module_order.bean.rquest.OrderDetailRequestBean;

import retrofit2.converter.gson.GsonConverterFactory;

import static com.pt.lib_common.constants.Constant.*;

public class OrderDetailActDelegate extends AppDelegate {

    private TextView order_detail_id;
    private TextView order_detail_price;
    private TextView order_detail_name;
    private TextView order_detail_phone;
    private ImageView order_detail_img1;
    private ImageView order_detail_img2;
    private ImageView order_detail_img3;
    private TextView order_detail_status;
    private String order_id;
    private int user_type;
    private TextView order_cancel;
    private TextView order_apply;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_detail_order;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        order_detail_id = get(R.id.order_detail_id);
        order_detail_price = get(R.id.order_detail_price);
        order_detail_name = get(R.id.order_detail_name);
        order_detail_phone = get(R.id.order_detail_phone);
        order_detail_img1 = get(R.id.order_detail_img1);
        order_detail_img2 = get(R.id.order_detail_img2);
        order_detail_img3 = get(R.id.order_detail_img3);
        order_detail_status = get(R.id.order_detail_status);
        order_cancel = get(R.id.order_cancel);
        order_apply = get(R.id.order_apply);
        order_id = getActivity().getIntent().getStringExtra(KEY_ORDER_ID);
        //获取当前的类型
        user_type = getActivity().getIntent().getIntExtra(ORDER_USER_TYPE, 1);
        requestOrderDetail();

        order_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //买入
                if (order_apply.isEnabled() && user_type == 1) {
                     //申请资金方, 申请垫资, 去资金方列表去
                    //ARouter.getInstance().build(ARouterPath.LOGIN_ACTIVITY).navigation(getActivity() , Constant.REQUEST_CODE_LABEL_TO_LOGIN);
                    ARouter.getInstance().build(ARouterPath.FUND_SIDE)
                            .navigation(getActivity() , KEY_ORDER_FUNDER);
                }

                if (order_apply.isEnabled() && user_type == 3) {
                    //资金方确认
                    requestMoneyConfirm();
                }
            }
        });

        order_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //买入
                if (order_cancel.isEnabled() && user_type == 1) {
                    //取消订单
                    requestCancelOrder(user_type);
                }
            }
        });
    }

    /**
     * 资金方确认
     */
    private void requestMoneyConfirm() {
        OrderCancelRequestBean requestBean = new OrderCancelRequestBean();
        requestBean.setId(order_id);
        requestBean.setUserType(user_type);
        EasyHttp.post(HttpConstant.API_MONEY_CONFIRM).headers("Content-Type", "application/json")
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
                            Snackbar.make(getRootView(), "资金方已同意", Snackbar.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }
                });
    }

    private void requestCancelOrder(int user_type) {
        OrderCancelRequestBean requestBean = new OrderCancelRequestBean();
        requestBean.setId(order_id);
        requestBean.setUserType(user_type);
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
                            Snackbar.make(getRootView(), "订单已取消", Snackbar.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }
                });
    }

    /**
     * 请求网络
     */
    private void requestOrderDetail() {
        OrderDetailRequestBean requestBean = new OrderDetailRequestBean();
        requestBean.setId(order_id);
        requestBean.setUserType(user_type);
        EasyHttp.post(HttpConstant.API_ORDER_DETAIL).headers("Content-Type", "application/json")
                .headers("Authorization", SPHelper.getString("token", "", true))
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        OrderDetailJsonBean jsonBean = new Gson().fromJson(s, OrderDetailJsonBean.class);
                        if (jsonBean.getCode() == 0 && jsonBean.getData() != null) {
                            order_detail_id.setText("订单号: "+jsonBean.getData().getId());
                            order_detail_price.setText("价格: ￥"+jsonBean.getData().getPrice());
                            order_detail_name.setText("姓名:" + jsonBean.getData().getPerson());
                            order_detail_phone.setText("电话: "+ jsonBean.getData().getPhone());
                            if (!jsonBean.getData().getPic1Url().equals("")) {
                                order_detail_img1.setVisibility(View.VISIBLE);
                                Glide.with(getActivity())
                                        .load(jsonBean.getData().getPic1Url())
                                        .placeholder(com.pt.lib_common.R.drawable.ic_place_holder)
                                        .centerCrop()
                                        .error(com.pt.lib_common.R.drawable.ic_place_holder).into(order_detail_img1);
                            }

                            if (!jsonBean.getData().getPic2Url().equals("")) {
                                order_detail_img2.setVisibility(View.VISIBLE);
                                Glide.with(getActivity())
                                        .load(jsonBean.getData().getPic2Url())
                                        .placeholder(com.pt.lib_common.R.drawable.ic_place_holder)
                                        .centerCrop()
                                        .error(com.pt.lib_common.R.drawable.ic_place_holder).into(order_detail_img2);
                            }

                            if (!jsonBean.getData().getPic3Url().equals("")) {
                                order_detail_img3.setVisibility(View.VISIBLE);
                                Glide.with(getActivity())
                                        .load(jsonBean.getData().getPic3Url())
                                        .placeholder(com.pt.lib_common.R.drawable.ic_place_holder)
                                        .centerCrop()
                                        .error(com.pt.lib_common.R.drawable.ic_place_holder).into(order_detail_img3);
                            }

                            if (user_type == 1) {
                                //买入的情况
                                if (jsonBean.getData().getOrderStatus() == 0){
                                    //取消和申请资金方
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.VISIBLE);
                                    order_cancel.setEnabled(true);
                                    order_apply.setEnabled(true);
                                    order_cancel.setText("取消订单");
                                    order_apply.setText("申请资金方");
                                    order_detail_status.setText("还未申请资金方");
                                } else if (jsonBean.getData().getOrderStatus() == -10) {
                                    order_apply.setVisibility(View.INVISIBLE);
                                    order_apply.setEnabled(false);
                                    order_cancel.setVisibility(View.INVISIBLE);
                                    order_cancel.setEnabled(false);
                                    //order_cancel.setText("订单已取消");
                                    order_detail_status.setText("订单已取消");
                                } else if (jsonBean.getData().getOrderStatus() == 10) {
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.INVISIBLE);
                                    order_cancel.setEnabled(true);
                                    order_apply.setEnabled(false);
                                    order_cancel.setText("取消订单");
                                    order_detail_status.setText("资金方确认中");
                                } else if (jsonBean.getData().getOrderStatus() == 20) {
                                    order_cancel.setVisibility(View.INVISIBLE);
                                    order_apply.setVisibility(View.VISIBLE);
                                    order_cancel.setEnabled(false);
                                    order_apply.setEnabled(false);
                                    order_detail_status.setText("资金方已同意");
                                }
                            } else if (user_type == 2){
                                //卖的情况, 显示取消
                                if (jsonBean.getData().getOrderStatus() == 0 ) {
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.INVISIBLE);
                                    order_cancel.setEnabled(true);
                                    order_apply.setEnabled(false);
                                    order_cancel.setText("取消订单");
                                }

                            } else if (user_type == 3){
                                //资金方
                                if (jsonBean.getData().getOrderStatus() == 10) {
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.VISIBLE);
                                    order_cancel.setEnabled(true);
                                    order_apply.setEnabled(true);
                                    order_cancel.setText("取消订单");
                                    order_apply.setText("确认订单");
                                }
                            }

                        }
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == KEY_ORDER_FUNDER && resultCode == KEY_ORDER_FUNDER_RESULT) {
            Bundle bundle = data.getBundleExtra(Constant.KEY_CHOOSE_FUND);
            FundSideItem item = (FundSideItem) bundle.getSerializable(CHOOSE_FUND_ITEM);
            //请求网络
            ApplyFunderRequestBean requestBean = new ApplyFunderRequestBean();
            requestBean.setFunderPhone(item.getPhone());
            requestBean.setOrderId(order_id);
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
                                Snackbar.make(getRootView(), "正在申请资金方垫资", Snackbar.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        }
                    });
        }
    }
}
