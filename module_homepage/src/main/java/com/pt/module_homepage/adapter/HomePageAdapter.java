package com.pt.module_homepage.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
                banner.setAutoPlayAble(item.getBannerItemList().size() > 1);
                banner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(context)
                                .load(((BannerItemDataBean) model).getXBannerUrl())
                                .placeholder(R.drawable.banner_placeholder)
                                .error(R.drawable.banner_placeholder)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into((ImageView) view);
                    }
                });
                banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                    @Override
                    public void onItemClick(XBanner banner, Object model, View view, int position) {
                        //Toast.makeText(context, "点击了第" + position + "图片", Toast.LENGTH_SHORT).show();
                        if (!((BannerItemDataBean) model).getLinkUrl().equals("")
                                && (((BannerItemDataBean) model).getLinkUrl().startsWith("http") ||
                                ((BannerItemDataBean) model).getLinkUrl().startsWith("https")
                                || ((BannerItemDataBean) model).getLinkUrl().startsWith("file"))) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            Uri content_url = Uri.parse(((BannerItemDataBean) model).getLinkUrl());
                            intent.setData(content_url);
                            context.startActivity(intent);
                        }
                    }
                });
                break;

            case HomePageDataBean.TYPE_HOME_PAGE_CATEGORY:
                ImageView home_cate_iv = helper.getView(R.id.home_cate_iv);
                TextView home_cate_tv = helper.getView(R.id.home_cate_tv);
                Glide.with(context).load(item.getCate_icon())
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .placeholder(R.drawable.ic_common_fb)
                        .error(R.drawable.ic_common_fb).into(home_cate_iv);
                home_cate_tv.setText(item.getCate_name());
                break;

            case HomePageDataBean.TYPE_HOME_PAGE_PROMOTE:
                ImageView homepage_promote_iv = helper.getView(R.id.homepage_promote_iv);
                ViewGroup.LayoutParams lp = homepage_promote_iv.getLayoutParams();
                lp.height = lp.width;
                homepage_promote_iv.setLayoutParams(lp);
                TextView homepage_promote_tv_title = helper.getView(R.id.homepage_promote_tv_title);
                TextView homepage_promote_tv_price = helper.getView(R.id.homepage_promote_tv_price);
                TextView homepage_promote_tv_description = helper.getView(R.id.homepage_promote_tv_description);
                homepage_promote_tv_title.setText(item.getPromote_title());
                homepage_promote_tv_price.setText(item.getPromote_price());
                homepage_promote_tv_description.setText(item.getPromote_description());
                Glide.with(context).load(item.getPromote_pic())
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                        .placeholder(R.drawable.ic_common_happy_yuu1_cat1)
                        .error(R.drawable.ic_common_happy_yuu1_cat1).into(homepage_promote_iv);
                helper.addOnClickListener(R.id.homepage_promote_tv_see);
                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case HomePageDataBean.TYPE_HOME_PAGE_BANNER:
                        case HomePageDataBean.TYPE_HOME_PAGE_PROMOTE:
                            return 4;
                        case HomePageDataBean.TYPE_HOME_PAGE_CATEGORY:
                            return 1;
                        default:
                            return 0;
                    }
                }
            });
        }
    }
}
