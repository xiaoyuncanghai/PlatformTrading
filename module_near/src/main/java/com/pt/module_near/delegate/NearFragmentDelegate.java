package com.pt.module_near.delegate;

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
import com.pt.lib_common.bean.CityInfo;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.SPHelper;
import com.pt.lib_common.view.citychoose.db.DBManager;
import com.pt.module_near.R;
import com.pt.module_near.adapter.NearAdapter;
import com.pt.module_near.bean.data.NearItemDataBean;
import com.pt.module_near.bean.json.NearJsonBean;
import com.pt.module_near.bean.request.NearRequestBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import retrofit2.converter.gson.GsonConverterFactory;

public class NearFragmentDelegate extends AppDelegate {

    public SmartRefreshLayout srl_near_page;
    private RecyclerView rcv_near_page;
    private NearAdapter nearAdapter;
    private ArrayList<NearItemDataBean> nearItemDataBeans = new ArrayList<NearItemDataBean>();
    private ArrayList<NearItemDataBean> nearItemDataBeansTemp = new ArrayList<NearItemDataBean>();
    private DBManager dbManager;
    private int cPage = 1;
    private String cityCode = "";

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_near;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        srl_near_page = get(R.id.srl_near_page);
        rcv_near_page = get(R.id.rcv_near_page);

        rcv_near_page.setLayoutManager(new GridLayoutManager(this.getActivity(), 2,
                GridLayoutManager.VERTICAL, false));
        nearAdapter = new NearAdapter(getActivity(), R.layout.fragment_near_item, nearItemDataBeans);
        rcv_near_page.setAdapter(nearAdapter);
        rcv_near_page.setItemAnimator(new DefaultItemAnimator());

        srl_near_page.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                cPage++;
                requestList();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                cPage = 1;
                requestList();
            }
        });
    }

    /**
     * 请求列表
     */
    private void requestList() {
        NearRequestBean requestBean = new NearRequestBean();
        requestBean.setCurrent(cPage);
        requestBean.setKeyword("");
        requestBean.setCityCode(cityCode);
        requestBean.setGoodsType(0);
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
                            nearItemDataBeansTemp.clear();
                            if (nearJsonBean.getData() != null && nearJsonBean.getData().getRecords() != null
                                    && nearJsonBean.getData().getRecords().size() > 0) {
                                for (SearchJsonBean.DataBean.RecordsBean recordsBean : nearJsonBean.getData().getRecords()) {
                                    NearItemDataBean itemDataBean = new NearItemDataBean();
                                    itemDataBean.setTitle(recordsBean.getTitle());
                                    itemDataBean.setPrice(recordsBean.getPrice());
                                    itemDataBean.setPic_url(recordsBean.getPic1Url());
                                    itemDataBean.setPicture(recordsBean.getPic1());
                                    nearItemDataBeansTemp.add(itemDataBean);
                                }
                                if (srl_near_page.isRefreshing()) {
                                    nearItemDataBeans.clear();
                                } else if (srl_near_page.isLoading()) {

                                }
                                nearItemDataBeans.addAll(nearItemDataBeansTemp);
                                nearAdapter.notifyDataSetChanged();
                                if (srl_near_page.isRefreshing()) {
                                    srl_near_page.finishRefresh();
                                } else if (srl_near_page.isLoading()) {
                                    srl_near_page.finishLoadmore();
                                }
                            } else {
                                if (srl_near_page.isRefreshing()) {
                                    srl_near_page.resetNoMoreData();
                                } else if (srl_near_page.isLoading()) {
                                    srl_near_page.finishLoadmoreWithNoMoreData();
                                }
                            }
                        } else if (nearJsonBean.getCode() == 500) {
                            Snackbar.make(srl_near_page, "服务端返回数据有误", Snackbar.LENGTH_SHORT).show();
                        } else if (nearJsonBean.getCode() == 401) {
                            SPHelper.putString("token", "", true);
                            SPHelper.putString("phone", "", true);
                            ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                            getActivity().finish();
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCityName(CityInfo cityInfo) {
        String cityName = cityInfo.getCityName();
        dbManager = new DBManager(getActivity());
        cityCode = dbManager.searchCityForName(cityName);
        LogUtils.d("code = " + cityCode);
    }
}
