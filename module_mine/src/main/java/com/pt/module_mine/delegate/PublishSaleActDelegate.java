package com.pt.module_mine.delegate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.GifSizeFilter;
import com.pt.module_mine.R;
import com.pt.module_mine.adpter.ImageChooseAdapter;
import com.pt.module_mine.bean.ImageBean;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xw.repo.XEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PublishSaleActDelegate extends AppDelegate {

    private EditText et_publish_sale_content;
    private RecyclerView rcv_publish_sale_image;
    private XEditText publish_sale_price;
    private XEditText publish_sale_location;
    private ImageView img_publish_sale_upload;
    private TextView tv_publish_sale_upload;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private ArrayList<ImageBean> imageBeans = new ArrayList<>();
    private ArrayList<ImageBean> imageBeansTemp = new ArrayList<>();
    private ImageChooseAdapter adapter;

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

        rcv_publish_sale_image.setLayoutManager(new GridLayoutManager(this.getActivity(), 3,
                GridLayoutManager.VERTICAL, false));
        //mAdapter = new UriAdapter();
        adapter = new ImageChooseAdapter(getActivity(), R.layout.publish_sale_image_item, imageBeans);
        rcv_publish_sale_image.setAdapter(adapter);

        img_publish_sale_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(PublishSaleActDelegate.this.getActivity());
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(
                        new Observer<Boolean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {
                                    startAction();
                                } else {
                                    Toast.makeText(PublishSaleActDelegate.this.getActivity(), R.string.permission_request_denied, Toast.LENGTH_LONG)
                                            .show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                );
            }
        });
    }

    private void startAction() {
        Matisse.from(PublishSaleActDelegate.this.getActivity())
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.pt.platformtrading_location.fileprovider", "test"))
                .maxSelectable(3)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getActivity().getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .setOnSelectedListener((uriList, pathList) -> {
                    Log.e("onSelected", "onSelected: pathList=" + pathList);
                })
                .showSingleMediaType(true)
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(isChecked -> {
                    Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                })
                .forResult(REQUEST_CODE_CHOOSE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            imageBeans.clear();
            if (Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                for (Uri s : Matisse.obtainResult(data)) {
                    ImageBean bean = new ImageBean();
                    bean.setImageUri(s);
                    imageBeans.add(bean);
                }
            }
            adapter.notifyDataSetChanged();
            if (imageBeans.size() > 2) {
                img_publish_sale_upload.setVisibility(View.GONE);
            } else {
                img_publish_sale_upload.setVisibility(View.VISIBLE);
            }
        }
    }
}
