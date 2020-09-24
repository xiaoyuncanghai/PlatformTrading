package com.pt.module_homepage.delegate;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.bean.CityInfo;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.SPHelper;
import com.pt.lib_common.util.Utils;
import com.pt.lib_common.view.circle.CircleImageView;
import com.pt.lib_common.view.citychoose.CityPicker;
import com.pt.lib_common.view.citychoose.adapter.OnPickListener;
import com.pt.lib_common.view.citychoose.db.DBManager;
import com.pt.lib_common.view.citychoose.model.City;
import com.pt.lib_common.view.citychoose.model.HotCity;
import com.pt.lib_common.view.citychoose.model.LocateState;
import com.pt.lib_common.view.citychoose.model.LocatedCity;
import com.pt.module_homepage.R;
import com.pt.module_homepage.adapter.HomePageAdapter;
import com.pt.module_homepage.databean.BannerItemDataBean;
import com.pt.module_homepage.databean.HomePageDataBean;
import com.pt.module_homepage.jsonbean.BannerJsonBean;
import com.pt.module_homepage.jsonbean.HomePageCategoryJsonBean;
import com.pt.module_homepage.jsonbean.HomePagePromoteJsonBean;
import com.pt.module_homepage.requestbean.HomePageIndexRequestBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportActivity;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePageFragmentDelegate extends AppDelegate {

    private ImageView iv_location;
    private TextView tv_location;
    private LinearLayout edit_search;
    private ImageView tv_icon_search;
    private SmartRefreshLayout srl_home_page;
    private RecyclerView rcv_home_page;
    private int cpage = 1;
    private LinearLayout fragment_header_layout;
    private ConstraintLayout coo_location;
    private ArrayList<BannerItemDataBean> bannerItemList = new ArrayList<BannerItemDataBean>();
    private ArrayList<HomePageDataBean> homePageItemList = new ArrayList<HomePageDataBean>();
    private ArrayList<HomePageDataBean> homePageItemListTemp = new ArrayList<HomePageDataBean>();
    private ArrayList<HomePageDataBean> homePageBannerTemp = new ArrayList<HomePageDataBean>();
    private ArrayList<HomePageDataBean> homePageCategoryTemp = new ArrayList<HomePageDataBean>();
    private HomePageAdapter homePageAdapter;
    private List<HotCity> hotCities;
    private String cityCode = "";
    private String cityName = "";

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        EventBus.getDefault().register(this);
        initCityData();
        initView();
    }

    private void initCityData() {
        hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京市", "110100"));
        hotCities.add(new HotCity("天津市", "120100"));
        hotCities.add(new HotCity("武汉市", "420100"));
    }

    private void initView() {
        fragment_header_layout = get(R.id.fragment_header_layout);
        iv_location = get(R.id.iv_location);
        tv_location = get(R.id.tv_location);
        edit_search = get(R.id.edit_search);
        tv_icon_search = get(R.id.tv_icon_search);
        coo_location = get(R.id.coo_location);
        srl_home_page = get(R.id.srl_home_page);
        rcv_home_page = get(R.id.rcv_home_page);

        fragment_header_layout.setPadding(0,
                Utils.getStatusBarHeight(this.getActivity()) + Utils.dip2px(this.getActivity(), 5f),
                0, Utils.dip2px(this.getActivity(), 10f));
        rcv_home_page.setLayoutManager(new GridLayoutManager(this.getActivity(), 4,
                GridLayoutManager.VERTICAL, false));
        homePageAdapter = new HomePageAdapter(getActivity(), homePageItemList);
        rcv_home_page.setAdapter(homePageAdapter);
        rcv_home_page.setItemAnimator(new DefaultItemAnimator());

        srl_home_page.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                cpage++;
                requestGoodIndex();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                cpage = 1;
                requestBanner();
            }
        });
        srl_home_page.autoRefresh();

        homePageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (adapter.getItemViewType(position)) {
                    case HomePageDataBean.TYPE_HOME_PAGE_CATEGORY:
                        String title = homePageItemList.get(position).getCate_name();
                        String id = homePageItemList.get(position).getCate_id();
                        ARouter.getInstance().build(ARouterPath.ORDER_CATEGORY)
                                .withString(Constant.KEY_CATEGORY_NAME, title)
                                .withString(Constant.KEY_CATEGORY_ID, id)
                                .navigation();
                        break;
                }
            }
        });

        homePageAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.homepage_promote_tv_see) {
                    String product_id = homePageItemList.get(position).getPromote_id();
                    ARouter.getInstance().build(ARouterPath.GOODS_DETAIL)
                            .withString(Constant.KEY_GOODS_ID, product_id)
                            .navigation();
                }
            }
        });

        //定位点击
        coo_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LocatedCity locatedCity;
                if (cityCode != "" && cityName != "") {
                    locatedCity = new LocatedCity(cityName, cityCode);
                } else {
                    locatedCity = null;
                }
                CityPicker.from((SupportActivity) HomePageFragmentDelegate.this.getActivity())
                        .enableAnimation(false)
                        .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                        .setLocatedCity(locatedCity)
                        .setHotCities(hotCities)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                cityCode = data.getCode();
                                cityName = data.getName();
                                tv_location.setText(cityName);
                                Toast.makeText(
                                        HomePageFragmentDelegate.this.getActivity(),
                                        String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
                                        Toast.LENGTH_SHORT)
                                        .show();
                                srl_home_page.autoRefresh();
                            }

                            @Override
                            public void onLocate() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (locatedCity != null) {
                                            CityPicker.from((SupportActivity) HomePageFragmentDelegate.this.getActivity())
                                                    .locateComplete(
                                                            locatedCity, LocateState.SUCCESS);
                                        } else {
                                            CityPicker.from((SupportActivity) HomePageFragmentDelegate.this.getActivity())
                                                    .locateComplete(
                                                            null, LocateState.FAILURE);
                                        }
                                    }
                                }, 3000);
                            }

                            //取消定位
                            @Override
                            public void onCancel() {

                            }
                        }).show();
            }
        });

        edit_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.SEARCH_PATH).navigation();
            }
        });
    }

    //请求轮播图数据
    private void requestBanner() {
        EasyHttp.post(HttpConstant.API_GET_AD)
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Snackbar.make(srl_home_page, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String s) {
                        BannerJsonBean bannerJsonBean = new Gson().fromJson(s, BannerJsonBean.class);
                        //清空了tempList
                        homePageBannerTemp.clear();
                        if (bannerJsonBean.getCode() == 0) {
                            if (bannerJsonBean.getData() != null && bannerJsonBean.getData().size() > 0) {
                                bannerItemList.clear();
                                for (int i = 0; i < bannerJsonBean.getData().size(); i++) {
                                    BannerItemDataBean bannerItem = new BannerItemDataBean();
                                    bannerItem.setTitle(bannerJsonBean.getData().get(i).getTitle());
                                    bannerItem.setImageUrl(bannerJsonBean.getData().get(i).getImageUrl());
                                    bannerItem.setLinkUrl(bannerJsonBean.getData().get(i).getLinkUrl());
                                    bannerItem.setReMark(bannerJsonBean.getData().get(i).getRemark());
                                    bannerItemList.add(bannerItem);
                                }
                                HomePageDataBean bannerDataBean = new HomePageDataBean();
                                bannerDataBean.setItemType(HomePageDataBean.TYPE_HOME_PAGE_BANNER);
                                bannerDataBean.setBannerItemList(bannerItemList);
                                homePageBannerTemp.add(bannerDataBean);
                            }
                        }
                        requestGoodCategory();
                    }
                });
    }

    //请求
    private void requestGoodCategory() {
        EasyHttp.post(HttpConstant.API_HOME_CATE)
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Snackbar.make(srl_home_page, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String s) {
                        HomePageCategoryJsonBean categoryJsonBean = new Gson().fromJson(s, HomePageCategoryJsonBean.class);
                        if (categoryJsonBean.getCode() == 0) {
                            homePageCategoryTemp.clear();
                            if (categoryJsonBean.getData() != null && categoryJsonBean.getData().size() > 0) {
                                for (int i = 0; i < categoryJsonBean.getData().size(); i++) {
                                    HomePageDataBean homepageCateDataBean = new HomePageDataBean();
                                    homepageCateDataBean.setItemType(HomePageDataBean.TYPE_HOME_PAGE_CATEGORY);
                                    homepageCateDataBean.setCate_name(categoryJsonBean.getData().get(i).getName());
                                    homepageCateDataBean.setCate_id(categoryJsonBean.getData().get(i).getId());
                                    homepageCateDataBean.setCate_icon(categoryJsonBean.getData().get(i).getIcon());
                                    homePageCategoryTemp.add(homepageCateDataBean);
                                }
                            }
                        }
                        requestGoodIndex();
                    }
                });
    }

    private void requestGoodIndex() {
        HomePageIndexRequestBean homePageIndexRequestBean = new HomePageIndexRequestBean();
        homePageIndexRequestBean.setCurrent(cpage);
        EasyHttp.post(HttpConstant.API_HOME_PAGE).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(homePageIndexRequestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Snackbar.make(srl_home_page, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String s) {
                        HomePagePromoteJsonBean promoteJsonBean = new Gson().fromJson(s, HomePagePromoteJsonBean.class);
                        if (promoteJsonBean.getCode() == 0) {
                            homePageItemListTemp.clear();
                            if (promoteJsonBean.getData() != null && promoteJsonBean.getData().getRecords() != null
                                    && promoteJsonBean.getData().getRecords().size() > 0) {
                                for (int i = 0; i < promoteJsonBean.getData().getRecords().size(); i++) {
                                    HomePageDataBean homepagePromoteDataBean = new HomePageDataBean();
                                    homepagePromoteDataBean.setItemType(HomePageDataBean.TYPE_HOME_PAGE_PROMOTE);
                                    homepagePromoteDataBean.setPromote_id(promoteJsonBean.getData().getRecords().get(i).getId());
                                    homepagePromoteDataBean.setPromote_pic(promoteJsonBean.getData().getRecords().get(i).getPic1Url());
                                    homepagePromoteDataBean.setPromote_price(promoteJsonBean.getData().getRecords().get(i).getPrice());
                                    homepagePromoteDataBean.setPromote_type(promoteJsonBean.getData().getRecords().get(i).getGoodsType());
                                    homepagePromoteDataBean.setPromote_title(promoteJsonBean.getData().getRecords().get(i).getTitle());
                                    homepagePromoteDataBean.setPromote_description(promoteJsonBean.getData().getRecords().get(i).getDescription());
                                    homePageItemListTemp.add(homepagePromoteDataBean);
                                }
                            } else {
                                if (srl_home_page.isRefreshing()) {
                                    srl_home_page.resetNoMoreData();
                                } else if (srl_home_page.isLoading()) {
                                    srl_home_page.finishLoadmoreWithNoMoreData();
                                }
                            }
                            if (srl_home_page.isRefreshing()) {
                                homePageItemList.clear();
                                homePageItemList.addAll(homePageBannerTemp);
                                homePageItemList.addAll(homePageCategoryTemp);
                                homePageItemList.addAll(homePageItemListTemp);
                            } else if (srl_home_page.isLoading()) {
                                homePageItemList.addAll(homePageItemListTemp);
                            }
                            homePageAdapter.notifyDataSetChanged();
                            if (srl_home_page.isRefreshing()) {
                                srl_home_page.finishRefresh();
                            } else if (srl_home_page.isLoading()) {
                                srl_home_page.finishLoadmore();
                            }
                        } else if (promoteJsonBean.getCode() == 500) {
                            Snackbar.make(srl_home_page, "服务端返回数据有误", Snackbar.LENGTH_SHORT).show();
                        } else if (promoteJsonBean.getCode() == 401) {
                            //accesstoekn过期
                            Snackbar.make(srl_home_page, "登录已经过期, 请重新登录", Snackbar.LENGTH_SHORT).show();
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
        this.cityName = cityName;
        tv_location.setText(cityName);
        List<City> allCities = new DBManager(getActivity()).getCityByProvince();
        for (City city : allCities) {
            if (city.getName().equals(cityName)) {
                cityCode = city.getCode();
                break;
            }
        }
        srl_home_page.autoRefresh();
    }
}
