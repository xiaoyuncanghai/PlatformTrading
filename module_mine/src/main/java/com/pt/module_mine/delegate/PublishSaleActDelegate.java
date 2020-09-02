package com.pt.module_mine.delegate;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.module_mine.R;
import com.xw.repo.XEditText;

public class PublishSaleActDelegate extends AppDelegate {

    private EditText et_publish_sale_content;
    private RecyclerView rcv_publish_sale_image;
    private XEditText publish_sale_price;
    private XEditText publish_sale_location;
    private ImageView img_publish_sale_upload;
    private TextView tv_publish_sale_upload;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_publish_sale;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        iniView();
    }

    private void iniView() {
        et_publish_sale_content = get(R.id.et_publish_sale_content);
        rcv_publish_sale_image = get(R.id.rcv_publish_sale_image);
        img_publish_sale_upload = get(R.id.img_publish_sale_upload);
        publish_sale_price = get(R.id.publish_sale_price);
        publish_sale_location = get(R.id.publish_sale_location);
        tv_publish_sale_upload = get(R.id.tv_publish_sale_upload);

    }
}
