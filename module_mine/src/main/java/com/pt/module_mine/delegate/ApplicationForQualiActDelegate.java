package com.pt.module_mine.delegate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.GifSizeFilter;
import com.pt.lib_common.util.SPHelper;
import com.pt.module_mine.R;
import com.pt.module_mine.bean.ImageBean;
import com.pt.module_mine.bean.json.ApplySaleJsonBean;
import com.pt.module_mine.bean.request.ApplySaleRequestBean;
import com.pt.module_mine.oss.Config;
import com.pt.module_mine.oss.service.OssService;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xw.repo.XEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 申请资格
 */
public class ApplicationForQualiActDelegate extends AppDelegate {

    private ImageView upload_business_license;
    private XEditText et_input_company_name;
    private XEditText et_input_company_code;
    private TextView tv_upload_application_quality;
    private ImageView show_business_license;
    private static final int REQUEST_CODE_CHOOSE = 26;
    private String imageName = "";
    private OssService mService;
    private ArrayList<ImageBean> imageBeans = new ArrayList<>();

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_application_quality;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        upload_business_license = get(R.id.upload_business_license);
        et_input_company_name = get(R.id.et_input_company_name);
        et_input_company_code = get(R.id.et_input_company_code);
        show_business_license = get(R.id.show_business_license);
        tv_upload_application_quality = get(R.id.tv_upload_application_quality);
        mService = initOSS(Config.OSS_ENDPOINT);
        mService.setCallbackAddress(Config.OSS_CALLBACK_URL);

        upload_business_license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                            Toast.LENGTH_LONG).show();
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

        et_input_company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_input_company_name.getText().toString().length() > 0
                        && et_input_company_code.getText().toString().length() > 0
                        && !imageName.equals("")) {
                    tv_upload_application_quality.setEnabled(true);
                } else {
                    tv_upload_application_quality.setEnabled(false);
                }
            }
        });

        et_input_company_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_input_company_name.getText().toString().length() > 0
                        && et_input_company_code.getText().toString().length() > 0
                        && !imageName.equals("")) {
                    tv_upload_application_quality.setEnabled(true);
                } else {
                    tv_upload_application_quality.setEnabled(false);
                }
            }
        });

        tv_upload_application_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_upload_application_quality.setEnabled(false);
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
                                    //最后一张图片也上传成功
                                    requestPressie();
                                }
                            }

                            @Override
                            public void onFailure(int position) {
                                tv_upload_application_quality.setEnabled(true);
                            }
                        });
                    }
                }/* else {
                    //没有照片直接请求网络  没有照片都上传不了
                    requestPressie();
                }*/
            }
        });
    }

    private void requestPressie() {
        ApplySaleRequestBean requestBean = new ApplySaleRequestBean();
        requestBean.setBusiLicPic(imageName);
        requestBean.setCompany(et_input_company_name.getText().toString());
        requestBean.setUscc(et_input_company_code.getText().toString());
        //表示是企业的
        requestBean.setUserQuality(1);
        EasyHttp.post(HttpConstant.API_APPAY_SALE).headers("Content-Type", "application/json")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(requestBean).execute(new SimpleCallBack<String>() {
            @Override
            public void onError(ApiException e) {
                tv_upload_application_quality.setEnabled(true);
                Snackbar.make(getRootView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String s) {
                tv_upload_application_quality.setEnabled(true);
                ApplySaleJsonBean jsonBean = new Gson().fromJson(s, ApplySaleJsonBean.class);
                if (jsonBean.getCode() == 0) {
                    Snackbar.make(getRootView(), "资料提交成功, 请稍后",
                            Snackbar.LENGTH_SHORT).show();
                    getActivity().finish();
                } else if (jsonBean.getCode() == 401) {
                    //accesstoekn过期
                    Snackbar.make(getRootView(), "登录已经过期, 请重新登录", Snackbar.LENGTH_SHORT).show();
                    SPHelper.putString("token", "", true);
                    SPHelper.putString("phone", "", true);
                    ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                    getActivity().finish();
                } else  {
                    Snackbar.make(getRootView(), "资料提交失败, 请重新输入",
                            Snackbar.LENGTH_SHORT).show();
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
                .maxSelectable(1)
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
       /* Matisse.from(getActivity())
                .choose(MimeType.ofImage())
                .theme(R.style.Matisse_Dracula)
                .countable(false)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .maxSelectable(1)
                .originalEnable(true)
                .maxOriginalSize(10)
                .imageEngine(new PicassoEngine())
                .forResult(REQUEST_CODE_CHOOSE);*/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == getActivity().RESULT_OK) {
            imageBeans.clear();
            show_business_license.setVisibility(View.VISIBLE);
            upload_business_license.setVisibility(View.GONE);
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
            LogUtils.d("image size = "+imageBeans.size());
            String path = imageBeans.get(0).getImagePath();
            imageName = imageBeans.get(0).getImageName();
            try {
                Bitmap bitmap = autoResizeFromLocalFile(path);
                show_business_license.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (et_input_company_name.getText().toString().length() > 0
                    && et_input_company_code.getText().toString().length() > 0
                    && !imageName.equals("")) {
                tv_upload_application_quality.setEnabled(true);
            } else {
                tv_upload_application_quality.setEnabled(false);
            }
        }
    }

    public Bitmap autoResizeFromLocalFile(String picturePath) throws IOException {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, options);

        options.inSampleSize = calculateInSampleSize(options, upload_business_license.getWidth(),
                upload_business_license.getHeight());
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(picturePath, options);
    }

    //计算图片缩放比例
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
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
