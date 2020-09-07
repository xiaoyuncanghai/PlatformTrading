package com.pt.module_mine.delegate;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.apkfuns.logutils.LogUtils;
import com.google.android.material.snackbar.Snackbar;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.GifSizeFilter;
import com.pt.module_mine.R;
import com.pt.module_mine.adpter.ImageChooseAdapter;
import com.pt.module_mine.bean.ImageBean;
import com.pt.module_mine.oss.Config;
import com.pt.module_mine.oss.service.OssService;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xw.repo.XEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;

import java.util.ArrayList;
import java.util.List;

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
    private ImageChooseAdapter adapter;
    //OSS 相关
    private OssService mService;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private ConstraintLayout loading_coo;
    private LinearLayout ll_content;

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
        loading_coo = get(R.id.loading_coo);
        ll_content = get(R.id.ll_content);

        rcv_publish_sale_image.setLayoutManager(new GridLayoutManager(this.getActivity(), 3,
                GridLayoutManager.VERTICAL, false));
        adapter = new ImageChooseAdapter(getActivity(), R.layout.publish_sale_image_item, imageBeans);
        rcv_publish_sale_image.setAdapter(adapter);
        mService = initOSS(Config.OSS_ENDPOINT);
        mService.setCallbackAddress(Config.OSS_CALLBACK_URL);
        initClickEvent();
    }

    private void initClickEvent() {
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
        tv_publish_sale_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBeans != null && imageBeans.size() > 0) {
                    for (int i = 0; i < imageBeans.size(); i++) {
                        String picturePath = imageBeans.get(i).getImagePath();
                        String pictureName = imageBeans.get(i).getImageName();
                        mService.asyncPutImage(pictureName, "pic/", picturePath, i, new OssService.OnUploadListener() {
                            @Override
                            public void onProgress(int position, long currentSize, long totalSize) {
                                // LogUtils.d("Lion, position = " + position + "  currentSize = " + currentSize);
                                int progress = (int) (100 * currentSize / totalSize);
                                LogUtils.d("Lion. progress = " + progress);
                                //showProgressDialog(position, progress);

                            }

                            @Override
                            public void onSuccess(int position, String imageUrl) {
                                LogUtils.d("Lion, position = " + position + " imageUrl = " + imageUrl);
                                Snackbar.make(getRootView(), "图片上传成功", Snackbar.LENGTH_SHORT).show();
                                ll_content.setVisibility(View.GONE);
                                loading_coo.setVisibility(View.VISIBLE);
                                //开始请求接口
                            }

                            @Override
                            public void onFailure(int position) {
                                LogUtils.d("Lion, position = " + position);
                            }
                        });
                    }
                }
            }
        });
    }

    private void showProgressDialog(int position, int progress) {
        builder = new  AlertDialog.Builder(getActivity());
        View v = View.inflate(getActivity(), R.layout.dialog_loading, null);
        builder.setView(v);
        if (dialog == null) {
            dialog = builder.create();
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
        if (progress == 100) {
            dialog.dismiss();
        }
    }

    private void startAction() {
        /*Matisse.from(PublishSaleActDelegate.this.getActivity())
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
                .forResult(REQUEST_CODE_CHOOSE);*/

        Matisse.from(getActivity())
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .maxSelectable(3)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(new PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            imageBeans.clear();
            if (Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                List<Uri> UriList = Matisse.obtainResult(data);
                List<String> pathList = Matisse.obtainPathResult(data);
                for (int i = 0; i < UriList.size(); i++) {
                    ImageBean bean = new ImageBean();
                    bean.setImageUri(UriList.get(i));
                    bean.setImagePath(pathList.get(i));
                    bean.setImageName(pathList.get(i).replaceAll("/", ""));
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

    public OssService initOSS(String endpoint) {
        //使用自己的获取STSToken的类
        String stsServer = Config.STS_SERVER_URL;
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(stsServer);
        String editBucketName = Config.BUCKET_NAME;
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getActivity(), endpoint, credentialProvider, conf);
        OSSLog.enableLog();
        return new OssService(oss, editBucketName);
    }
}
