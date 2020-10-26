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
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.base.BaseApplication;
import com.pt.lib_common.bean.databean.ModifyInfoDataBean;
import com.pt.lib_common.bean.jsonbean.ModifyGoodsJsonBean;
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
import com.pt.lib_common.view.citychoose.db.DBManager;
import com.pt.lib_common.view.citychoose.model.City;
import com.pt.module_mine.R;
import com.pt.module_mine.adpter.ContentAdapter;
import com.pt.module_mine.adpter.ContentItemListener;
import com.pt.module_mine.adpter.ImageModifyAdapter;
import com.pt.module_mine.bean.CategoryDatebean;
import com.pt.module_mine.bean.ImageDataBean;
import com.pt.module_mine.bean.ModifyRequestBean;
import com.pt.module_mine.bean.json.CategoryJsonBean;
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

public class GoodsModifyActivityDelegate extends AppDelegate {

    private EditText et_modify_goods_title;
    private EditText et_modify_goods_content;
    private RecyclerView rcv_modify_goods_image;
    private TextView modify_goods_cate;
    private EditText modify_goods_price;
    private TextView modify_goods_location;
    private TextView tv_modify_goods_upload;
    private String id;
    private ModifyInfoDataBean info;
    private ImageModifyAdapter adapter;
    private OssService mService;
    private int MAX_PIC_NUM = 3;
    private String cityCode = BaseApplication.getInstance().getCity().getCityCode();
    private ArrayList<CategoryDatebean> categoryList = new ArrayList<>();
    private String chooseCategory;
    private ListDialog listDialog;
    private ArrayList<ImageDataBean> imageDataBeans = new ArrayList<>();
    private ArrayList<ImageDataBean> imageDataBeanLocals = new ArrayList<>();
    private ConstraintLayout loading_coo;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_goods_modify;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        et_modify_goods_title = get(R.id.et_modify_goods_title);
        et_modify_goods_content = get(R.id.et_modify_goods_content);
        rcv_modify_goods_image = get(R.id.rcv_modify_goods_image);
        modify_goods_cate = get(R.id.modify_goods_cate);
        modify_goods_price = get(R.id.modify_goods_price);
        modify_goods_location = get(R.id.modify_goods_location);
        tv_modify_goods_upload = get(R.id.tv_modify_goods_upload);
        loading_coo = get(R.id.loading_coo);
        id = getActivity().getIntent().getStringExtra(Constant.KEY_GOODS_ID);
        adapter = new ImageModifyAdapter(getActivity(), imageDataBeans);
        rcv_modify_goods_image.setLayoutManager(new GridLayoutManager(this.getActivity(), 3,
                GridLayoutManager.VERTICAL, false));
        rcv_modify_goods_image.setAdapter(adapter);
        mService = initOSS(Config.OSS_ENDPOINT);
        mService.setCallbackAddress(Config.OSS_CALLBACK_URL);
        info = getDataFromIntent();
        initViewData();
        requestCategroy();
        initClickEvent();
    }

    /**
     * 点击事件
     */
    private void initClickEvent() {
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.iv_close) {
                imageDataBeans.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (adapter.getItemViewType(position) == ImageDataBean.NO_IMAGE) {
                //添加图片
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
                                    Toast.makeText(getActivity(), R.string.permission_request_denied,
                                            Toast.LENGTH_LONG)
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

        modify_goods_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPicker.from(getActivity())
                        .enableAnimation(false)
                        .setAnimationStyle(R.style.DefaultCityPickerAnimation)
                        .setLocatedCity(null)
                        .setHotCities(null)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                cityCode = data.getCode();
                                modify_goods_location.setText(data.getName());
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

        modify_goods_cate.setOnClickListener(v -> {
            ContentAdapter adapter = new ContentAdapter(getActivity(), categoryList, new ContentItemListener() {
                @Override
                public void onItemClick(View view, int position) {
                    listDialog.dismiss();
                    modify_goods_cate.setText(categoryList.get(position).getCategoryName());
                    chooseCategory = categoryList.get(position).getCategoryId();
                    if (et_modify_goods_title.getText().toString().length() > 0
                            && et_modify_goods_content.getText().toString().length() > 0
                            && !"".equals(chooseCategory)
                            && modify_goods_price.getText().toString().length() > 0) {
                        tv_modify_goods_upload.setEnabled(true);
                    } else {
                        tv_modify_goods_upload.setEnabled(false);
                    }
                }
            });
            listDialog = new ListDialog(getActivity(), R.style.MyDialog, "", adapter);
            listDialog.show();
        });

        et_modify_goods_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_modify_goods_title.getText().toString().length() > 0
                        && et_modify_goods_content.getText().toString().length() > 0
                        && !("").equals(chooseCategory)
                        && modify_goods_price.getText().toString().length() > 0) {
                    tv_modify_goods_upload.setEnabled(true);
                } else {
                    tv_modify_goods_upload.setEnabled(false);
                }
            }
        });

        et_modify_goods_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_modify_goods_title.getText().toString().length() > 0
                        && et_modify_goods_content.getText().toString().length() > 0
                        && !"".equals(chooseCategory)
                        && modify_goods_price.getText().toString().length() > 0) {
                    tv_modify_goods_upload.setEnabled(true);
                } else {
                    tv_modify_goods_upload.setEnabled(false);
                }
            }
        });

        modify_goods_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_modify_goods_title.getText().toString().length() > 0
                        && et_modify_goods_content.getText().toString().length() > 0
                        && !("").equals(chooseCategory)
                        && modify_goods_price.getText().toString().length() > 0) {
                    tv_modify_goods_upload.setEnabled(true);
                } else {
                    tv_modify_goods_upload.setEnabled(false);
                }
            }
        });

        tv_modify_goods_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading_coo.setVisibility(View.VISIBLE);
                if (imageDataBeanLocals != null && imageDataBeanLocals.size() > 0) {
                    for (int i = 0; i < imageDataBeanLocals.size(); i++) {
                        String picturePath = imageDataBeanLocals.get(i).getImagePath();
                        String pictureName = imageDataBeanLocals.get(i).getImageName();
                        mService.asyncPutImage(pictureName, "pic/", picturePath, i, new OssService.OnUploadListener() {
                            @Override
                            public void onProgress(int position, long currentSize, long totalSize) {
                                int progress = (int) (100 * currentSize / totalSize);
                            }

                            @Override
                            public void onSuccess(int position, String imageUrl) {
                                Snackbar.make(getRootView(), "图片上传成功", Snackbar.LENGTH_SHORT).show();
                                if (position == imageDataBeanLocals.size() - 1) {
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
                    requestCreateGoods();
                }
            }
        });
    }

    private void requestCreateGoods() {
        ModifyRequestBean request = new ModifyRequestBean();
        request.setCateId1(chooseCategory);
        request.setCityCode(cityCode);
        request.setDescription(et_modify_goods_content.getText().toString());
        request.setId(id);
        if (imageDataBeanLocals != null && imageDataBeanLocals.size() > 0) {
            switch (imageDataBeanLocals.size()) {
                case 1:
                    request.setPic1(imageDataBeanLocals.get(0).getImageName());
                    break;
                case 2:
                    request.setPic1(imageDataBeanLocals.get(0).getImageName());
                    request.setPic2(imageDataBeanLocals.get(1).getImageName());
                    break;
                case 3:
                    request.setPic1(imageDataBeanLocals.get(0).getImageName());
                    request.setPic2(imageDataBeanLocals.get(1).getImageName());
                    request.setPic3(imageDataBeanLocals.get(2).getImageName());
                    break;
            }
        }
        request.setPrice(modify_goods_price.getText().toString());
        request.setTitle(et_modify_goods_title.getText().toString());
        EasyHttp.post(HttpConstant.API_UPDATE_GOODS).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(request)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        loading_coo.setVisibility(View.GONE);
                        Snackbar.make(getRootView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String s) {
                        loading_coo.setVisibility(View.GONE);
                        ModifyGoodsJsonBean jsonBean = new Gson().fromJson(s, ModifyGoodsJsonBean.class);
                        if (jsonBean.getCode() == 0) {
                            Snackbar.make(getRootView(), "修改商品成功", Snackbar.LENGTH_SHORT).show();
                            getActivity().setResult(getActivity().RESULT_OK);
                            getActivity().finish();
                        } else if (jsonBean.getCode() == 401) {
                            Snackbar.make(getRootView(), "登录已经过期, 请重新登录", Snackbar.LENGTH_SHORT).show();
                            SPHelper.putString("token", "", true);
                            SPHelper.putString("phone", "", true);
                            ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                            getActivity().finish();
                        } else if (jsonBean.getCode() == 500) {
                            Snackbar.make(getRootView(), "修改商品失败", Snackbar.LENGTH_SHORT).show();
                        }
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
                .maxSelectable(MAX_PIC_NUM - imageDataBeans.size())
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
                .forResult(Constant.KEY_REQUEST_CODE_CHOOSE_PHOTO);
    }

    /**
     * 拿到整体数据
     *
     * @return: ModifyInfoDataBean数据对象
     */
    private ModifyInfoDataBean getDataFromIntent() {
        Bundle bundle = getActivity().getIntent().getBundleExtra(Constant.KEY_MODIFY_INFO_SERIALIZABLE);
        ModifyInfoDataBean infoDataBean = (ModifyInfoDataBean) bundle.getSerializable(Constant.KEY_MODIFY_INFO);
        return infoDataBean;
    }

    /**
     * 初始化各种控件
     */
    private void initViewData() {
        et_modify_goods_title.setText(info.getTitle());
        et_modify_goods_content.setText(info.getDescription());
        chooseCategory = info.getCateId();
        modify_goods_cate.setText(info.getCategory());
        modify_goods_price.setText(info.getPrice());
        cityCode = info.getLocation();
        if (!"".equals(info.getLocation())) {
            modify_goods_location.setText(new DBManager(getActivity()).searchCityByCode(info.getLocation()).getName());
        }
        String pic1Url = info.getPic1Url();
        if (pic1Url != null && !"".equals(pic1Url)) {
            ImageDataBean imageDataBean = new ImageDataBean(ImageDataBean.IMAGE_FROM_INTENT);
            imageDataBean.setImageUrl(pic1Url);
            imageDataBeans.add(imageDataBean);
        }
        String pic2Url = info.getPic2Url();
        if (pic2Url != null && !"".equals(pic2Url)) {
            ImageDataBean imageDataBean2 = new ImageDataBean(ImageDataBean.IMAGE_FROM_INTENT);
            imageDataBean2.setImageUrl(pic2Url);
            imageDataBeans.add(imageDataBean2);
        }
        String pic3Url = info.getPic3Url();
        if (pic3Url != null && !"".equals(pic3Url)) {
            ImageDataBean imageDataBean = new ImageDataBean(ImageDataBean.IMAGE_FROM_INTENT);
            imageDataBean.setImageUrl(pic3Url);
            imageDataBeans.add(imageDataBean);
        }
        adapter.notifyDataSetChanged();
        if (et_modify_goods_title.getText().toString().length() > 0
                && et_modify_goods_content.getText().toString().length() > 0
                && !("").equals(info.getCateId())
                && modify_goods_price.getText().toString().length() > 0) {
            tv_modify_goods_upload.setEnabled(true);
        } else {
            tv_modify_goods_upload.setEnabled(false);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.KEY_REQUEST_CODE_CHOOSE_PHOTO && resultCode == getActivity().RESULT_OK) {
            imageDataBeanLocals.clear();
            if (Matisse.obtainResult(data) != null && Matisse.obtainResult(data).size() > 0) {
                List<Uri> UriList = Matisse.obtainResult(data);
                List<String> pathList = Matisse.obtainPathResult(data);
                for (int i = 0; i < UriList.size(); i++) {
                    ImageDataBean bean = new ImageDataBean(ImageDataBean.IMAGE_FROM_LOCAL);
                    bean.setImageUri(UriList.get(i));
                    bean.setImagePath(pathList.get(i));
                    bean.setImageName(pathList.get(i).replaceAll("/", ""));
                    imageDataBeanLocals.add(bean);
                }
                imageDataBeans.addAll(imageDataBeanLocals);
                adapter.notifyDataSetChanged();
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
}
