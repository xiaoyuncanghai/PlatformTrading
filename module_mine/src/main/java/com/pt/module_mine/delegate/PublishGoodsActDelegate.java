package com.pt.module_mine.delegate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.apkfuns.logutils.LogUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.base.BaseApplication;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.GifSizeFilter;
import com.pt.lib_common.util.SPHelper;
import com.pt.lib_common.view.citychoose.CityPicker;
import com.pt.lib_common.view.citychoose.adapter.OnPickListener;
import com.pt.lib_common.view.citychoose.model.City;
import com.pt.module_mine.R;
import com.pt.module_mine.adpter.ContentAdapter;
import com.pt.module_mine.adpter.ContentItemListener;
import com.pt.module_mine.adpter.PhotoAdapter;
import com.pt.module_mine.bean.CategoryDatebean;
import com.pt.module_mine.bean.ImageBean;
import com.pt.module_mine.bean.json.CategoryJsonBean;
import com.pt.module_mine.bean.json.CreateGoodsJsonBean;
import com.pt.module_mine.bean.request.CreateRequestBean;
import com.pt.module_mine.dialog.ListDialog;
import com.pt.module_mine.oss.Config;
import com.pt.module_mine.oss.service.OssService;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.converter.gson.GsonConverterFactory;

public class PublishGoodsActDelegate extends AppDelegate {

    private static final int MAX_PIC_NUM = 3;
    private TextView et_publish_goods_title;
    private TextView et_publish_goods_content;
    private RecyclerView rcv_publish_goods_image;
    private TextView publish_goods_cate;
    private EditText publish_goods_price;
    private TextView publish_goods_location;
    private TextView tv_publish_goods_upload;
    private static final int REQUEST_CODE_CHOOSE = 23;
    private ArrayList<ImageBean> imageBeans = new ArrayList<>();
    private ArrayList<ImageBean> imageBeansTemp = new ArrayList<>();
    private OssService mService;
    private ArrayList<CategoryDatebean> categoryList = new ArrayList<>();
    private String cityCode = BaseApplication.getInstance().getCity().getCityCode();
    private String chooseCategory = "";
    private ListDialog listDialog;
    private PhotoAdapter photoAdapter;
    private ConstraintLayout loading_coo;

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
        publish_goods_cate = get(R.id.publish_goods_cate);
        publish_goods_price = get(R.id.publish_goods_price);
        publish_goods_location = get(R.id.publish_goods_location);
        tv_publish_goods_upload = get(R.id.tv_publish_goods_upload);
        loading_coo = get(R.id.loading_coo);

        photoAdapter = new PhotoAdapter(getActivity(), imageBeans);
        rcv_publish_goods_image.setLayoutManager(new GridLayoutManager(this.getActivity(), 3,
                GridLayoutManager.VERTICAL, false));
        rcv_publish_goods_image.setAdapter(photoAdapter);

