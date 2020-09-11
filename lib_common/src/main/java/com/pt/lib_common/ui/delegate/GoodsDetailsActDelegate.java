package com.pt.lib_common.ui.delegate;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.pt.lib_common.R;
import com.pt.lib_common.adapter.GoodsDetailAdapter;
import com.pt.lib_common.bean.databean.GoodsDetailDateBean;
import com.pt.lib_common.bean.jsonbean.GoodsDetatilJsonBean;
import com.pt.lib_common.bean.requestBean.GoodsDetailRequestBean;
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
    private ArrayList<GoodsDetailDateBean> detailListTemp = new ArrayList<>();
    private GoodsDetailAdapter goodsDetailAdapter;
    private String goods_id;
    private boolean isOwen;

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
                                order_delete_or_transaction.setText("删除");
                            } else {
                                isOwen = false;
                                order_off_shelf.setVisibility(View.INVISIBLE);
                                order_modified.setVisibility(View.INVISIBLE);
                                order_delete_or_transaction.setVisibility(View.VISIBLE);
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
