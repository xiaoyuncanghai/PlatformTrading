package com.pt.platformtrading_location;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.snackbar.Snackbar;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.util.Utils;
import com.pt.platformtrading_location.delegate.MainActDelegate;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.lang.reflect.Field;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@Route(path = ARouterPath.MAIN_PATH)
public class MainActivity extends ActivityPresenter<MainActDelegate> {

    @Override
    protected Class<MainActDelegate> getDelegateClass() {
        return MainActDelegate.class;
    }

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //请求权限
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            if (Utils.externalStorageExist()) {
                                File files_flsd = new File(Utils.getExternalStoragePath());
                                if (!files_flsd.exists()) {
                                    files_flsd.mkdir();
                                }

                                File apk_flsd = new File(Utils.getExternalStoragePath(), "apk_download");
                                if (!apk_flsd.exists()) {
                                    apk_flsd.mkdir();
                                }

                                File db_flsd = new File(Utils.getExternalStoragePath(), "db");
                                if (!db_flsd.exists()) {
                                    db_flsd.mkdir();
                                }

                                File res_flsd = new File(Utils.getExternalStoragePath(), "res");
                                if (!res_flsd.exists()) {
                                    res_flsd.mkdir();
                                }
                            } else {
                                LogUtil.e("无SD卡");
                                return;
                            }
                        } else {
                            Snackbar.make(viewDelegate.getRootView(), R.string.permission_request_denied_result, Snackbar.LENGTH_LONG).show();
                        }

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(MainActivity.this);
                            boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
                            if (!areNotificationsEnabled) {
                                //  去开启通知栏
                                showOpenNoticeDialog();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void showOpenNoticeDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage(R.string.open_notify);
        //监听下方button点击事件
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(localIntent);
            }
        });
        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
        try {
            Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
            mAlert.setAccessible(true);
            Object mAlertController = mAlert.get(dialog);
            Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
            mTitle.setAccessible(true);
            TextView mTitleView = (TextView) mTitle.get(mAlertController);
            mTitleView.setTextSize(15f);
            Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
            mMessage.setAccessible(true);
            TextView mMessageView = (TextView) mMessage.get(mAlertController);
            mMessageView.setTextSize(14f);
            mMessageView.setTextColor(Color.parseColor("#666666"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#999999"));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(14f);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#0080ff"));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(14f);
    }
}
/*if (Build.VERSION.SDK_INT >= 23 &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
                        ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
                    },
                    Constant.RESQUEST_LOCATION_CODE
            );
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            this.startActivity(intent);
        }*/