package com.pt.lib_common.ui.delegate;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.pt.lib_common.R;
import com.pt.lib_common.adapter.CategoryListAdapter;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.bean.databean.CategoryItemDateBean;
import com.pt.lib_common.bean.jsonbean.CategoryListJsonBean;
import com.pt.lib_common.bean.requestBean.CategoryListRequestBean;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import retrofit2.converter.gson.GsonConverterFactory;

public class OrderCategoryActDelegate extends AppDelegate {

    private SmartRefreshLayout srl_order_category;
    private RecyclerView rcv_order_category;
    private String id;
    private ArrayList<CategoryItemDateBean> categoryItemList = new ArrayList<>();
    private ArrayList<CategoryItemDateBean> categoryItemListTemp = new ArrayList<>();
    private CategoryListAdapter adapter;
    private int cpage = 1;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_order_category;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        id = getActivity().getIntent().getStringExtra(Constant.KEY_CATEGORY_ID);
        srl_order_category = get(R.id.srl_order_category);
        rcv_order_category = get(R.id.rcv_order_category);

        rcv_order_category.setLayoutManager(new GridLayoutManager(this.getActivity(), 1,
                GridLayoutManager.VERTICAL, false));
        adapter = new CategoryListAdapter(getActivity(), R.layout.activity_category_list_item,
                categoryItemList);
        rcv_order_category.setAdapter(adapter);
        rcv_order_category.setItemAnimator(new DefaultItemAnimator());

        srl_order_category.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                cpage++;
                requestList();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                cpage = 1;
                requestList();
            }
        });
        srl_order_category.autoRefresh();
        /*adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String product_id = categoryItemList.get(position).getId();
                ARouter.getInstance().build(ARouterPath.GOODS_DETAIL)
                        .withString(Constant.KEY_GOODS_ID, product_id)
                        .navigation();
            }
        });*/
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.category_item_tv_see) {
                    String product_id = categoryItemList.get(position).getId();
                    ARouter.getInstance().build(ARouterPath.GOODS_DETAIL)
                            .withString(Constant.KEY_GOODS_ID, product_id)
                            .navigation();
                }
            }
        });
    }

    /**
     * 请求网络
     */
    private void requestList() {
        CategoryListRequestBean bean = new CategoryListRequestBean();
        bean.setCateId1(id);
        bean.setCurrent(cpage);
        //goodType & keyword为可选参数不传
        EasyHttp.post(HttpConstant.API_CATEGORY_LIST).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(bean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        CategoryListJsonBean jsonBean = new Gson().fromJson(s, CategoryListJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            categoryItemListTemp.clear();
                            if (jsonBean.getData() != null && jsonBean.getData().getRecords() != null
                                    && jsonBean.getData().getRecords().size() > 0) {
                                for (CategoryListJsonBean.DataBean.RecordsBean json : jsonBean.getData().getRecords()) {
                                    CategoryItemDateBean categoryItem = new CategoryItemDateBean();
                                    categoryItem.setTitle(json.getTitle());
                                    categoryItem.setDescription(json.getDescription());
                                    categoryItem.setId(json.getId());
                                    categoryItem.setPrice(json.getPrice());
                                    categoryItem.setPic(json.getPic1());
                                    categoryItem.setPicUrl(json.getPic1Url());
                                    categoryItem.setStatus(json.getGoodsType());
                                    categoryItemListTemp.add(categoryItem);
                                }
                                if (srl_order_category.isRefreshing()) {
                                    categoryItemList.clear();
                                }
                                categoryItemList.addAll(categoryItemListTemp);
                                adapter.notifyDataSetChanged();
                                if (srl_order_category.isRefreshing()) {
                                    srl_order_category.finishRefresh();
                                } else if (srl_order_category.isLoading()) {
                                    srl_order_category.finishLoadmore();
                                }
                            } else {
                                if (srl_order_category.isRefreshing()) {
                                    srl_order_category.resetNoMoreData();
                                    srl_order_category.finishRefresh();
                                } else if (srl_order_category.isLoading()) {
                                    srl_order_category.finishLoadmoreWithNoMoreData();
                                }
                            }

                        }
                    }
                });
    }
}
