package com.pt.module_mine.delegate;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.imagepipeline.core.ImagePipeline;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.module_mine.R;
import com.xw.repo.XEditText;

public class PublishGoodsActDelegate extends AppDelegate {

    private TextView et_publish_goods_title;
    private TextView et_publish_goods_content;
    private RecyclerView rcv_publish_goods_image;
    private ImageView img_publish_goods_upload;
    private TextView publish_goods_cate;
    private XEditText publish_goods_price;
    private TextView publish_goods_location;
    private TextView tv_publish_goods_upload;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_publish_goods;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        et_publish_goods_title = get(R.id.et_publish_goods_title);
        et_publish_goods_content = get(R.id.et_publish_goods_content);
        rcv_publish_goods_image = get(R.id.rcv_publish_goods_image);
        img_publish_goods_upload = get(R.id.img_publish_goods_upload);
        publish_goods_cate = get(R.id.publish_goods_cate);
        publish_goods_price = get(R.id.publish_goods_price);
        publish_goods_location = get(R.id.publish_goods_location);
        tv_publish_goods_upload = get(R.id.tv_publish_goods_upload);
    }
}
