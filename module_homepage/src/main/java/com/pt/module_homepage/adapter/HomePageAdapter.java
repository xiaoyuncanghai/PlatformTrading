package com.pt.module_homepage.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pt.module_homepage.R;
import com.pt.module_homepage.databean.BannerItemDataBean;
import com.pt.module_homepage.databean.HomePageDataBean;
import com.stx.xhb.androidx.XBanner;

import java.util.List;

public class HomePageAdapter extends BaseMultiItemQuickAdapter<HomePageDataBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    private Context context;
    public HomePageAdapter(Context context, List<HomePageDataBean> data) {
        super(data);
        this.context = context;
        addItemType(HomePageDataBean.TYPE_HOME_PAGE_BANNER, R.layout.home_page_banner);
        addItemType(HomePageDataBean.TYPE_HOME_PAGE_CATEGORY, R.layout.home_page_category);
        addItemType(HomePageDataBean.TYPE_HOME_PAGE_PROMOTE, R.layout.home_page_promote);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePageDataBean item) {
        switch (helper.getItemViewType()) {
            case HomePageDataBean.TYPE_HOME_PAGE_BANNER:
                XBanner banner = helper.getView(R.id.xBanner);
                banner.setBannerData(item.getBannerItemList());
                banner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(context)
                                .load(((BannerItemDataBean) model).getXBannerUrl())
                                .placeholder(R.drawable.banner_placeholder)
                                .error(R.drawable.banner_placeholder).into((ImageView) view);
                    }
                });
                banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(XBanner banner, Object model,View view, int position) {
                        Toast.makeText(context, "点击了第"+position+"图片", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
                        case HomePageDataBean.TYPE_HOME_PAGE_BANNER:
                            return 4;
                        case HomePageDataBean.TYPE_HOME_PAGE_CATEGORY:
                            return 2;
                        default:
                            return 1;
                    }
                }
            });
        }
    }
}
