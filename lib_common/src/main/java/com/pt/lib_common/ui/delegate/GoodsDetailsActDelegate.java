package com.pt.lib_common.ui.delegate;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.pt.lib_common.bean.jsonbean.GoodsDetatilJsonBean;
import com.pt.lib_common.bean.jsonbean.GoodsOffShelfJsonBean;
import com.pt.lib_common.bean.requestBean.GoodsDetailRequestBean;
import com.pt.lib_common.bean.requestBean.GoodsOffShelfRequestBean;
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
    private boolean isOwen;
    private int currentStatus;

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
        order_delete_or_transaction = get(R.id.order_delete_or_transaction);

        rcv_order_detail.setLayoutManager(new GridLayoutManager(this.getActivity(), 1,
                GridLayoutManager.VERTICAL, false));
        goodsDetailAdapter = new GoodsDetailAdapter(getActivity(), detailList);
        rcv_order_detail.setAdapter(goodsDetailAdapter);
        rcv_order_detail.setItemAnimator(new DefaultItemAnimator());
        srl_order_detail.setEnablePureScrollMode(true);
        requestList();

        if (order_off_shelf.isEnabled()) {
            //上架:
            order_off_shelf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOwen && currentStatus == 1) {
                        //上架状态显示下架
                        GoodsOffShelfRequestBean requestBean = new GoodsOffShelfRequestBean();
                        requestBean.setId(goods_id);
                        EasyHttp.post(HttpConstant.API_OFF_SHELF).headers("Content-Type", "application/json")
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
                    } else if (isOwen && currentStatus == 1) {
                        //下架状态显示上架
                        GoodsOffShelfRequestBean requestBean = new GoodsOffShelfRequestBean();
                        requestBean.setId(goods_id);
                        EasyHttp.post(HttpConstant.API_ON_SHELF).headers("Content-Type", "application/json")
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
                                            Snackbar.make(getRootView(), "上架成功", Snackbar.LENGTH_SHORT).show();
                                            getActivity().finish();
                                        }
                                    }
                                });
                    }
                }
            });
        }

        if (order_modified.isEnabled()) {
            order_modified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOwen) {
                        //修改订单
                        ARouter.getInstance().build(ARouterPath.GOODS_MODIFY)
                                .withString(Constant.KEY_GOODS_ID, goods_id).navigation();
                    }
                }
            });
        }

        if (order_delete_or_transaction.isEnabled()) {
            order_delete_or_transaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOwen) {
                        //删除订单
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
                    } else {
                        //订单交易, 不是自己的,
                    }
                }
            });
        }
    }

    /**
     * 请求详情数据
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
                        GoodsDetatilJsonBean detailBean = new Gson().fromJson(s, GoodsDetatilJsonBean.class);
                        if (detailBean.getData() != null) {
                            LogUtils.d("phone = " + SPHelper.getString("phone", "", true));
                            if (detailBean.getData().getCreateUserPhone().equals(
                                    SPHelper.getString("phone", "", true))) {
                                //为自己创建的商品
                                isOwen = true;
                                order_off_shelf.setVisibility(View.VISIBLE);
                                order_modified.setVisibility(View.VISIBLE);
                                order_delete_or_transaction.setVisibility(View.VISIBLE);
                                order_modified.setEnabled(true);
                                order_delete_or_transaction.setEnabled(true);
                                //查看当前自己订单的状态 取反状态
                                if (detailBean.getData().getGoodsStatus() == 0) {
                                    //当前为下架状态
                                    order_off_shelf.setEnabled(true);
                                    currentStatus = 0;
                                    order_off_shelf.setText("上架");
                                } else if (detailBean.getData().getGoodsStatus() == 1) {
                                    currentStatus = 1;
                                    order_off_shelf.setEnabled(true);
                                    order_off_shelf.setText("下架");
                                } else if (detailBean.getData().getGoodsStatus() == 3) {
                                    currentStatus = 3;
                                    order_off_shelf.setEnabled(false);
                                    order_off_shelf.setText("违规");
                                } else if (detailBean.getData().getGoodsStatus() == 4) {
                                    currentStatus = 4;
                                    order_off_shelf.setEnabled(false);
                                    order_off_shelf.setText("禁售");
                                }
                                order_delete_or_transaction.setText("删除");
                            } else {
                                isOwen = false;
                                order_off_shelf.setVisibility(View.INVISIBLE);
                                order_modified.setVisibility(View.INVISIBLE);
                                order_delete_or_transaction.setVisibility(View.VISIBLE);
                                order_off_shelf.setEnabled(false);
                                order_modified.setEnabled(false);
                                order_delete_or_transaction.setEnabled(true);
                                order_delete_or_transaction.setText("交易");
                            }

                            GoodsDetailDateBean titleItem = new GoodsDetailDateBean(GoodsDetailDateBean.KEY_GOODS_DETAIIL_TITLE);
                            titleItem.setTitle(detailBean.getData().getTitle());
                            titleItem.setDescription(detailBean.getData().getDescription());
                            titleItem.setGoodsStatusDes(detailBean.getData().getGoodsStatusDes());
                            titleItem.setCategory(detailBean.getData().getCateName1());
                            titleItem.setUpdateTime(detailBean.getData().getUpdateTime());
                            titleItem.setCreatePhone(detailBean.getData().getCreateUserPhone());
                            titleItem.setPrice(detailBean.getData().getPrice());
                            titleItem.setGoodsStatus(detailBean.getData().getGoodsStatus());
                            detailList.add(titleItem);
                            GoodsDetailDateBean picItem = new GoodsDetailDateBean(GoodsDetailDateBean.KEY_GOODS_DETAIL_BODY);
                            picItem.setPic1Url(detailBean.getData().getPic1Url());
                            picItem.setPic2Url(detailBean.getData().getPic3Url());
                            picItem.setPic3Url(detailBean.getData().getPic3Url());
                            detailList.add(picItem);
                            goodsDetailAdapter.notifyDataSetChanged();
                        } else {
                            //商品为null 显示EmptyView
                        }
                    }
                });
    }
}
