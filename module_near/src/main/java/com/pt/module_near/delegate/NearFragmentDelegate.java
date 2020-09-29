package com.pt.module_near.delegate;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.SPHelper;
import com.pt.lib_common.util.Utils;
import com.pt.lib_common.view.citychoose.db.DBManager;
import com.pt.lib_common.view.citychoose.model.City;
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
import java.util.List;

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
    private LinearLayout near_search;
    private LinearLayout ll_user_search;
    private LinearLayout near_header_layout_ll;

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
        near_search = get(R.id.near_search);
        ll_user_search = get(R.id.ll_user_search);
        near_header_layout_ll = get(R.id.near_header_layout_ll);
        rcv_near_page.setLayoutManager(new GridLayoutManager(this.getActivity(), 2,
                GridLayoutManager.VERTICAL, false));
        near_header_layout_ll.setPadding(0,
                Utils.getStatusBarHeight(this.getActivity()) + Utils.dip2px(this.getActivity(), 5f),
                0, Utils.dip2px(this.getActivity(), 10f));
        nearAdapter = new NearAdapter(getActivity(), R.layout.fragment_near_item, nearItemDataBeans);
        rcv_near_page.setAdapter(nearAdapter);
        rcv_near_page.setItemAnimator(new DefaultItemAnimator());

        near_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去search 界面
                ARouter.getInstance().build(ARouterPath.SEARCH_PATH).navigation();
            }
        });

        ll_user_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //大家都在搜索
                ARouter.getInstance().build(ARouterPath.ALL_SEARCH_PATH)
                        .withString(Constant.KEY_CITY_CODE, cityCode).navigation();
            }
        });

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

        nearAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.near_promote_iv_to_details) {
                    String product_id = nearItemDataBeans.get(position).getId();
                    ARouter.getInstance().build(ARouterPath.GOODS_DETAIL)
                            .withString(Constant.KEY_GOODS_ID, product_id)
                            .navigation();
                }
            }
        });
    }

    /**
     * 请求列表
     */
    private void requestList() {
        NearRequestBean requestBean = new NearRequestBean();
        requestBean.setCurrent(cPage);
        requestBean.setCityCode(cityCode);
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
                                for (NearJsonBean.DataBean.RecordsBean recordsBean : nearJsonBean.getData().getRecords()) {
                                    NearItemDataBean itemDataBean = new NearItemDataBean();
                                    itemDataBean.setTitle(recordsBean.getTitle());
                                    itemDataBean.setPrice(recordsBean.getPrice());
                                    itemDataBean.setPic_url(recordsBean.getPic1Url());
                                    itemDataBean.setPicture(recordsBean.getPic1());
                                    itemDataBean.setId(recordsBean.getId());
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
                                    srl_near_page.finishRefresh();
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
    public void updateCityName(String cityName) {
        dbManager = new DBManager(getActivity());
        List<City> allCities = new DBManager(getActivity()).getCityByProvince();
        for (City city : allCities) {
            if (city.getName().equals(cityName)) {
                cityCode = city.getCode();
                LogUtils.d("NearFgt rec cityCode = "+ cityCode);
                break;
            }
        }
    }
}
