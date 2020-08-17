package com.pt.module_homepage.delegate;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.pt.lib_common.bean.jsonbean.SendSmsJsonBean;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.view.circle.CircleImageView;
import com.pt.module_homepage.R;
import com.pt.module_homepage.jsonbean.BannerJsonBean;
import com.pt.module_homepage.requestbean.BannerRequestBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import retrofit2.converter.gson.GsonConverterFactory;

public class HomePageFragmentDelegate extends AppDelegate {

    private CircleImageView iv_location;
    private TextView tv_location;
    private LinearLayout edit_search;
    private TextView tv_icon_search;
    private ImageView iv_user_info;
    private SmartRefreshLayout srl_home_page;
    private RecyclerView rcv_home_page;
    private int cpage = 1;

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
        iv_location = get(R.id.iv_location);
        tv_location = get(R.id.tv_location);
        edit_search = get(R.id.edit_search);
        tv_icon_search = get(R.id.tv_icon_search);
        iv_user_info = get(R.id.iv_user_info);

        srl_home_page = get(R.id.srl_home_page);
        rcv_home_page = get(R.id.rcv_home_page);

        srl_home_page.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                cpage++;
                //requestList(cpage);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                requestBanner();
                requestGoodCategory();
                requestGoodIndex();
            }
        });
    }

    //请求轮播图光谷
    private void requestBanner() {
        BannerRequestBean requestBean = new BannerRequestBean();
        requestBean.setCategory(1);
        requestBean.setShowNumber(5);
        EasyHttp.post(HttpConstant.API_GET_AD).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        LogUtils.d("get AD failed");
                    }

                    @Override
                    public void onSuccess(String s) {
                        LogUtils.d("AD: "+s);
                        BannerJsonBean bannerJsonBean = new Gson().fromJson(s, BannerJsonBean.class);
                        //转化数据
                    }
                });
    }

    private void requestGoodIndex() {
    }

    private void requestGoodCategory() {
    }
}
