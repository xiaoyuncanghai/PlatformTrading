package com.pt.module_mine.delegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.bean.databean.ModifyInfoDataBean;
import com.pt.lib_common.bean.jsonbean.GoodsOffShelfJsonBean;
import com.pt.lib_common.bean.requestBean.GoodsOffShelfRequestBean;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.SPHelper;
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
    private int need_position = -1;

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
                cpage++;
                requestList();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                cpage = 1;
                requestList();
            }
        });
        srl_mine_publish_list.autoRefresh();

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.publish_item_onSale) {
                String goods_id = publishList.get(position).getId();
                TextView publish_item_onSale = (TextView) adapter.getViewByPosition(rcv_mine_publish_list,
                        position, R.id.publish_item_onSale);
                //上架操作
                if (publishList.get(position).getGoodsStatus() == 0) {
                    requestOnShelf(goods_id, position, publish_item_onSale);
                } else if (publishList.get(position).getGoodsStatus() == 1) {
                    requestOffShelf(goods_id, position, publish_item_onSale);
                }
            }

            if (view.getId() == R.id.publish_item_modify) {
                //修改
                String goods_id = publishList.get(position).getId();
                ModifyInfoDataBean infoData = new ModifyInfoDataBean();
                infoData.setUserType(publishList.get(position).getGoodsType());
                infoData.setTitle(publishList.get(position).getTitle());
                infoData.setDescription(publishList.get(position).getDescription());
                //infoData.setCategory(publishList.get(position).getC);
                infoData.setPrice(publishList.get(position).getPrice());
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY_MODIFY_INFO, infoData);
                ARouter.getInstance().build(ARouterPath.GOODS_MODIFY)
                        .withBundle(Constant.KEY_MODIFY_INFO_SERIALIZABLE, bundle)
                        .withString(Constant.KEY_GOODS_ID, goods_id).navigation(getActivity(),
                        Constant.KEY_MODIFY_FROM_PUBLISH_LIST);
            }

            if (view.getId() == R.id.publish_item_delete) {
                //删除
                String goods_id = publishList.get(position).getId();
                deleteGoods(position, goods_id);
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                need_position = position;
                String goods_id = publishList.get(position).getId();
                ARouter.getInstance().build(ARouterPath.GOODS_DETAIL)
                        .withString(Constant.KEY_GOODS_ID, goods_id)
                        .navigation(getActivity(), Constant.KEY_FROM_PUBLISH_LIST_REQUEST);
            }
        });
    }

    /**
     * 删除商品
     *
     * @param goods_id
     */
    private void deleteGoods(int position, String goods_id) {
        GoodsOffShelfRequestBean requestBean = new GoodsOffShelfRequestBean();
        requestBean.setId(goods_id);
        EasyHttp.post(HttpConstant.API_DELETE_GOODS).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                    }

                    @Override
                    public void onSuccess(String s) {
                        GoodsOffShelfJsonBean jsonBean = new Gson().fromJson(s, GoodsOffShelfJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            Snackbar.make(getRootView(), "删除成功", Snackbar.LENGTH_SHORT).show();
                            publishList.remove(position);
                            adapter.notifyItemRemoved(position);
                        }
                    }
                });
    }

    /**
     * 上架操作
     *
     * @param goods_id
     * @param position
     * @param publish_item_onSale
     */
    private void requestOnShelf(String goods_id, int position, TextView publish_item_onSale) {
        GoodsOffShelfRequestBean requestBean = new GoodsOffShelfRequestBean();
        requestBean.setId(goods_id);
        EasyHttp.post(HttpConstant.API_ON_SHELF).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .headers("Authorization", SPHelper.getString("token", "", true))
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                    }

                    @Override
                    public void onSuccess(String s) {
                        GoodsOffShelfJsonBean jsonBean = new Gson().fromJson(s, GoodsOffShelfJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            //数据还是没有跟新, 需要直接将view
                            publishList.get(position).setGoodsStatus(1);
                            adapter.notifyItemChanged(position);
                            publish_item_onSale.setText("下架");
                        }
                    }
                });
    }

    private void requestOffShelf(String goods_id, int position, TextView publish_item_onSale) {
        GoodsOffShelfRequestBean requestBean = new GoodsOffShelfRequestBean();
        requestBean.setId(goods_id);
        EasyHttp.post(HttpConstant.API_OFF_SHELF).headers("Content-Type", "application/json")
                .headers("Authorization", SPHelper.getString("token", "", true))
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Snackbar.make(getRootView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String s) {
                        GoodsOffShelfJsonBean jsonBean = new Gson().fromJson(s, GoodsOffShelfJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            publishList.get(position).setGoodsStatus(0);
                            adapter.notifyItemChanged(position);
                            publish_item_onSale.setText("下架");
                        }
                    }
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
                                    //dataBean.setCategory(recordsBean.get);
                                    dataBean.setGoodsStatus(recordsBean.getGoodsStatus());
                                    dataBean.setGoodsStatusDes(recordsBean.getGoodsStatusDes());
                                    dataBean.setGoodsType(recordsBean.getGoodsType());
                                    dataBean.setId(recordsBean.getId());
                                    dataBean.setPic1(recordsBean.getPic1());
                                    dataBean.setPic1Url(recordsBean.getPic1Url());
                                    dataBean.setPrice(recordsBean.getPrice());
                                    dataBean.setCreate_time(recordsBean.getCreateTime());
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
                        } else if (jsonBean.getCode() == 401) {
                            //accesstoekn过期
                            Snackbar.make(getRootView(), "登录已经过期, 请重新登录", Snackbar.LENGTH_SHORT).show();
                            SPHelper.putString("token", "", true);
                            SPHelper.putString("phone", "", true);
                            ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                            getActivity().finish();
                        }
                    }
                });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.KEY_FROM_PUBLISH_LIST_REQUEST && resultCode == getActivity().RESULT_OK
                && need_position != -1) {
            publishList.remove(need_position);
            adapter.notifyItemRemoved(need_position);
        }

        if (requestCode == Constant.KEY_MODIFY_FROM_PUBLISH_LIST && resultCode == getActivity().RESULT_OK) {
            publishList.clear();
            requestList();
        }
    }


}





















