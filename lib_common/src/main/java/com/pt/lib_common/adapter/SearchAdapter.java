package com.pt.lib_common.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.donkingliang.labels.LabelsView;
import com.pt.lib_common.R;
import com.pt.lib_common.bean.databean.SearchBean;

import java.util.List;

public class SearchAdapter extends BaseMultiItemQuickAdapter<SearchBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Context context;
    private final Typeface typeface;
    private LabelsView labels;
    public SearchAdapter(Context context, List<SearchBean> data) {
        super(data);
        this.context = context;
        typeface= Typeface.createFromAsset(this.context.getAssets(),
                "iconfont/iconfont.ttf");
        addItemType(SearchBean.TYPE_EMPTY, R.layout.search_layout_item_empty);
        addItemType(SearchBean.TYPE_LIST, R.layout.search_layout_item_main);
    }

    public interface OnLabelClickListener{
        void onLabelClick(TextView label, Object data, int position);
    }

    private OnLabelClickListener onLabelClickListener;

    public void setOnLabelClickListener(OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchBean item) {
        switch (helper.getItemViewType()) {
            case SearchBean.TYPE_EMPTY:
                helper.setTypeface(R.id.tv_delete,typeface);
                helper.setText(R.id.tv_delete , "\ue609");
                helper.setText(R.id.tv_empty , "抱歉,暂无关于“"+item.getSearchContent()+"”的相关信息");
                if (item.isSearch()){
                    //  代表搜索了
                    helper.getView(R.id.ll_search_empty).setVisibility(View.VISIBLE);
                }else{
                    helper.getView(R.id.ll_search_empty).setVisibility(View.GONE);
                }
                labels = helper.getView(R.id.search_history);
                labels.setLabels(item.getLabels());
                labels.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
                    @Override
                    public void onLabelClick(TextView label, Object data, int position) {
                        if (onLabelClickListener!=null){
                            onLabelClickListener.onLabelClick(label , data ,position);
                        }
                    }
                });
                helper.addOnClickListener(R.id.tv_delete);
                break;

            case SearchBean.TYPE_LIST:
                ImageView search_promote_iv = helper.getView(R.id.search_promote_iv);
                TextView search_promote_tv_title = helper.getView(R.id.search_promote_tv_title);
                TextView search_promote_tv_price =helper.getView(R.id.search_promote_tv_price);
                search_promote_tv_title.setText(item.getTitle());
                search_promote_tv_price.setText(item.getPrice());
                Glide.with(context).load(item.getPicUrl()).placeholder(R.drawable.default_error)
                        .error(R.drawable.default_error).into(search_promote_iv);
                break;
        }
    }
}
