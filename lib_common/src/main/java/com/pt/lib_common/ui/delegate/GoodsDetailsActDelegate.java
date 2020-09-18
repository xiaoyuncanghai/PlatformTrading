package com.pt.lib_common.ui.delegate;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.R;
import com.pt.lib_common.adapter.GoodsDetailAdapter;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.bean.databean.GoodsDetailDateBean;
import com.pt.lib_common.bean.jsonbean.CreateOrderJson;
import com.pt.lib_common.bean.jsonbean.GoodsDetatilJsonBean;
import com.pt.lib_common.bean.jsonbean.GoodsOffShelfJsonBean;
import com.pt.lib_common.bean.jsonbean.PhoneRequestJsonBean;
import com.pt.lib_common.bean.requestBean.ExchangeRequestBean;
import com.pt.lib_common.bean.requestBean.GoodsDetailRequestBean;
import com.pt.lib_common.bean.requestBean.GoodsOffShelfRequestBean;
import com.pt.lib_common.bean.requestBean.PhoneRequestBean;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.SPHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import retrofit2.converter.gson.GsonConverterFactory;

public class GoodsDetailsActDelegate extends AppDelegate {

    private RecyclerView rcv_order_detail;
    private SmartRefreshLayout srl_order_detail;
    private TextView order_off_shelf;
    private TextView order_modified;
    private TextView order_delete_or_transaction;
    private ArrayList<GoodsDetailDateBean> detailList = new ArrayList<>();
    private GoodsDetailAdapter goodsDetailAdapter;
    private String goods_id;
    private int goodsType;
    private String phone;
    private String name;
    private TextView order_apply_money;
    private int goodsStatus;
    private TextView order_exchange;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        goods_id = getActivity().getIntent().getStringExtra(Constant.KEY_GOODS_ID);
        srl_order_detail = get(R.id.srl_order_detail);
        rcv_order_detail = get(R.id.rcv_order_detail);
        order_off_shelf = get(R.id.order_off_shelf);
        order_modified = get(R.id.order_modified);
        order_apply_money = get(R.id.order_apply_money);
        order_delete_or_transaction = get(R.id.order_delete_or_transaction);
        order_exchange = get(R.id.order_exchange);
        rcv_order_detail.setLayoutManager(new GridLayoutManager(this.getActivity(), 1,
                GridLayoutManager.VERTICAL, false));
        goodsDetailAdapter = new GoodsDetailAdapter(getActivity(), detailList);
        rcv_order_detail.setAdapter(goodsDetailAdapter);
        rcv_order_detail.setItemAnimator(new DefaultItemAnimator());
        srl_order_detail.setEnablePureScrollMode(true);
        requestList();
        requestUserPhoneInfo();

