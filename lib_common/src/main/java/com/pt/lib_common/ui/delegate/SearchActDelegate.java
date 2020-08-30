package com.pt.lib_common.ui.delegate;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.pt.lib_common.R;
import com.pt.lib_common.adapter.SearchAdapter;
import com.pt.lib_common.base.SearchJsonBean;
import com.pt.lib_common.base.request.SearchRequestBean;
import com.pt.lib_common.bean.databean.SearchBean;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.SPHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActDelegate extends AppDelegate {

    private TextView tv_cancel;
    private SmartRefreshLayout srl_search;
    private RecyclerView rv_search;
    private EditText et_search;
    private TextView tv_icon_search;

    private Set<String> histories;
    private List<String> labels = new ArrayList<>();
    private List<SearchBean> searchListTemp = new ArrayList<>();
    private List<SearchBean> searchList = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private String keyword = "";
    private int cpage = 1;
    private String cityCode = "";

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        tv_cancel = get(R.id.tv_cancel);
        srl_search = get(R.id.srl_search);
        rv_search = get(R.id.rv_search);
        et_search = get(R.id.et_search);
        tv_icon_search = get(R.id.tv_icon_search);
        tv_icon_search.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "iconfont/iconfont.ttf"));
        tv_icon_search.setText("\ue616");
        histories = SPHelper.getStringSet("search_history", new HashSet<String>());

        rv_search.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rv_search.setItemAnimator(new DefaultItemAnimator());
        searchAdapter = new SearchAdapter(this.getActivity(), searchList);
        searchAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rv_search.setAdapter(searchAdapter);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        //存在历史记录
        if (histories.size() > 0) {
            for (String history : histories) {
                labels.add(history);
            }
            searchList.clear();
            SearchBean searchBean = new SearchBean(SearchBean.TYPE_EMPTY);
            searchBean.setSearch(false);
            searchBean.setLabels(labels);
            searchList.add(searchBean);
        } else {
            searchList.clear();
        }
        searchAdapter.notifyDataSetChanged();

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (!TextUtils.isEmpty(et_search.getText().toString())) {
                    histories.add(et_search.getText().toString());
                    if (!labels.contains(et_search.getText().toString())) {
                        labels.add(et_search.getText().toString());
                    }
                    if (labels.size() > 20) {
                        labels.remove(0);
                        histories.remove(labels.get(0));
                    }
                    SPHelper.putStringSet("search_history", histories);
                }
                if (et_search.getText().toString().length() > 0) {
                    keyword = et_search.getText().toString();
                    requestList(1, keyword, cityCode, false);
                } else {
                    searchList.clear();
                    labels.clear();
                    for (String history : histories) {
                        labels.add(history);
                    }
                    SearchBean searchBean = new SearchBean(SearchBean.TYPE_EMPTY);
                    searchBean.setSearch(false);
                    searchBean.setLabels(labels);
                    searchList.add(searchBean);
                    searchAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        srl_search.setEnablePureScrollMode(true);
        srl_search.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                cpage++;
                requestList(cpage, keyword, cityCode, true);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                cpage = 1;
                requestList(cpage, keyword, cityCode, true);
            }
        });
        searchAdapter.setOnLabelClickListener(new SearchAdapter.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                requestList(1, (String) data, cityCode, false);
            }
        });

        searchAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SPHelper.remove("search_history");
                searchList.clear();
                SearchBean searchModel = new SearchBean(SearchBean.TYPE_EMPTY);
                searchModel.setSearch(false);
                searchList.add(searchModel);
                searchAdapter.notifyDataSetChanged();
            }
        });

        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (adapter.getItemViewType(position)) {
                    case SearchBean.TYPE_LIST:
                        /*Uri uri = Uri.parse(ARouterPath.
                                BASE_URL+ARouterPath.ARTICAL_DETAIL+"?aid="
                                +searchModelList.get(position).getAid()+"&inner=1");*/
                        //ARouter.getInstance().build(uri).navigation();
                        break;
                }
            }
        });
    }

    private void requestList(int cpage, final String keyword, String cityCode, final boolean isLoadMore) {
        SearchRequestBean requestBean = new SearchRequestBean();
        requestBean.setCurrent(cpage);
        requestBean.setKeyword(keyword);
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
                        LogUtils.d("Search Data = " + s);
                        SearchJsonBean jsonBean = new Gson().fromJson(s, SearchJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            if (jsonBean.getData() != null && jsonBean.getData().getRecords() != null
                                    && jsonBean.getData().getRecords().size() > 0) {
                                searchListTemp.clear();
                                for (SearchJsonBean.DataBean.RecordsBean recordsBean : jsonBean.getData().getRecords()) {
                                    SearchBean bean = new SearchBean(SearchBean.TYPE_LIST);
                                    bean.setId(recordsBean.getId());
                                    bean.setPic(recordsBean.getPic1());
                                    bean.setPicUrl(recordsBean.getPic1Url());
                                    bean.setTitle(recordsBean.getTitle());
                                    bean.setPrice(recordsBean.getPrice());
                                    bean.setGoodsType(recordsBean.getGoodsType());
                                    searchListTemp.add(bean);
                                }
                                if (!isLoadMore) {
                                    //是否需要清除
                                    searchList.clear();
                                }
                                if (srl_search.isRefreshing()) {
                                    searchList.clear();
                                } else if (srl_search.isLoading()) {
                                    LogUtils.d("222");
                                }
                                searchList.addAll(searchListTemp);
                                LogUtils.d("searchList size = "+searchList.size());
                                searchAdapter.notifyDataSetChanged();
                                if (srl_search.isRefreshing()) {
                                    srl_search.finishRefresh();
                                } else if (srl_search.isLoading()) {
                                    srl_search.finishLoadmore();
                                }
                            } else {
                                if (!isLoadMore){
                                    srl_search.setEnablePureScrollMode(true);
                                    searchList.clear();
                                    labels.clear();
                                    for (String history : histories) {
                                        labels.add(history);
                                    }
                                    SearchBean searchModel1 = new SearchBean(SearchBean.TYPE_EMPTY);
                                    searchModel1.setSearch(true);
                                    searchModel1.setSearchContent(keyword);
                                    searchModel1.setLabels(labels);
                                    searchList.add(searchModel1);
                                    searchAdapter.notifyDataSetChanged();
                                }
                                /*if (srl_search.isRefreshing()) {
                                    srl_search.resetNoMoreData();
                                    srl_search.finishRefresh();
                                } else if (srl_search.isLoading()) {
                                    srl_search.finishLoadmoreWithNoMoreData();
                                }*/
                            }
                        } else if (jsonBean.getCode() == 401) {
                            //去登陆界面
                        }
                    }
                });
    }
}
