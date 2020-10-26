package com.pt.lib_common.ui.delegate;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.pt.lib_common.bean.databean.ModifyInfoDataBean;
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
import com.pt.lib_common.util.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xw.repo.XEditText;

import java.util.ArrayList;

import retrofit2.converter.gson.GsonConverterFactory;

public class GoodsDetailsActDelegate extends AppDelegate {

    private RecyclerView rcv_order_detail;
    private SmartRefreshLayout srl_order_detail;
    private TextView order_off_shelf;
    private TextView order_modified;
    private TextView order_delete_or_transaction;
    private ArrayList<GoodsDetailDateBean> detailList = new ArrayList<>();
    private ModifyInfoDataBean infoData = new ModifyInfoDataBean();
    private GoodsDetailAdapter goodsDetailAdapter;
    private String goods_id;
    private int goodsType;
    private String phone;
    private String name;
    //private TextView order_apply_money;
    private int goodsStatus;
    private TextView order_exchange;
    private XEditText input_name;
    private XEditText input_phone;
    private ConstraintLayout loading_coo;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        loading_coo = get(R.id.loading_coo);
        loading_coo.setVisibility(View.VISIBLE);
        goods_id = getActivity().getIntent().getStringExtra(Constant.KEY_GOODS_ID);
        srl_order_detail = get(R.id.srl_order_detail);
        rcv_order_detail = get(R.id.rcv_order_detail);
        order_off_shelf = get(R.id.order_off_shelf);
        order_modified = get(R.id.order_modified);
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
                order_off_shelf.setEnabled(false);
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
                order_exchange.setEnabled(false);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY_MODIFY_INFO, infoData);
                ARouter.getInstance().build(ARouterPath.GOODS_MODIFY)
                        .withBundle(Constant.KEY_MODIFY_INFO_SERIALIZABLE, bundle)
                        .withString(Constant.KEY_GOODS_ID, goods_id).navigation(getActivity(),
                        Constant.KEY_MODIFY_DETAIL_REQUEST);
                order_modified.setEnabled(true);
                getActivity().finish();
            }
        });

        //资金抵押
        /*order_apply_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.FUND_SIDE)
                        .navigation(getActivity(), KEY_ORDER_FUNDER_SALER);
            }
        });*/

        order_delete_or_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        order_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_exchange.setEnabled(false);
                showDialog();
            }
        });
    }

    private void showDeleteDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dilog_delete, null);
        final Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(true)
                .create();
        dialog.show();
        TextView cancel = view.findViewById(R.id.delete_cancel);
        TextView confirm = view.findViewById(R.id.delete_confirm);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGoods();
                dialog.dismiss();
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
                        order_delete_or_transaction.setEnabled(true);
                    }

                    @Override
                    public void onSuccess(String s) {
                        order_delete_or_transaction.setEnabled(true);
                        GoodsOffShelfJsonBean jsonBean = new Gson().fromJson(s, GoodsOffShelfJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            //Snackbar.make(getRootView(), "删除成功", Snackbar.LENGTH_SHORT).show();
                            getActivity().setResult(getActivity().RESULT_OK);
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
                        Snackbar.make(getRootView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        order_off_shelf.setEnabled(true);
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
                        order_off_shelf.setEnabled(true);
                    }

                    @Override
                    public void onSuccess(String s) {
                        //order_off_shelf.setEnabled(true);
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

    /*不需要传person & phone了
    private void showSaleDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dilog_input_user_phone, null);
        final Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(true)
                .create();
        dialog.show();
        final XEditText input_name = (XEditText) view.findViewById(R.id.input_name);
        final XEditText input_phone = (XEditText) view.findViewById(R.id.input_phone);
        input_phone.setPattern(new int[]{3, 4, 4}, " ");
        input_phone.setInputType(InputType.TYPE_CLASS_PHONE);
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
                    //去申请资金资金
                    //选择资金方, 申请资金方垫支
                    ARouter.getInstance().build(ARouterPath.FUND_SIDE)
                            .navigation(getActivity(), KEY_ORDER_FUNDER_SALER);
                    phone_input = input_phone.getText().toString();
                    name_input = input_name.getText().toString();
                    dialog.dismiss();
                } else {
                    Snackbar.make(getRootView(), "请先输入姓名和电话号码", Snackbar.LENGTH_SHORT).show();
                }
            }
        });}*/


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
        input_name = (XEditText) view.findViewById(R.id.input_name);
        input_phone = (XEditText) view.findViewById(R.id.input_phone);
        input_phone.setPattern(new int[]{3, 4, 4}, " ");
        input_phone.setInputType(InputType.TYPE_CLASS_PHONE);
        input_name.setText(name);
        input_phone.setText(phone);
        TextView cancel = view.findViewById(R.id.cancel_input);
        TextView confirm = view.findViewById(R.id.confirm_input);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                order_exchange.setEnabled(true);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input_name.getText().toString() != "" && input_phone.getText().toString() != "") {
                    if (!Utils.isMobile(input_phone.getText().toString().replace(" ", ""))) {
                        Utils.closeSoftInput(getActivity());
                        Snackbar.make(srl_order_detail, "手机号码格式错误, 请重新输入", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    requestExchangeInternet(goodsType, input_name.getText().toString(),
                            input_phone.getText().toString().replace(" ", ""));
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
                            order_exchange.setEnabled(true);
                            Snackbar.make(getRootView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(String s) {
                            order_exchange.setEnabled(true);
                            CreateOrderJson order = new Gson().fromJson(s, CreateOrderJson.class);
                            if (order.getCode() == 0) {
                                ARouter.getInstance().build(ARouterPath.ORDER_DETAIL)
                                        .withString(Constant.KEY_ORDER_ID, order.getData())
                                        .withInt(Constant.ORDER_USER_TYPE, 2)
                                        .navigation();
                                getActivity().finish();
                            } else if (order.getCode() == 401) {
                                Snackbar.make(getRootView(), "登录已经过期, 请重新登录", Snackbar.LENGTH_SHORT).show();
                                SPHelper.putString("token", "", true);
                                SPHelper.putString("phone", "", true);
                                ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                                getActivity().finish();
                            } else {
                                Snackbar.make(getRootView(), "交易失败", Snackbar.LENGTH_SHORT).show();
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
                            order_exchange.setEnabled(true);
                            Snackbar.make(getRootView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(String s) {
                            order_exchange.setEnabled(true);
                            CreateOrderJson order = new Gson().fromJson(s, CreateOrderJson.class);
                            if (order.getCode() == 0) {
                                ARouter.getInstance().build(ARouterPath.ORDER_DETAIL)
                                        .withString(Constant.KEY_ORDER_ID, order.getData())
                                        .withInt(Constant.ORDER_USER_TYPE, 1)
                                        .navigation();
                                getActivity().finish();
                            } else if (order.getCode() == 401) {
                                Snackbar.make(getRootView(), "登录已经过期, 请重新登录", Snackbar.LENGTH_SHORT).show();
                                SPHelper.putString("token", "", true);
                                SPHelper.putString("phone", "", true);
                                ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                                getActivity().finish();
                            } else {
                                Snackbar.make(getRootView(), "交易失败", Snackbar.LENGTH_SHORT).show();
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
                        Snackbar.make(getRootView(), "获取数据失败,请稍后重试...", Snackbar.LENGTH_SHORT).show();
                        loading_coo.setVisibility(View.GONE);
                    }

                    @Override
                    public void onSuccess(String s) {
                        loading_coo.setVisibility(View.GONE);
                        GoodsDetatilJsonBean detailJsonBean = new Gson().fromJson(s, GoodsDetatilJsonBean.class);
                        if (detailJsonBean.getCode() == 0) {
                            if (detailJsonBean.getData() != null) {
                                goodsType = detailJsonBean.getData().getGoodsType();
                                String phoneText = SPHelper.getString("phone", "", true);
                                LogUtils.d("current user phone = " + phoneText);
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
                                            //order_apply_money.setVisibility(View.GONE);
                                            order_delete_or_transaction.setVisibility(View.VISIBLE);
                                            order_delete_or_transaction.setText("删除");
                                            order_exchange.setVisibility(View.GONE);
                                        } else if (goodsStatus == 1) {
                                            order_off_shelf.setVisibility(View.VISIBLE);
                                            order_off_shelf.setText("下架");
                                            order_modified.setVisibility(View.VISIBLE);
                                            order_modified.setText("修改");
                                            //order_apply_money.setVisibility(View.GONE);
                                            order_delete_or_transaction.setVisibility(View.VISIBLE);
                                            order_delete_or_transaction.setText("删除");
                                            order_exchange.setVisibility(View.GONE);
                                        } else if (goodsStatus == 3) {
                                            order_off_shelf.setVisibility(View.GONE);
                                            order_modified.setVisibility(View.VISIBLE);
                                            order_modified.setText("修改");
                                            //order_apply_money.setVisibility(View.GONE);
                                            order_delete_or_transaction.setVisibility(View.VISIBLE);
                                            order_delete_or_transaction.setText("删除");
                                            order_exchange.setVisibility(View.GONE);
                                        }
                                    } else {
                                        order_off_shelf.setVisibility(View.GONE);
                                        order_modified.setVisibility(View.GONE);
                                        //order_apply_money.setVisibility(View.GONE);
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
                                            //order_apply_money.setVisibility(View.VISIBLE);
                                            //order_apply_money.setText("资金");
                                            order_delete_or_transaction.setVisibility(View.VISIBLE);
                                            order_delete_or_transaction.setText("删除");
                                            order_exchange.setVisibility(View.GONE);
                                        } else if (goodsStatus == 1) {
                                            //显示下架, 修改, 资金, 删除
                                            order_off_shelf.setVisibility(View.VISIBLE);
                                            order_off_shelf.setText("下架");
                                            order_modified.setVisibility(View.VISIBLE);
                                            order_modified.setText("修改");
                                            //order_apply_money.setVisibility(View.VISIBLE);
                                            //order_apply_money.setText("资金");
                                            order_delete_or_transaction.setVisibility(View.VISIBLE);
                                            order_delete_or_transaction.setText("删除");
                                            order_exchange.setVisibility(View.GONE);
                                        } else if (goodsStatus == 3) {
                                            order_off_shelf.setVisibility(View.GONE);
                                            order_modified.setVisibility(View.VISIBLE);
                                            order_modified.setText("修改");
                                            //order_apply_money.setVisibility(View.GONE);
                                            order_delete_or_transaction.setVisibility(View.VISIBLE);
                                            order_delete_or_transaction.setText("删除");
                                            order_exchange.setVisibility(View.GONE);
                                        }
                                    } else {
                                        order_off_shelf.setVisibility(View.GONE);
                                        order_modified.setVisibility(View.GONE);
                                        //order_apply_money.setVisibility(View.GONE);
                                        order_delete_or_transaction.setVisibility(View.GONE);
                                        order_exchange.setVisibility(View.VISIBLE);
                                        order_exchange.setText("交易");
                                    }
                                }
                                infoData.setUserType(goodsType);
                                GoodsDetailDateBean titleItem = new GoodsDetailDateBean(GoodsDetailDateBean.KEY_GOODS_DETAIIL_TITLE);
                                titleItem.setTitle(detailJsonBean.getData().getTitle());
                                infoData.setTitle(detailJsonBean.getData().getTitle());
                                titleItem.setDescription(detailJsonBean.getData().getDescription());
                                infoData.setDescription(detailJsonBean.getData().getDescription());
                                titleItem.setGoodsStatusDes(detailJsonBean.getData().getGoodsStatusDes());
                                titleItem.setCategory(detailJsonBean.getData().getCateName1());
                                infoData.setCategory(detailJsonBean.getData().getCateName1());
                                infoData.setCateId(detailJsonBean.getData().getCateId1());
                                titleItem.setCreateTime(detailJsonBean.getData().getCreateTime());
                                //titleItem.setCreatePhone(detailJsonBean.getData().getCreateUserPhone());
                                titleItem.setPrice(detailJsonBean.getData().getPrice());
                                infoData.setPrice(detailJsonBean.getData().getPrice());
                                //titleItem.setGoodsStatus(detailJsonBean.getData().getGoodsStatus());
                                detailList.add(titleItem);
                                GoodsDetailDateBean picItem = new GoodsDetailDateBean(GoodsDetailDateBean.KEY_GOODS_DETAIL_BODY);
                                picItem.setPic1Url(detailJsonBean.getData().getPic1Url());
                                infoData.setPic1Url(detailJsonBean.getData().getPic1Url());
                                picItem.setPic2Url(detailJsonBean.getData().getPic2Url());
                                infoData.setPic2Url(detailJsonBean.getData().getPic2Url());
                                picItem.setPic3Url(detailJsonBean.getData().getPic3Url());
                                infoData.setPic3Url(detailJsonBean.getData().getPic3Url());
                                infoData.setLocation(detailJsonBean.getData().getCityCode());
                                detailList.add(picItem);
                                goodsDetailAdapter.notifyDataSetChanged();
                            } else {
                                //显示EmptyView
                            }
                        } else if (detailJsonBean.getCode() == 401) {
                            Snackbar.make(getRootView(), "登录已经过期, 请重新登录", Snackbar.LENGTH_SHORT).show();
                            SPHelper.putString("token", "", true);
                            SPHelper.putString("phone", "", true);
                            ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                            getActivity().finish();
                        } else {
                            Snackbar.make(getRootView(), "获取数据失败", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.KEY_MODIFY_DETAIL_REQUEST && resultCode == getActivity().RESULT_OK) {
            detailList.clear();
            requestList();
        }
        /*if (requestCode == KEY_ORDER_FUNDER_SALER && resultCode == KEY_ORDER_FUNDER_RESULT) {
            Bundle bundle = data.getBundleExtra(Constant.KEY_CHOOSE_FUND);
            FundSideItem item = (FundSideItem) bundle.getSerializable(CHOOSE_FUND_ITEM);
            ApplyFunderSalerRequestBean requestBean = new ApplyFunderSalerRequestBean();
            requestBean.setFunderPhone(item.getPhone());
            requestBean.setGid(goods_id);

            EasyHttp.post(HttpConstant.API_APPLY_SALE_FUNDER).headers("Content-Type", "application/json")
                    .addConverterFactory(GsonConverterFactory.create())
                    .upObject(requestBean)
                    .execute(new SimpleCallBack<String>() {
                        @Override
                        public void onError(ApiException e) {

                        }

                        @Override
                        public void onSuccess(String s) {
                            ApplyFunderSaleJsonBean jsonBean = new Gson().fromJson(s, ApplyFunderSaleJsonBean.class);
                            if (jsonBean.getCode() == 0) {
                                Snackbar.make(getRootView(), "正在申请资金方垫资", Snackbar.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        }
                    });
        }*/

    }
}