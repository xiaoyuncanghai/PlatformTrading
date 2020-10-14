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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.apkfuns.logutils.LogUtils;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.google.android.material.snackbar.Snackbar;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.base.BaseApplication;
import com.pt.lib_common.bean.CityInfo;
import com.pt.lib_common.bean.databean.FundSideItem;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.themvp.presenter.ActivityPresenter;
import com.pt.lib_common.util.Utils;
import com.pt.lib_common.view.citychoose.db.DBManager;
import com.pt.lib_common.view.citychoose.model.City;
import com.pt.platformtrading_location.delegate.MainActDelegate;
import com.pt.platformtrading_location.service.LocationService;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.LogUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.pt.lib_common.constants.Constant.CHOOSE_FUND_ITEM;
import static com.pt.lib_common.constants.Constant.KEY_ORDER_FUNDER_RESULT;

@Route(path = ARouterPath.MAIN_PATH)
public class MainActivity extends ActivityPresenter<MainActDelegate> {

    @Override
    protected Class<MainActDelegate> getDelegateClass() {
        return MainActDelegate.class;
    }

    private AlertDialog.Builder builder;
    private final int SDK_PERMISSION_REQUEST = 127;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
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
        //先检查权限, 有直接定位
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocation();
        } else {
            getPersimmions();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationService != null) {
            locationService.unregisterListener(mListener);
            locationService.stop();
        }
    }

    private LocationService locationService;
    private int start = 0;

    public void startLocation() {
        locationService = ((MainApplication) getApplication()).locationService;
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
    }

    public BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        /**
         * 定位请求回调函数
         * @param location 定位结果
         */
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                start++;
                LogUtils.d("onReceive Location");
                if (location.getCity() != null && !location.getCity().equals("")) {
                    List<City> allCities = new DBManager(MainActivity.this).getCityByProvince();
                    String cityCode = "";
                    for (City city : allCities) {
                        if (city.getName().equals(location.getCity())) {
                            cityCode = city.getCode();
                            break;
                        }
                    }
                    BaseApplication.getInstance().setCity(new CityInfo(location.getCity(), cityCode));
                    locationService.unregisterListener(mListener);
                    locationService.stop();
                    start = 0;
                    EventBus.getDefault().post(location.getCity());
                } else {
                    LogUtils.d("定位失败");
                    BaseApplication.getInstance().setCity(new CityInfo("", ""));
                }
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
        }
    };


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
        if (requestCode == SDK_PERMISSION_REQUEST) {
            if (permissions.length == 2 && grantResults.length == 2) {
                if (permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                        && permissions[1].equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        startLocation();
                    } else {
                        //TODO:可以做弹框
                        //handleNoPermission()
                    }
                }
            }
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.KEY_FROM_NEAR_REQUEST:
                if (resultCode == RESULT_OK) {
                    viewDelegate.getNearFragment().refreshDeleteData();
                }
                break;

            case Constant.KEY_FROM_HOMEPAGE_REQUEST:
                if (resultCode == RESULT_OK) {
                    viewDelegate.getHomepageFragment().refreshDeleteData();
                }
                break;

            case Constant.KEY_APPLY_MONEY_FROM_ORDER_LIST:
                if (resultCode == KEY_ORDER_FUNDER_RESULT) {
                    Bundle bundle = data.getBundleExtra(Constant.KEY_CHOOSE_FUND);
                    FundSideItem item = (FundSideItem) bundle.getSerializable(CHOOSE_FUND_ITEM);
                    viewDelegate.getOrder2Fragment().refreshData(item);
                }
                break;
        }
    }
}