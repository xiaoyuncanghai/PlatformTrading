package com.pt.module_mine.delegate;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.pt.lib_common.adapter.ItemSelectDialogAdapter;
import com.pt.lib_common.bean.ItemSelectDialogModel;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.DialogHelp;
import com.pt.lib_common.util.GifSizeFilter;
import com.pt.lib_common.util.PhotoUtils;
import com.pt.lib_common.util.Utils;
import com.pt.module_mine.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xw.repo.XEditText;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;

import java.io.File;
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

        img_publish_sale_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击拍照或者是从相册库里面选择
                showImageOpDialog();
            }
        });
    }

    private List<ItemSelectDialogModel> itemSelectDataList = new ArrayList<>();
    private String[] itemSelectTitle = new String[]{"拍照", "从相册中选择", "取消"};
    private File fileUri = new File(Utils.getExternalStoragePath() + "/photo.jpg");
    private File fileCropUri = new File(Utils.getExternalStoragePath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;

    private void showImageOpDialog() {
        DialogHelp.getInstance().makeItemSelectDialog(this.getActivity(), true);
        RecyclerView rv_select = DialogHelp.getInstance().contentView.findViewById(R.id.rv_select);
        rv_select.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rv_select.setItemAnimator(new DefaultItemAnimator());
        ItemSelectDialogAdapter itemSelectDialogAdapter = new ItemSelectDialogAdapter(itemSelectDataList);
        itemSelectDialogAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        rv_select.setAdapter(itemSelectDialogAdapter);
        itemSelectDialogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (adapter.getItemViewType(position)) {
                    case ItemSelectDialogModel.TYPE_CANCEL:
                        DialogHelp.getInstance().dismissDialog();
                        break;
                    case ItemSelectDialogModel.TYPE_SELECT:
                        switch (position) {
                            case 0:
                                //拍照
                                RxPermissions rxPermissions = new RxPermissions(getActivity());
                                rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE)
                                        .subscribe(new Observer<Boolean>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {

                                            }

                                            @Override
                                            public void onNext(Boolean aBoolean) {
                                                if (aBoolean) {
                                                    if (Utils.externalStorageExist()) {
                                                        imageUri = Uri.fromFile(fileUri);
                                                        //通过FileProvider创建一个content类型的Uri
                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                                            imageUri = FileProvider.getUriForFile(getActivity(),
                                                                    "com.pt.platformtrading_location", fileUri);
                                                        }
                                                        PhotoUtils.takePicture(getActivity(), imageUri, Constant.REQUEST_CODE_CAMERA);
                                                    } else {
                                                        Snackbar.make(getRootView() ,"该设备没有SD卡",Snackbar.LENGTH_LONG).show();
                                                    }
                                                    /*Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
                                                            new File(Environment.getExternalStorageDirectory(), imgName)));
                                                    startActivityForResult(cameraIntent, CAMERA_RESULT_CODE);*/
                                                } else {
                                                    Snackbar.make(getRootView(), R.string.permission_request_denied,Snackbar.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onComplete() {
                                                DialogHelp.getInstance().dismissDialog();
                                            }
                                        });
                                break;
                            case 1:
                                //相册
                                break;
                        }
                        break;
                }
            }
        });

        itemSelectDataList.clear();
        ItemSelectDialogModel itemSelectDialogModel1 = new ItemSelectDialogModel(1);
        itemSelectDialogModel1.setItemText(itemSelectTitle[0]);
        itemSelectDataList.add(itemSelectDialogModel1);

        ItemSelectDialogModel itemSelectDialogModel2 = new ItemSelectDialogModel(1);
        itemSelectDialogModel2.setItemText(itemSelectTitle[1]);
        itemSelectDataList.add(itemSelectDialogModel2);

        ItemSelectDialogModel itemSelectDialogModel = new ItemSelectDialogModel(2);
        itemSelectDialogModel.setItemText(itemSelectTitle[2]);
        itemSelectDataList.add(itemSelectDialogModel);
        itemSelectDialogAdapter.notifyDataSetChanged();
    }

    /*private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;
    public void onActivityResult(int requestCode , int resultCode , Intent intent){
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case Constant.REQUEST_CODE_CAMERA:
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(getActivity(), imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, Constant.REQUEST_CODE_CROP);
                    break;
                //访问相册完成回调
                case Constant.REQUEST_CODE_GALLERY:
                    if (Utils.externalStorageExist()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(getActivity(), Matisse.obtainResult(intent).get(0)));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            newUri = FileProvider.getUriForFile(this.getActivity(), "com.yuu1.fulisudi", new File(newUri.getPath()));
                        }
                        PhotoUtils.cropImageUri(getActivity(), newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, Constant.REQUEST_CODE_CROP);
                    } else {
                        Snackbar.make(refreshLayout ,"该设备没有SD卡",Snackbar.LENGTH_LONG).show();
                    }
                    break;
                case Constant.REQUEST_CODE_CROP:
                    //  裁剪

                    *//*Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        showImages(bitmap);

                    }*//*
                    requetsUploadAvatar(fileCropUri);
                    break;
                default:
            }
        }
    }
*/
}
