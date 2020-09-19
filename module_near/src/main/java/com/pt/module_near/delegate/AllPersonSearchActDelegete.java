package com.pt.module_near.delegate;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.base.SearchJsonBean;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.SPHelper;
import com.pt.module_near.R;
import com.pt.module_near.adapter.NearAdapter;
import com.pt.module_near.adapter.NearRequestAdapter;
import com.pt.module_near.bean.data.NearItemDataBean;
import com.pt.module_near.bean.data.NearRequestDataBean;
import com.pt.module_near.bean.json.NearJsonBean;
import com.pt.module_near.bean.request.NearRequestRequestBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import retrofit2.converter.gson.GsonConverterFactory;

public class AllPersonSearchActDelegete extends AppDelegate {

    private SmartRefreshLayout srl_near_request_goods_page;
    private RecyclerView rcv_request_goods_page;
    private NearRequestAdapter nearAdapter;
    private int cpage;
    private String cityCode;

    @Override
    public int getRootLayoutId() {
        return R.layout.all_person_search;
    }

    private ArrayList<NearItemDataBean> nearRequestDataBeans = new ArrayList<>();
    private ArrayList<NearItemDataBean> nearRequestDataBeanTemps = new ArrayList<>();

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        srl_near_request_goods_page = get(R.id.srl_near_request_goods_page);
        rcv_request_goods_page = get(R.id.rcv_request_goods_page);
        cityCode = getActivity().getIntent().getStringExtra(Constant.KEY_CITY_CODE);

        rcv_request_goods_page.setLayoutManager(new GridLayoutManager(this.getActivity(), 2,
                GridLayoutManager.VERTICAL, false));
        nearAdapter = new NearRequestAdapter(getActivity(),
                R.layout.fragment_near_item, nearRequestDataBeans);
        rcv_request_goods_page.setAdapter(nearAdapter);
        rcv_request_goods_page.setItemAnimator(new DefaultItemAnimator());
        srl_near_request_goods_page.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                cpage ++;
                requestList();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                cpage = 1;
                requestList();
            }
        });
        srl_near_request_goods_page.autoRefresh();
    }

    /**
     * 请求网络
     */
    private void requestList() {
        NearRequestRequestBean requestBean = new NearRequestRequestBean();
        requestBean.setCityCode(cityCode);
        requestBean.setCurrent(cpage);
        requestBean.setGoodsType(1);
        EasyHttp.post(HttpConstant.API_SEARCH).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        NearJsonBean nearJsonBean = new Gson().fromJson(s, NearJsonBean.class);
                        if (nearJsonBean.getCode() == 0) {
                            nearRequestDataBeanTemps.clear();
                            if (nearJsonBean.getData() != null && nearJsonBean.getData().getRecords() != null
                                    && nearJsonBean.getData().getRecords().size() > 0) {
                                for (SearchJsonBean.DataBean.RecordsBean recordsBean : nearJsonBean.getData().getRecords()) {
                                    NearItemDataBean itemDataBean = new NearItemDataBean();
                                    itemDataBean.setTitle(recordsBean.getTitle());
                                    itemDataBean.setPrice(recordsBean.getPrice());
                                    itemDataBean.setPic_url(recordsBean.getPic1Url());
                                    itemDataBean.setPicture(recordsBean.getPic1());
                                    itemDataBean.setId(recordsBean.getId());
                                    nearRequestDataBeanTemps.add(itemDataBean);
                                }
                                if (srl_near_request_goods_page.isRefreshing()) {
                                    nearRequestDataBeans.clear();
                                } else if (srl_near_request_goods_page.isLoading()) {

                                }
                                nearRequestDataBeans.addAll(nearRequestDataBeanTemps);
                                nearAdapter.notifyDataSetChanged();
                                if (srl_near_request_goods_page.isRefreshing()) {
                                    srl_near_request_goods_page.finishRefresh();
                                } else if (srl_near_request_goods_page.isLoading()) {
                                    srl_near_request_goods_page.finishLoadmore();
                                }
                            } else {
                                if (srl_near_request_goods_page.isRefreshing()) {
                                    srl_near_request_goods_page.resetNoMoreData();
                                    srl_near_request_goods_page.finishRefresh();
                                } else if (srl_near_request_goods_page.isLoading()) {
                                    srl_near_request_goods_page.finishLoadmoreWithNoMoreData();
                                }
                            }
                        } else if (nearJsonBean.getCode() == 500) {
                            Snackbar.make(srl_near_request_goods_page, "服务端返回数据有误", Snackbar.LENGTH_SHORT).show();
                        } else if (nearJsonBean.getCode() == 401) {
                            SPHelper.putString("token", "", true);
                            SPHelper.putString("phone", "", true);
                            ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                            getActivity().finish();
                        }
                    }
                });
    }
}