        // 上架和下架
        order_off_shelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goodsStatus == 0) {
                    //执行上架操作
                    requestOnShelf();
                } else if (goodsStatus == 1) {
                    requestOffShelf();
                }
            }
        });

        order_modified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改订单
                ARouter.getInstance().build(ARouterPath.GOODS_MODIFY)
                        .withString(Constant.KEY_GOODS_ID, goods_id).navigation();
            }
        });

        //资金抵押
        order_apply_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        order_delete_or_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGoods();
            }
        });

        order_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    /**
     * 删除商品
     */
    private void deleteGoods() {
        GoodsOffShelfRequestBean requestBean = new GoodsOffShelfRequestBean();
        requestBean.setId(goods_id);
        EasyHttp.post(HttpConstant.API_DELETE_GOODS).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        GoodsOffShelfJsonBean jsonBean = new Gson().fromJson(s, GoodsOffShelfJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            Snackbar.make(getRootView(), "删除成功", Snackbar.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }
                });
    }

    /**
     * 下架操作
     */
    private void requestOffShelf() {
        GoodsOffShelfRequestBean requestBean = new GoodsOffShelfRequestBean();
        requestBean.setId(goods_id);
        EasyHttp.post(HttpConstant.API_OFF_SHELF).headers("Content-Type", "application/json")
                .headers("Authorization", SPHelper.getString("token", "", true))
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        GoodsOffShelfJsonBean jsonBean = new Gson().fromJson(s, GoodsOffShelfJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            Snackbar.make(getRootView(), "下架成功", Snackbar.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }
                });
    }

    /**
     * 上架操作
     */
    private void requestOnShelf() {
        GoodsOffShelfRequestBean requestBean = new GoodsOffShelfRequestBean();
        requestBean.setId(goods_id);
        EasyHttp.post(HttpConstant.API_ON_SHELF).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .headers("Authorization", SPHelper.getString("token", "", true))
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        GoodsOffShelfJsonBean jsonBean = new Gson().fromJson(s, GoodsOffShelfJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            Snackbar.make(getRootView(), "上架成功", Snackbar.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                    }
                });
    }

    /**
     * 请求上次填写的姓名和电话
     */
    private void requestUserPhoneInfo() {
        PhoneRequestBean requestBean = new PhoneRequestBean();
        requestBean.setGoodsType(goodsType);
        EasyHttp.post(HttpConstant.API_PHONE_USER).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        PhoneRequestJsonBean jsonBean = new Gson().fromJson(s, PhoneRequestJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            name = jsonBean.getData().getPerson();
                            phone = jsonBean.getData().getPhone();
                        }
                    }
                });
    }

    /**
     * 弹出对话框输入求购者的姓名和电话
     */
    private void showDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dilog_input_user_phone, null);
        final Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(true)
                .create();
        dialog.show();
        final EditText input_name = (EditText) view.findViewById(R.id.input_name);
        final EditText input_phone = (EditText) view.findViewById(R.id.input_phone);
        input_name.setText(name);
        input_phone.setText(phone);
        TextView cancel = view.findViewById(R.id.cancel_input);
        TextView confirm = view.findViewById(R.id.confirm_input);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_name.getText().toString() != "" && input_phone.getText().toString() != "") {
                    requestExchangeInternet(goodsType, input_name.getText().toString(),
                            input_phone.getText().toString());
                    dialog.dismiss();
                } else {
                    Snackbar.make(getRootView(), "请先输入姓名和电话号码", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestExchangeInternet(final int goodsType, String name, String phone) {
        ExchangeRequestBean requestBean = new ExchangeRequestBean();
        requestBean.setGid(goods_id);
        requestBean.setPerson(name);
        requestBean.setPhone(phone);
        if (goodsType == 1) {
            //交易求购
            EasyHttp.post(HttpConstant.API_SEEK_REQUEST).headers("Content-Type", "application/json")
                    .addConverterFactory(GsonConverterFactory.create())
                    .upObject(requestBean)
                    .execute(new SimpleCallBack<String>() {
                        @Override
                        public void onError(ApiException e) {

                        }

                        @Override
                        public void onSuccess(String s) {
                            CreateOrderJson order = new Gson().fromJson(s, CreateOrderJson.class);
                            if (order.getCode() == 0) {
                                ARouter.getInstance().build(ARouterPath.ORDER_DETAIL)
                                        .withString(Constant.KEY_ORDER_ID, order.getData())
                                        .withInt(Constant.ORDER_USER_TYPE, 1)
                                        .navigation();
                            }
                        }
                    });

        } else if (goodsType == 2) {
            EasyHttp.post(HttpConstant.API_SALE_REQUEST).headers("Content-Type", "application/json")
                    .addConverterFactory(GsonConverterFactory.create())
                    .upObject(requestBean)
                    .execute(new SimpleCallBack<String>() {
                        @Override
                        public void onError(ApiException e) {

                        }

                        @Override
                        public void onSuccess(String s) {
                            CreateOrderJson order = new Gson().fromJson(s, CreateOrderJson.class);
                            if (order.getCode() == 0) {
                                ARouter.getInstance().build(ARouterPath.ORDER_DETAIL)
                                        .withString(Constant.KEY_ORDER_ID, order.getData())
                                        .withInt(Constant.ORDER_USER_TYPE, 2)
                                        .navigation();
                            }
                        }
                    });

        }
    }

    /**
     * 请求商品详情数据
     */
    private void requestList() {
        GoodsDetailRequestBean requestBean = new GoodsDetailRequestBean();
        requestBean.setId(goods_id);
        EasyHttp.post(HttpConstant.API_GOODS_DETAIL).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        GoodsDetatilJsonBean detailJsonBean = new Gson().fromJson(s, GoodsDetatilJsonBean.class);
                        if (detailJsonBean.getData() != null) {
                            goodsType = detailJsonBean.getData().getGoodsType();
                            String phoneText = SPHelper.getString("phone", "", true);
                            LogUtils.d("current user phone = "+ phoneText);
                            goodsStatus = detailJsonBean.getData().getGoodsStatus();
                            if (goodsType == 1) {
                                //自己布求购商品, 上架  修改  删除
                                //别人发布的值存在交易
                                //order_exchange.setText("交易");
                                if (detailJsonBean.getData().getCreateUserPhone().equals(phoneText)) {
                                    if (goodsStatus == 0) {
                                        order_off_shelf.setVisibility(View.VISIBLE);
                                        order_off_shelf.setText("上架");
                                        order_modified.setVisibility(View.VISIBLE);
                                        order_modified.setText("修改");
                                        order_apply_money.setVisibility(View.GONE);
                                        order_delete_or_transaction.setVisibility(View.VISIBLE);
                                        order_delete_or_transaction.setText("删除");
                                        order_exchange.setVisibility(View.GONE);
                                    } else if (goodsStatus == 1) {
                                        order_off_shelf.setVisibility(View.VISIBLE);
                                        order_off_shelf.setText("下架");
                                        order_modified.setVisibility(View.VISIBLE);
                                        order_modified.setText("修改");
                                        order_apply_money.setVisibility(View.GONE);
                                        order_delete_or_transaction.setVisibility(View.VISIBLE);
                                        order_delete_or_transaction.setText("删除");
                                        order_exchange.setVisibility(View.GONE);
                                    } else if (goodsStatus == 3) {
                                        order_off_shelf.setVisibility(View.GONE);
                                        order_modified.setVisibility(View.VISIBLE);
                                        order_modified.setText("修改");
                                        order_apply_money.setVisibility(View.GONE);
                                        order_delete_or_transaction.setVisibility(View.VISIBLE);
                                        order_delete_or_transaction.setText("删除");
                                        order_exchange.setVisibility(View.GONE);
                                    }
                                } else {
                                    order_off_shelf.setVisibility(View.GONE);
                                    order_modified.setVisibility(View.GONE);
                                    order_apply_money.setVisibility(View.GONE);
                                    order_delete_or_transaction.setVisibility(View.GONE);
                                    order_exchange.setVisibility(View.VISIBLE);
                                    order_exchange.setText("交易");
                                }
                            } else if (goodsType == 2) {
                                //售卖商品,可以直接抵押
                                //自己售卖还是别人售卖
                                if (detailJsonBean.getData().getCreateUserPhone().equals(phoneText)) {
                                    //是自己售卖
                                    if (goodsStatus == 0) {
                                        //显示上架, 修改, 资金, 删除
                                        order_off_shelf.setVisibility(View.VISIBLE);
                                        order_off_shelf.setText("上架");
                                        order_modified.setVisibility(View.VISIBLE);
                                        order_modified.setText("修改");
                                        order_apply_money.setVisibility(View.VISIBLE);
                                        order_apply_money.setText("资金");
                                        order_delete_or_transaction.setVisibility(View.VISIBLE);
                                        order_delete_or_transaction.setText("删除");
                                        order_exchange.setVisibility(View.GONE);
                                    } else if (goodsStatus == 1) {
                                        //显示下架, 修改, 资金, 删除
                                        order_off_shelf.setVisibility(View.VISIBLE);
                                        order_off_shelf.setText("下架");
                                        order_modified.setVisibility(View.VISIBLE);
                                        order_modified.setText("修改");
                                        order_apply_money.setVisibility(View.VISIBLE);
                                        order_apply_money.setText("资金");
                                        order_delete_or_transaction.setVisibility(View.VISIBLE);
                                        order_delete_or_transaction.setText("删除");
                                        order_exchange.setVisibility(View.GONE);
                                    } else if (goodsStatus == 3) {
                                        order_off_shelf.setVisibility(View.GONE);
                                        order_modified.setVisibility(View.VISIBLE);
                                        order_modified.setText("修改");
                                        order_apply_money.setVisibility(View.GONE);
                                        order_delete_or_transaction.setVisibility(View.VISIBLE);
                                        order_delete_or_transaction.setText("删除");
                                        order_exchange.setVisibility(View.GONE);
                                    }
                                } else {
                                    order_off_shelf.setVisibility(View.GONE);
                                    order_modified.setVisibility(View.GONE);
                                    order_apply_money.setVisibility(View.GONE);
                                    order_delete_or_transaction.setVisibility(View.GONE);
                                    order_exchange.setVisibility(View.VISIBLE);
                                    order_exchange.setText("交易");
                                }
                            }
                            GoodsDetailDateBean titleItem = new GoodsDetailDateBean(GoodsDetailDateBean.KEY_GOODS_DETAIIL_TITLE);
                            titleItem.setTitle(detailJsonBean.getData().getTitle());
                            titleItem.setDescription(detailJsonBean.getData().getDescription());
                            titleItem.setGoodsStatusDes(detailJsonBean.getData().getGoodsStatusDes());
                            titleItem.setCategory(detailJsonBean.getData().getCateName1());
                            titleItem.setUpdateTime(detailJsonBean.getData().getUpdateTime());
                            titleItem.setCreatePhone(detailJsonBean.getData().getCreateUserPhone());
                            titleItem.setPrice(detailJsonBean.getData().getPrice());
                            titleItem.setGoodsStatus(detailJsonBean.getData().getGoodsStatus());
                            detailList.add(titleItem);
                            GoodsDetailDateBean picItem = new GoodsDetailDateBean(GoodsDetailDateBean.KEY_GOODS_DETAIL_BODY);
                            picItem.setPic1Url(detailJsonBean.getData().getPic1Url());
                            picItem.setPic2Url(detailJsonBean.getData().getPic2Url());
                            picItem.setPic3Url(detailJsonBean.getData().getPic3Url());
                            detailList.add(picItem);
                            goodsDetailAdapter.notifyDataSetChanged();
                        } else {
                            //显示EmptyView
                        }
                    }
                });
    }
}
