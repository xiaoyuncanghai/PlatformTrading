package com.pt.lib_common.util;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.pt.lib_common.base.BaseApplication;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Utils初始化相关 </p>
 */
public class Utils {

    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 全局获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    /**
     * View获取Activity的工具
     *
     * @param view view
     * @return Activity
     */
    public static
    @NonNull
    Activity getActivity(View view) {
        Context context = view.getContext();

        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }

        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    /**
     * 全局获取String的方法
     *
     * @param id 资源Id
     * @return String
     */
    public static String getString(@StringRes int id) {
        return context.getResources().getString(id);
    }

    /**
     * 判断App是否是Debug版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        if (StringUtils.isSpace(context.getPackageName())) return false;
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }


    public static <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    /**
     * 获取状态栏高度
     *
     *
     */
    public static int getStatusBarHeight(Context context) {
        int result = 25;
        int resourceId = 0;
        try {
            resourceId = context.getResources().getIdentifier(
                    "status_bar_height", "dimen", "android");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param context
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param context
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param context
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param context
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 毫秒转换为时间,不报错
     *
     * @param m
     * @return
     */
    public static String millisecondToTime(long m, String reg) {
        String time = "";
        SimpleDateFormat sdf = new SimpleDateFormat(reg);
        try {
            time = sdf.format(new Date(m));
        } catch (Exception e) {
        }
        return time;
    }

    public static long secondToTime(String sTime) {
        long time = 0;
        try {
            time = Long.parseLong(sTime) * 1000;
        } catch (NumberFormatException e) {
        }
        return time;
    }

    public static String date2String(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        return dateString;
    }

    /**
     * String转date
     */
    @SuppressLint("SimpleDateFormat")
    public static Date stringtoDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * String转date yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static Date stringtoDate(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 根据Calendar返回星期String
     */
    public static String calendarToWeek(Calendar c) {
        String week = "星期";
        switch (c.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                week += "日";
                break;
            case 2:
                week += "一";
                break;
            case 3:
                week += "二";
                break;
            case 4:
                week += "三";
                break;
            case 5:
                week += "四";
                break;
            case 6:
                week += "五";
                break;
            case 7:
                week += "六";
                break;
        }
        return week;
    }


    public static int string2Int(String text){
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 外置存储是否存在
     *
     * @return boolean
     */
    public static boolean externalStorageExist() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 外置存储路径
     *
     * @return String
     */
    public static String getExternalStoragePath() {
        if (externalStorageExist()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"yuu1_flsd";
        }
        return null;
    }


    /**
     * apk存储位置
     *
     * @return String
     */
    public static String getApkStoragePath() {
        if (externalStorageExist()) {
            return getExternalStoragePath()+ File.separator+"apk_download";
        }
        return null;
    }


    /**
     * 数据库存储位置
     *
     * @return String
     */
    public static String getDBStoragePath() {
        if (externalStorageExist()) {
            return getExternalStoragePath()+ File.separator+"db";
        }
        return null;
    }

    /**
     * 资源包存储位置
     *
     * @return String
     */
    public static String getResStoragePath() {
        if (externalStorageExist()) {
            return getExternalStoragePath()+ File.separator+"res";
        }
        return null;
    }



    /**
     * 获取手机QQ包名
     * @return
     */
    public static String getQQPackageName() {
        return "com.tencent.mobileqq";
    }


    /**
     * 检测Apk是否安装
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isApkInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 打开QQ聊天
     * @return
     */
    public static void  openQQChat(View view , Context context, String qqNumber) {
        if (Utils.isApkInstalled(context, Utils.getQQPackageName())) {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" +qqNumber;
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }else {
            Utils.copyToClipboard(view,context, qqNumber);
        }
    }

    /**
     * 复制内容到粘贴板
     * @param c
     * @param cs
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static void copyToClipboard(View view, Context c, CharSequence cs) {
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            // api 11以上
            try {
                ClipboardManager clipboard = (ClipboardManager) c.getSystemService(
                        Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("key", cs);
                clipboard.setPrimaryClip(clip);
                Snackbar.make(view,"已成功复制到剪贴板!",Snackbar.LENGTH_SHORT).show();
            } catch (Exception e) {
                Snackbar.make(view,"抱歉,复制失败!",Snackbar.LENGTH_SHORT).show();
            }
        } else {
            // api 11以下
            try {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) c.getSystemService(c.CLIPBOARD_SERVICE);
                clipboard.setText(cs);
                Snackbar.make(view,"已成功复制到剪贴板!",Snackbar.LENGTH_SHORT).show();
            } catch (Exception e) {
                Snackbar.make(view,"抱歉,复制失败!",Snackbar.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 获取指定应用的VersionName
     *
     * @param context
     * @param pkn
     * @return
     */
    public static String getVersionName(Context context, String pkn) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(pkn, 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }

        return null;
    }

    public static String getVersionName() {
        return getVersionName(BaseApplication.getInstance(), BaseApplication.getInstance()
                .getPackageName());
    }


    /*
    * 安装apk
    *
    * */
    public static boolean  installApk(Context context , String apkPath){
        File file = new File(apkPath);
        if (file.exists()){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
            return true;
        }else{
            return false;
        }

    }

    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    public static void install(Context context , String apkPath) {
        File file = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(context, "com.yuu1.fulisudi", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }else{
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }









}