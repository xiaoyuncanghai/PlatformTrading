package com.pt.platformtrading_location;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.logutils.LogUtils;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.baidu.location.PoiRegion;
import com.google.android.material.snackbar.Snackbar;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.util.Utils;
import com.pt.platformtrading_location.delegate.MainActDelegate;
import com.pt.platformtrading_location.service.LocationService;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

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

        getPersimmions();
        //开始定位
        startLocation();
    }

    private LocationService locationService;
    private void startLocation() {
        LocationService locationService = ((MainApplication) getApplication()).locationService;
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
    }

    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        /**
         * 定位请求回调函数
         * @param location 定位结果
         */
        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {

                LogUtils.d("current type = "+location.getLocType());
                LogUtils.d("current city = "+location.getCity());
                LogUtils.d("current citycode = "+location.getCityCode());
                LogUtils.d("current Ad = "+location.getAdCode());
                LogUtils.d("current Country = "+location.getCountryCode());
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
            super.onConnectHotSpotMessage(s, i);
        }

        /**
         * 回调定位诊断信息，开发者可以根据相关信息解决定位遇到的一些问题
         * @param locType 当前定位类型
         * @param diagnosticType 诊断类型（1~9）
         * @param diagnosticMessage 具体的诊断信息释义
         */
        @Override
        public void onLocDiagnosticMessage(int locType, int diagnosticType, String diagnosticMessage) {
            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage);
            LogUtils.d("locType = "+locType);
            if (locType == BDLocation.TypeNetWorkLocation) {
                if (diagnosticType == 1) {
                    //sb.append("网络定位成功，没有开启GPS，建议打开GPS会更好");
                } else if (diagnosticType == 2) {
                    //sb.append("网络定位成功，没有开启Wi-Fi，建议打开Wi-Fi会更好");
                }
            } else if (locType == BDLocation.TypeOffLineLocationFail) {
                if (diagnosticType == 3) {
                    //sb.append("定位失败，请您检查您的网络状态");
                }
            } else if (locType == BDLocation.TypeCriteriaException) {
                if (diagnosticType == 4) {
                    //sb.append("定位失败，无法获取任何有效定位依据");

                } else if (diagnosticType == 5) {
                    //sb.append("定位失败，无法获取有效定位依据，请检查运营商网络或者Wi-Fi网络是否正常开启，尝试重新请求定位");

                } else if (diagnosticType == 6) {
                    //sb.append("定位失败，无法获取有效定位依据，请尝试插入一张sim卡或打开Wi-Fi重试");
                } else if (diagnosticType == 7) {
                    //sb.append("定位失败，飞行模式下无法获取有效定位依据，请关闭飞行模式重试");
                } else if (diagnosticType == 9) {
                    //sb.append("定位失败，无法获取任何有效定位依据");
                }
            } else if (locType == BDLocation.TypeServerError) {
                if (diagnosticType == 8) {
                    //sb.append("定位失败，请确认您定位的开关打开状态，是否赋予APP定位权限");
                }
            }
        }
    };

    private String permissionInfo;
    private final int SDK_PERMISSION_REQUEST = 127;
    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
             */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        // Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS); => 到设置里面去
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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