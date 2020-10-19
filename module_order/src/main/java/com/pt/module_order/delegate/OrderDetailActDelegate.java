package com.pt.module_order.delegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
import com.pt.module_order.adapter.OrderDetailLogAdapter;
import com.pt.module_order.bean.json.ApplyFunderJsonBean;
import com.pt.module_order.bean.json.OrderDetailJsonBean;
import com.pt.module_order.bean.rquest.ApplyFunderRequestBean;
import com.pt.module_order.bean.rquest.OrderDetailRequestBean;
import com.pt.module_order.bean.rquest.OrderMoneyRequestBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.converter.gson.GsonConverterFactory;

import static com.pt.lib_common.constants.Constant.CHOOSE_FUND_ITEM;
import static com.pt.lib_common.constants.Constant.KEY_ORDER_FUNDER;
import static com.pt.lib_common.constants.Constant.KEY_ORDER_FUNDER_RESULT;
import static com.pt.lib_common.constants.Constant.KEY_ORDER_ID;
import static com.pt.lib_common.constants.Constant.ORDER_USER_TYPE;

public class OrderDetailActDelegate extends AppDelegate {
    private String order_id;
    private int user_type;
    private String phoneNumber;

    private TextView order_detail_status;
    private TextView order_detail_title;
    private TextView order_detail_price;
    private TextView order_detail_type_des;
    private TextView order_detail_user_phone;
    private TextView order_detail_funder_phone;
    private TextView order_detail_money_apply_person_phone;
    private TextView order_detail_money_apply_time;
    private ImageView order_detail_content_image;
    private TextView order_detail_content_title;
    private TextView order_detail_content_description;
    private TextView order_detail_content_time;
    private TextView order_detail_content_price;
    //private TextView order_detail_log_info_content;
    private TextView order_apply;
    private TextView order_cancel;
    private LinearLayout ll_order_detail_log_info;
    private RecyclerView rcv_order_detail_log_info_content;
    private List<String> logList = new ArrayList<>();
    private OrderDetailLogAdapter adapter;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_detail_order;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        order_detail_status = get(R.id.order_detail_status);
        order_detail_title = get(R.id.order_detail_title);
        order_detail_price = get(R.id.order_detail_price);
        order_detail_type_des = get(R.id.order_detail_type_des);
        order_detail_user_phone = get(R.id.order_detail_user_phone);
        order_detail_funder_phone = get(R.id.order_detail_funder_phone);
        order_detail_money_apply_person_phone = get(R.id.order_detail_money_apply_person_phone);
        order_detail_money_apply_time = get(R.id.order_detail_money_apply_time);
        order_detail_content_image = get(R.id.order_detail_content_image);
        order_detail_content_title = get(R.id.order_detail_content_title);
        order_detail_content_description = get(R.id.order_detail_content_description);
        order_detail_content_time = get(R.id.order_detail_content_time);
        order_detail_content_price = get(R.id.order_detail_content_price);
        ll_order_detail_log_info = get(R.id.ll_order_detail_log_info);
        rcv_order_detail_log_info_content = get(R.id.rcv_order_detail_log_info_content);
        rcv_order_detail_log_info_content.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new OrderDetailLogAdapter(getActivity(), R.layout.order_detail_log_item, logList);
        rcv_order_detail_log_info_content.setAdapter(adapter);
        rcv_order_detail_log_info_content.setItemAnimator(new DefaultItemAnimator());
        order_apply = get(R.id.order_apply);
        order_cancel = get(R.id.order_cancel);
        order_id = getActivity().getIntent().getStringExtra(KEY_ORDER_ID);
        //获取当前的类型
        user_type = getActivity().getIntent().getIntExtra(ORDER_USER_TYPE, 1);
        requestOrderDetail();

        /*order_detail_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                getActivity().startActivity(intent);
            }
        });*/

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
                            if (jsonBean.getData().getOrderStatusDes() != null
                                    && !("".equals(jsonBean.getData().getOrderStatusDes()))) {
                                order_detail_status.setVisibility(View.VISIBLE);
                                order_detail_status.setText(jsonBean.getData().getOrderStatusDes());
                            } else {
                                order_detail_status.setVisibility(View.GONE);
                            }
                            //订单号
                            order_detail_title.setText("订单号: " + jsonBean.getData().getSn());
                            order_detail_price.setText("订单金额: ￥" + jsonBean.getData().getPrice());
                            order_detail_type_des.setText("订单类型: " + jsonBean.getData().getOrderTypeDes());

