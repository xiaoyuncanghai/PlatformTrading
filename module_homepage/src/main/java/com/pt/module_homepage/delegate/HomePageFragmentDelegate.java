package com.pt.module_homepage.delegate;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.Utils;
import com.pt.lib_common.view.circle.CircleImageView;
import com.pt.module_homepage.adapter.HomePageAdapter;
import com.pt.module_homepage.R;
import com.pt.module_homepage.databean.BannerItemDataBean;
import com.pt.module_homepage.databean.HomePageDataBean;
import com.pt.module_homepage.jsonbean.BannerJsonBean;
import com.pt.module_homepage.jsonbean.HomePageCategoryJsonBean;
import com.pt.module_homepage.jsonbean.HomePagePromoteJsonBean;
import com.pt.module_homepage.requestbean.HomePageIndexRequestBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import retrofit2.converter.gson.GsonConverterFactory;

public class HomePageFragmentDelegate extends AppDelegate {

    private CircleImageView iv_location;
    private TextView tv_location;
    private LinearLayout edit_search;
    private ImageView tv_icon_search;
    private ImageView iv_user_info;
    private SmartRefreshLayout srl_home_page;
    private RecyclerView rcv_home_page;
    private int cpage = 1;
    private ConstraintLayout fragment_header_layout;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        initView();
    }

    private void initView() {
        fragment_header_layout = get(R.id.fragment_header_layout);
        iv_location = get(R.id.iv_location);
        tv_location = get(R.id.tv_location);
        edit_search = get(R.id.edit_search);
        tv_icon_search = get(R.id.tv_icon_search);
        iv_user_info = get(R.id.iv_user_info);

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
                requestBanner();
            }
        });
        srl_home_page.autoRefresh();
    }

    private ArrayList<BannerItemDataBean> bannerItemList = new ArrayList<BannerItemDataBean>();
    private ArrayList<HomePageDataBean> homePageItemList = new ArrayList<HomePageDataBean>();
    private ArrayList<HomePageDataBean> homePageItemListTemp = new ArrayList<HomePageDataBean>();
    private HomePageAdapter homePageAdapter;

    //请求轮播图数据
    private void requestBanner() {
        EasyHttp.post(HttpConstant.API_GET_AD)
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                    }

                    @Override
                    public void onSuccess(String s) {
                        BannerJsonBean bannerJsonBean = new Gson().fromJson(s, BannerJsonBean.class);
                        //清空了tempList
                        homePageItemListTemp.clear();
                        if (bannerJsonBean.getCode() == 0) {
                            //遍历数据, 转化数据
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
                                homePageItemListTemp.add(bannerDataBean);
                            }
                        }
                        LogUtils.d("homePageItemListTemp.size1 = " + homePageItemListTemp.size());
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
                            if (categoryJsonBean.getData() != null && categoryJsonBean.getData().size() > 0) {
                                for (int i = 0; i < categoryJsonBean.getData().size(); i++) {
                                    HomePageDataBean homepageCateDataBean = new HomePageDataBean();
                                    homepageCateDataBean.setItemType(HomePageDataBean.TYPE_HOME_PAGE_CATEGORY);
                                    homepageCateDataBean.setCate_name(categoryJsonBean.getData().get(i).getName());
                                    homepageCateDataBean.setCate_id(categoryJsonBean.getData().get(i).getId());
                                    homepageCateDataBean.setCate_icon(categoryJsonBean.getData().get(i).getIcon());
                                    homePageItemListTemp.add(homepageCateDataBean);
                                }
                            }
                        }
                        LogUtils.d("homePageItemListTemp.size2 = " + homePageItemListTemp.size());
                        requestGoodIndex();
                    }
                });
    }

    private void requestGoodIndex() {
        HomePageIndexRequestBean homePageIndexRequestBean = new HomePageIndexRequestBean();
        homePageIndexRequestBean.setCityCode("");
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
                            if (promoteJsonBean.getData() != null && promoteJsonBean.getData().getRecords() != null
                                    && promoteJsonBean.getData().getRecords().size() > 0) {
                                for (int i = 0; i < promoteJsonBean.getData().getRecords().size(); i++) {
                                    HomePageDataBean homepagePromoteDataBean = new HomePageDataBean();
                                    homepagePromoteDataBean.setItemType(HomePageDataBean.TYPE_HOME_PAGE_PROMOTE);
                                    homepagePromoteDataBean.setPromote_id(promoteJsonBean.getData().getRecords().get(i).getId());
                                    homepagePromoteDataBean.setPromote_pic(promoteJsonBean.getData().getRecords().get(i).getPic1());
                                    homepagePromoteDataBean.setPromote_price(promoteJsonBean.getData().getRecords().get(i).getPrice());
                                    homepagePromoteDataBean.setPromote_type(promoteJsonBean.getData().getRecords().get(i).getGoodsType());
                                    homepagePromoteDataBean.setPromote_title(promoteJsonBean.getData().getRecords().get(i).getTitle());
                                    homePageItemListTemp.add(homepagePromoteDataBean);
                                }
                                if (srl_home_page.isRefreshing()) {
                                    //刷新状态
                                    homePageItemList.clear();
                                }
                                homePageItemList.addAll(homePageItemListTemp);
                                homePageAdapter.notifyItemRangeChanged(homePageItemList.size() - promoteJsonBean.getData().getRecords().size(),
                                        promoteJsonBean.getData().getRecords().size());
                                if (srl_home_page.isRefreshing()) {
                                    srl_home_page.finishRefresh();
                                } else if (srl_home_page.isLoading()) {
                                    srl_home_page.finishLoadmore();
                                }
                            } else {
                                //表示没有数据了
                                if (srl_home_page.isRefreshing()) {
                                    srl_home_page.resetNoMoreData();
                                    srl_home_page.finishRefresh();
                                } else if (srl_home_page.isLoading()) {
                                    srl_home_page.finishLoadmoreWithNoMoreData();
                                }
                            }
                        } else if (promoteJsonBean.getCode() == 500) {
                            //请求参数有误
                            Snackbar.make(srl_home_page, "服务端返回数据有误", Snackbar.LENGTH_SHORT).show();
                        } else if (promoteJsonBean.getCode() == 401) {
                            //accesstoekn过期
                        }
                    }
                });
    }

}
