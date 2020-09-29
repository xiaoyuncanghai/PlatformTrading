package com.pt.module_mine.delegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.pt.lib_common.bean.databean.FundSideItem;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.module_mine.R;
import com.pt.module_mine.adpter.FundSideAdapter;
import com.pt.module_mine.bean.json.FuncSideJsonBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class FundSideListActDelegate extends AppDelegate {

    private SmartRefreshLayout srl_fund_side_list;
    private RecyclerView rcv_fund_side_list;
    private ArrayList<FundSideItem> fundSideList = new ArrayList<>();
    private FundSideAdapter adapter;
    private View emptyView;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_fund_side_list;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        srl_fund_side_list = get(R.id.srl_fund_side_list);
        rcv_fund_side_list = get(R.id.rcv_fund_side_list);
        emptyView = View.inflate(this.getActivity(), R.layout.layout_empty, null);
        rcv_fund_side_list.setLayoutManager(new GridLayoutManager(this.getActivity(), 1,
                GridLayoutManager.VERTICAL, false));
        adapter = new FundSideAdapter(getActivity(), R.layout.fund_side_item, fundSideList);
        rcv_fund_side_list.setAdapter(adapter);
        rcv_fund_side_list.setItemAnimator(new DefaultItemAnimator());

        srl_fund_side_list.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                requestList();
            }
        });

        srl_fund_side_list.autoRefresh();

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //点击item取得对应的
                FundSideItem item = fundSideList.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.CHOOSE_FUND_ITEM, item);
                Intent intent = new Intent();
                intent.putExtra(Constant.KEY_CHOOSE_FUND, bundle);
                getActivity().setResult(Constant.KEY_ORDER_FUNDER_RESULT, intent);
                getActivity().finish();
            }
        });
    }

    /**
     * 请求接口
     */
    private void requestList() {
        EasyHttp.post(HttpConstant.API_FUND_SIDE_LIST).timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        FuncSideJsonBean jsonBean = new Gson().fromJson(s, FuncSideJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            if (jsonBean.getData() != null && jsonBean.getData().size() > 0) {
                                fundSideList.clear();
                                for (FuncSideJsonBean.DataBean json : jsonBean.getData()) {
                                    FundSideItem item = new FundSideItem();
                                    item.setName(json.getName());
                                    item.setPhone(json.getPhone());
                                    fundSideList.add(item);
                                }
                                adapter.notifyDataSetChanged();
                                if (srl_fund_side_list.isRefreshing()) {
                                    //正在刷新
                                    srl_fund_side_list.finishRefresh();
                                }
                            }
                        } else {
                            if (srl_fund_side_list.isRefreshing()) {
                                srl_fund_side_list.finishRefresh();
                                srl_fund_side_list.resetNoMoreData();
                            }
                            ((TextView) emptyView.findViewById(R.id.tv_empty)).setText("暂时没有资金方入驻~");
                            ((ImageView) emptyView.findViewById(R.id.iv_empty)).setImageResource(R.drawable.ic_common_excited_yuu1_cat);
                            adapter.setEmptyView(emptyView);
                        }
                    }
                });
    }
}
