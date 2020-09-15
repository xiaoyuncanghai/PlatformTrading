package com.pt.module_mine.delegate;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.module_mine.R;
import com.pt.module_mine.adpter.PublistListAdapter;
import com.pt.module_mine.bean.PublishItemDataBean;
import com.pt.module_mine.bean.json.PublishListJsonBean;
import com.pt.module_mine.bean.request.PublishListRequestBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import retrofit2.converter.gson.GsonConverterFactory;

public class PublishListActDelegate extends AppDelegate {

    private SmartRefreshLayout srl_mine_publish_list;
    private RecyclerView rcv_mine_publish_list;
    private PublistListAdapter adapter;
    private ArrayList<PublishItemDataBean> publishList = new ArrayList<PublishItemDataBean>();
    private ArrayList<PublishItemDataBean> publishListTemp = new ArrayList<PublishItemDataBean>();
    private int cpage = 1;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_publish_list;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        srl_mine_publish_list = get(R.id.srl_mine_publish_list);
        rcv_mine_publish_list = get(R.id.rcv_mine_publish_list);

        rcv_mine_publish_list.setLayoutManager(new GridLayoutManager(this.getActivity(), 1,
                GridLayoutManager.VERTICAL, false));
        adapter = new PublistListAdapter(getActivity(), R.layout.fragment_publish_list_item,
                publishList);
        rcv_mine_publish_list.setAdapter(adapter);
        rcv_mine_publish_list.setItemAnimator(new DefaultItemAnimator());

        srl_mine_publish_list.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
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
        srl_mine_publish_list.autoRefresh();

        adapter.setOnItemClickListener((adapter, view, position) -> {
            String goods_id = publishList.get(position).getId();
            ARouter.getInstance().build(ARouterPath.GOODS_DETAIL)
                    .withString(Constant.KEY_GOODS_ID, goods_id)
                    .navigation();
        });
    }

    /**
     * 请求网络
     */
    private void requestList() {
        PublishListRequestBean requestBean = new PublishListRequestBean();
        requestBean.setCurrent(cpage);
        EasyHttp.post(HttpConstant.API_PUBLISH_GOODS).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        PublishListJsonBean jsonBean = new Gson().fromJson(s, PublishListJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            if (jsonBean.getData() != null && jsonBean.getData().getRecords() != null
                                && jsonBean.getData().getRecords().size() > 0) {
                                publishListTemp.clear();
                                for (PublishListJsonBean.DataBean.RecordsBean recordsBean
                                        : jsonBean.getData().getRecords()) {
                                    PublishItemDataBean dataBean = new PublishItemDataBean();
                                    dataBean.setTitle(recordsBean.getTitle());
                                    dataBean.setDescription(recordsBean.getDescription());
                                    dataBean.setGoodsStatus(recordsBean.getGoodsStatus());
                                    dataBean.setGoodsStatusDes(recordsBean.getGoodsStatusDes());
                                    dataBean.setGoodsType(recordsBean.getGoodsType());
                                    dataBean.setId(recordsBean.getId());
                                    dataBean.setPic1(recordsBean.getPic1());
                                    dataBean.setPic1Url(recordsBean.getPic1Url());
                                    dataBean.setPrice(recordsBean.getPrice());
                                    publishListTemp.add(dataBean);
                                }
                                if (srl_mine_publish_list.isRefreshing()) {
                                    publishList.clear();
                                }
                                publishList.addAll(publishListTemp);
                                adapter.notifyDataSetChanged();
                                if (srl_mine_publish_list.isRefreshing()) {
                                    srl_mine_publish_list.finishRefresh();
                                } else if (srl_mine_publish_list.isLoading()) {
                                    srl_mine_publish_list.finishLoadmore();
                                }
                            } else {
                                if (srl_mine_publish_list.isRefreshing()) {
                                    srl_mine_publish_list.resetNoMoreData();
                                    srl_mine_publish_list.finishRefresh();
                                } else if (srl_mine_publish_list.isLoading()) {
                                    srl_mine_publish_list.finishLoadmoreWithNoMoreData();
                                }
                            }
                        }
                    }
                });
    }


    public void onNewIntent(Intent intent) {
        srl_mine_publish_list.autoRefresh();
    }
}





















