package com.pt.module_order.delegate;

import android.content.Intent;
import android.net.Uri;
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
import com.pt.module_order.bean.rquest.OrderMoneyRequestBean;

import retrofit2.converter.gson.GsonConverterFactory;

import static com.pt.lib_common.constants.Constant.CHOOSE_FUND_ITEM;
import static com.pt.lib_common.constants.Constant.KEY_ORDER_FUNDER;
import static com.pt.lib_common.constants.Constant.KEY_ORDER_FUNDER_RESULT;
import static com.pt.lib_common.constants.Constant.KEY_ORDER_ID;
import static com.pt.lib_common.constants.Constant.ORDER_USER_TYPE;

public class OrderDetailActDelegate extends AppDelegate {

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
    private TextView order_detail_title;
    private TextView order_detail_description;
    private TextView order_detail_type_des;
    private String phoneNumber;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_detail_order;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        order_detail_title = get(R.id.order_detail_title);
        order_detail_description = get(R.id.order_detail_description);
        order_detail_price = get(R.id.order_detail_price);
        order_detail_name = get(R.id.order_detail_name);
        order_detail_phone = get(R.id.order_detail_phone);
        order_detail_img1 = get(R.id.order_detail_img1);
        order_detail_img2 = get(R.id.order_detail_img2);
        order_detail_img3 = get(R.id.order_detail_img3);
        order_detail_type_des = get(R.id.order_detail_type_des);
        order_detail_status = get(R.id.order_detail_status);
        order_cancel = get(R.id.order_cancel);
        order_apply = get(R.id.order_apply);
        order_id = getActivity().getIntent().getStringExtra(KEY_ORDER_ID);
        //获取当前的类型
        user_type = getActivity().getIntent().getIntExtra(ORDER_USER_TYPE, 1);
        requestOrderDetail();

        order_detail_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                getActivity().startActivity(intent);
            }
        });

        order_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //买入
                if (user_type == 1) {
                    //申请资金方, 申请垫资, 去资金方列表去
                    ARouter.getInstance().build(ARouterPath.FUND_SIDE)
                            .navigation(getActivity(), KEY_ORDER_FUNDER);
                }

                if (user_type == 3) {
                    //资金方确认
                    order_apply.setEnabled(false);
                    requestMoneyConfirm();
                }
            }
        });

        order_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //买入
                order_cancel.setEnabled(false);
                //取消订单
                requestCancelOrder(user_type);

            }
        });
    }

    /**
     * 资金方确认
     */
    private void requestMoneyConfirm() {
        OrderMoneyRequestBean requestBean = new OrderMoneyRequestBean();
        requestBean.setId(order_id);
        EasyHttp.post(HttpConstant.API_MONEY_CONFIRM).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        order_apply.setEnabled(true);
                        Snackbar.make(getRootView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String s) {
                        order_apply.setEnabled(true);
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
        requestBean.setOrderSource(user_type);
        EasyHttp.post(HttpConstant.API_CANCEL_ORDER).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        order_cancel.setEnabled(true);
                    }

                    @Override
                    public void onSuccess(String s) {
                        order_cancel.setEnabled(true);
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
                            order_detail_title.setText(jsonBean.getData().getTitle());
                            order_detail_description.setText("商品描述: " + jsonBean.getData().getDescription());
                            order_detail_price.setText("价格: ￥" + jsonBean.getData().getPrice());
                            order_detail_name.setText("姓名:" + jsonBean.getData().getPerson());
                            order_detail_phone.setText("电话: " + jsonBean.getData().getPhone());
                            order_detail_type_des.setText(jsonBean.getData().getOrderTypeDes());
                            order_detail_status.setText(jsonBean.getData().getOrderStatusDes());
                            if (!jsonBean.getData().getPic1Url().equals("")) {
                                order_detail_img1.setVisibility(View.VISIBLE);
                                Glide.with(getActivity())
                                        .load(jsonBean.getData().getPic1Url())
                                        .placeholder(R.drawable.ic_place_holder)
                                        .centerCrop()
                                        .error(R.drawable.ic_place_holder).into(order_detail_img1);
                            }

                            if (!jsonBean.getData().getPic2Url().equals("")) {
                                order_detail_img2.setVisibility(View.VISIBLE);
                                Glide.with(getActivity())
                                        .load(jsonBean.getData().getPic2Url())
                                        .placeholder(R.drawable.ic_place_holder)
                                        .centerCrop()
                                        .error(R.drawable.ic_place_holder).into(order_detail_img2);
                            }

                            if (!jsonBean.getData().getPic3Url().equals("")) {
                                order_detail_img3.setVisibility(View.VISIBLE);
                                Glide.with(getActivity())
                                        .load(jsonBean.getData().getPic3Url())
                                        .placeholder(R.drawable.ic_place_holder)
                                        .centerCrop()
                                        .error(R.drawable.ic_place_holder).into(order_detail_img3);
                            }

                            if (user_type == 1) {
                                //买入的情况
                                order_detail_phone.setEnabled(false);
                                if (jsonBean.getData().getOrderStatus() == 0) {
                                    //取消和申请资金方
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.VISIBLE);
                                    order_cancel.setText("取消");
                                    order_apply.setText("申请资金");
                                } else if (jsonBean.getData().getOrderStatus() == -10) {
                                    order_apply.setVisibility(View.GONE);
                                    order_cancel.setVisibility(View.GONE);
                                } else if (jsonBean.getData().getOrderStatus() == 10) {
                                    order_cancel.setVisibility(View.GONE);
                                    order_apply.setVisibility(View.GONE);
                                } else if (jsonBean.getData().getOrderStatus() == 20) {
                                    order_cancel.setVisibility(View.GONE);
                                    order_apply.setVisibility(View.GONE);
                                }
                            } else if (user_type == 2) {
                                //卖的情况, 显示取消
                                order_detail_phone.setEnabled(true);
                                phoneNumber = jsonBean.getData().getPhone();
                                if (jsonBean.getData().getOrderStatus() == 0) {
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.GONE);
                                    order_cancel.setText("取消订单");
                                } else {
                                    order_cancel.setVisibility(View.GONE);
                                    order_apply.setVisibility(View.GONE);
                                }

                            } else if (user_type == 3) {
                                //资金方
                                order_detail_phone.setEnabled(false);
                                if (jsonBean.getData().getOrderStatus() == 10) {
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.VISIBLE);
                                    order_cancel.setText("取消");
                                    order_apply.setText("确认");
                                } else {
                                    order_cancel.setVisibility(View.GONE);
                                    order_apply.setVisibility(View.GONE);
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