                            order_detail_user_phone.setText("联系人: " + jsonBean.getData().getPerson() + "  " + jsonBean.getData().getPhone());
                            if (jsonBean.getData().getFunderPhone() != null
                                    && !("".equals(jsonBean.getData().getFunderPhone()))) {
                                order_detail_funder_phone.setVisibility(View.VISIBLE);
                                order_detail_funder_phone.setText("资方电话: " + jsonBean.getData().getFunderPhone());
                            } else {
                                order_detail_funder_phone.setVisibility(View.GONE);
                            }

                            if (jsonBean.getData().getApplyFunderUserPhone() != null
                                    && !("".equals(jsonBean.getData().getApplyFunderUserPhone()))) {
                                order_detail_money_apply_person_phone.setVisibility(View.VISIBLE);
                                order_detail_money_apply_person_phone.setText("资金申请人: " + jsonBean.getData().getApplyFunderUserPhone());
                            } else {
                                order_detail_money_apply_person_phone.setVisibility(View.GONE);
                            }
                            if (jsonBean.getData().getApplyFunderDate() != null
                                    && !("".equals(jsonBean.getData().getApplyFunderDate()))) {
                                order_detail_money_apply_time.setVisibility(View.VISIBLE);
                                order_detail_money_apply_time.setText("资金申请时间: " + jsonBean.getData().getApplyFunderDate());
                            } else {
                                order_detail_money_apply_time.setVisibility(View.GONE);
                            }
                            Glide.with(getActivity())
                                    .load(jsonBean.getData().getPic1Url())
                                    .placeholder(R.drawable.default_error)
                                    .error(R.drawable.default_error)
                                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(4)))
                                    .into(order_detail_content_image);
                            order_detail_content_title.setText(jsonBean.getData().getTitle());
                            order_detail_content_description.setText(jsonBean.getData().getDescription());
                            order_detail_content_time.setText(jsonBean.getData().getCreateTime());
                            order_detail_content_price.setText("￥" + jsonBean.getData().getPrice());
                            if (jsonBean.getData().getOrderLogList() != null && jsonBean.getData().getOrderLogList().size() > 0) {
                                ll_order_detail_log_info.setVisibility(View.VISIBLE);
                                logList.clear();
                                for (String log : jsonBean.getData().getOrderLogList()) {
                                    logList.add(log);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                ll_order_detail_log_info.setVisibility(View.GONE);
                            }

                            if (user_type == 1) {
                                //买入的情况
                                if (jsonBean.getData().getOrderStatus() == 0) {
                                    //取消和申请资金方
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.VISIBLE);
                                    order_cancel.setText("取消订单");
                                    order_apply.setText("申请资金");
                                } else if (jsonBean.getData().getOrderStatus() == -10) {
                                    order_apply.setVisibility(View.GONE);
                                    order_cancel.setVisibility(View.GONE);
                                } else if (jsonBean.getData().getOrderStatus() == 10) {
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_cancel.setText("取消订单");
                                    order_apply.setVisibility(View.GONE);
                                } else if (jsonBean.getData().getOrderStatus() == 20) {
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.GONE);
                                    order_cancel.setText("取消订单");
                                }
                            } else if (user_type == 2) {
                                //卖的情况, 显示取消
                                phoneNumber = jsonBean.getData().getPhone();
                                if (jsonBean.getData().getOrderStatus() == 0) {
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.GONE);
                                    order_cancel.setText("取消订单");
                                } else if (jsonBean.getData().getOrderStatus() == -10) {
                                    order_cancel.setVisibility(View.GONE);
                                    order_apply.setVisibility(View.GONE);
                                } else if (jsonBean.getData().getOrderStatus() == 10) {
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.GONE);
                                    order_cancel.setText("取消订单");
                                } else if (jsonBean.getData().getOrderStatus() == 20) {
                                    //资金方已经同意,卖出的情况依然可以取消订单
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.GONE);
                                    order_cancel.setText("取消订单");
                                }

                            } else if (user_type == 3) {
                                //资金方
                                if (jsonBean.getData().getOrderStatus() == 10) {
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.VISIBLE);
                                    order_cancel.setText("取消订单");
                                    order_apply.setText("同意申请");
                                } else if (jsonBean.getData().getOrderStatus() == -10){
                                    order_cancel.setVisibility(View.GONE);
                                    order_apply.setVisibility(View.GONE);
                                } else if (jsonBean.getData().getOrderStatus() == 20) {
                                    //资金方已经确认
                                    order_cancel.setVisibility(View.VISIBLE);
                                    order_apply.setVisibility(View.GONE);
                                    order_cancel.setText("取消订单");
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