        mService = initOSS(Config.OSS_ENDPOINT);
        mService.setCallbackAddress(Config.OSS_CALLBACK_URL);
        initClickEvent();
        requestCategroy();
    }

    private void initClickEvent() {
        photoAdapter.setOnItemClickListener(new PhotoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view.getId() == R.id.iv_close) {
                    imageBeans.remove(position);
                    photoAdapter.notifyDataSetChanged();
                }

                if (view.getId() == R.id.iv_add) {
                    RxPermissions rxPermissions = new RxPermissions(getActivity());
                    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(
                            new Observer<Boolean>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                }

                                @Override
                                public void onNext(Boolean aBoolean) {
                                    if (aBoolean) {
                                        startAction();
                                    } else {
                                        Toast.makeText(getActivity(), R.string.permission_request_denied, Toast.LENGTH_LONG)
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
            }
        });
        //启动定位
        publish_goods_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPicker.from(PublishGoodsActDelegate.this.getActivity())
                        .enableAnimation(false)
                        .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                        .setLocatedCity(null)
                        .setHotCities(null)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                cityCode = data.getCode();
                                publish_goods_location.setText(data.getName());
                            }

                            @Override
                            public void onLocate() {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                }, 3000);
                            }

                            //取消定位
                            @Override
                            public void onCancel() {

                            }
                        }).show();
            }
        });

        /*rcv_publish_goods_image.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {

                }
            }
        }));*/

        publish_goods_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentAdapter adapter = new ContentAdapter(getActivity(), categoryList, new ContentItemListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        listDialog.dismiss();
                        publish_goods_cate.setText(categoryList.get(position).getCategoryName());
                        chooseCategory = categoryList.get(position).getCategoryId();
                        if (et_publish_goods_title.getText().toString().length() > 0
                                && et_publish_goods_content.getText().toString().length() > 0
                                && !chooseCategory.equals("")
                                && publish_goods_price.getText().toString().length() > 0) {
                            tv_publish_goods_upload.setEnabled(true);
                        } else {
                            tv_publish_goods_upload.setEnabled(false);
                        }
                    }
                });
                listDialog = new ListDialog(getActivity(), R.style.MyDialog, "", adapter);
                listDialog.show();
            }
        });
        et_publish_goods_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_publish_goods_title.getText().toString().length() > 0
                        && et_publish_goods_content.getText().toString().length() > 0
                        && !chooseCategory.equals("")
                        && publish_goods_price.getText().toString().length() > 0) {
                    tv_publish_goods_upload.setEnabled(true);
                } else {
                    tv_publish_goods_upload.setEnabled(false);
                }
            }
        });
        et_publish_goods_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_publish_goods_title.getText().toString().length() > 0
                        && et_publish_goods_content.getText().toString().length() > 0
                        && !chooseCategory.equals("")
                        && publish_goods_price.getText().toString().length() > 0) {
                    tv_publish_goods_upload.setEnabled(true);
                } else {
                    tv_publish_goods_upload.setEnabled(false);
                }
            }
        });

        publish_goods_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_publish_goods_title.getText().toString().length() > 0
                        && et_publish_goods_content.getText().toString().length() > 0
                        && !chooseCategory.equals("")
                        && publish_goods_price.getText().toString().length() > 0) {
                    tv_publish_goods_upload.setEnabled(true);
                } else {
                    tv_publish_goods_upload.setEnabled(false);
                }
            }
        });

        tv_publish_goods_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading_coo.setVisibility(View.VISIBLE);
                if (imageBeans != null && imageBeans.size() > 0) {
                    for (int i = 0; i < imageBeans.size(); i++) {
                        String picturePath = imageBeans.get(i).getImagePath();
                        String pictureName = imageBeans.get(i).getImageName();
                        mService.asyncPutImage(pictureName, "pic/", picturePath, i, new OssService.OnUploadListener() {
                            @Override
                            public void onProgress(int position, long currentSize, long totalSize) {
                                int progress = (int) (100 * currentSize / totalSize);
                                LogUtils.d("Lion. progress = " + progress);
                            }

                            @Override
                            public void onSuccess(int position, String imageUrl) {
                                LogUtils.d("Lion, position = " + position + " imageUrl = " + imageUrl);
                                Snackbar.make(getRootView(), "图片上传成功", Snackbar.LENGTH_SHORT).show();
                                if (position == imageBeans.size() - 1) {
                                    requestCreateGoods();
                                }
                            }

                            @Override
                            public void onFailure(int position) {
                                loading_coo.setVisibility(View.GONE);
                            }
                        });
                    }
                } else {
                    //没有照片直接请求网络
                    requestCreateGoods();
                }
            }
        });
    }

    private void requestCreateGoods() {
        CreateRequestBean requestBean = new CreateRequestBean();
        //必选参数
        requestBean.setCateId1(chooseCategory);
        //可选参数
        requestBean.setCityCode(cityCode);
        //必选参数
        requestBean.setDescription(et_publish_goods_content.getText().toString());
        //可选参数
        requestBean.setGoodsType("2");
        //图片是可选参数
        if (imageBeans != null && imageBeans.size() > 0) {
            switch (imageBeans.size()) {
                case 1:
                    requestBean.setPic1(imageBeans.get(0).getImageName());
                    break;
                case 2:
                    requestBean.setPic1(imageBeans.get(0).getImageName());
                    requestBean.setPic2(imageBeans.get(1).getImageName());
                    break;
                case 3:
                    requestBean.setPic1(imageBeans.get(0).getImageName());
                    requestBean.setPic2(imageBeans.get(1).getImageName());
                    requestBean.setPic3(imageBeans.get(2).getImageName());
                    break;
            }
        }
        //必选参数
        requestBean.setPrice(publish_goods_price.getText().toString());
        //必选参数
        requestBean.setTitle(et_publish_goods_title.getText().toString());
        EasyHttp.post(HttpConstant.API_CREATE_GOODS).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Snackbar.make(getRootView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                        loading_coo.setVisibility(View.GONE);
                    }

                    @Override
                    public void onSuccess(String s) {
                        loading_coo.setVisibility(View.GONE);
                        CreateGoodsJsonBean jsonBean = new Gson().fromJson(s, CreateGoodsJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            Snackbar.make(getRootView(), "发布成功", Snackbar.LENGTH_SHORT).show();
                            ARouter.getInstance().build(ARouterPath.PUBLISH_STATUS).withInt(Constant.KEY_PUBLISH_STATUS, 1)
                                    .navigation();
                        } else if (jsonBean.getCode() == 401) {
                            //accesstoekn过期
                            Snackbar.make(getRootView(), "登录已经过期, 请重新登录", Snackbar.LENGTH_SHORT).show();
                            SPHelper.putString("token", "", true);
                            SPHelper.putString("phone", "", true);
                            ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                        } else {
                            Snackbar.make(getRootView(), "发布失败", Snackbar.LENGTH_SHORT).show();
                            ARouter.getInstance().build(ARouterPath.PUBLISH_STATUS).withInt(Constant.KEY_PUBLISH_STATUS, 2)
                                    .navigation();
                        }
                        getActivity().finish();
                    }
                });
    }


    private void startAction() {
        Matisse.from(getActivity())
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.pt.platformtrading_location.fileprovider", "test"))
                .maxSelectable(MAX_PIC_NUM - imageBeans.size())
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getActivity().getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .setOnSelectedListener((uriList, pathList) -> {
                })
                .showSingleMediaType(true)
                .originalEnable(true)
                .maxOriginalSize(10)
                .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(isChecked -> {
                })
                .forResult(REQUEST_CODE_CHOOSE);
        /*Matisse.from(getActivity())
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .maxSelectable(3)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(new PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE);*/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            imageBeansTemp.clear();
            if (Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                List<Uri> UriList = Matisse.obtainResult(data);
                List<String> pathList = Matisse.obtainPathResult(data);
                for (int i = 0; i < UriList.size(); i++) {
                    ImageBean bean = new ImageBean();
                    bean.setImageUri(UriList.get(i));
                    bean.setImagePath(pathList.get(i));
                    bean.setImageName(pathList.get(i).replaceAll("/", ""));
                    imageBeansTemp.add(bean);
                }
                imageBeans.addAll(imageBeansTemp);
                photoAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 请求分类信息
     */
    private void requestCategroy() {
        EasyHttp.post(HttpConstant.API_ADD_CATE_SEARCH)
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Snackbar.make(getRootView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String s) {
                        CategoryJsonBean categoryJsonBean = new Gson().fromJson(s, CategoryJsonBean.class);
                        if (categoryJsonBean.getCode() == 0) {
                            categoryList.clear();
                            if (categoryJsonBean.getData() != null && categoryJsonBean.getData().size() > 0) {
                                for (int i = 0; i < categoryJsonBean.getData().size(); i++) {
                                    CategoryDatebean categoryDatebean = new CategoryDatebean();
                                    categoryDatebean.setCategoryId(categoryJsonBean.getData().get(i).getId());
                                    categoryDatebean.setCategoryName(categoryJsonBean.getData().get(i).getName());
                                    categoryList.add(categoryDatebean);
                                }
                            }
                        }
                    }
                });
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
