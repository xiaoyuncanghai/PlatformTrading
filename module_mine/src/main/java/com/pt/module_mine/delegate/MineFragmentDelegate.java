package com.pt.module_mine.delegate;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.pt.lib_common.base.ARouterPath;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.rxEasyhttp.EasyHttp;
import com.pt.lib_common.rxEasyhttp.callback.SimpleCallBack;
import com.pt.lib_common.rxEasyhttp.exception.ApiException;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.SPHelper;
import com.pt.lib_common.util.ToastUtils;
import com.pt.lib_common.util.Utils;
import com.pt.module_mine.R;
import com.pt.module_mine.bean.json.CheckJsonBean;
import com.pt.module_mine.bean.json.LogoutJsonBean;

import java.lang.reflect.Field;

public class MineFragmentDelegate extends AppDelegate {

    private ConstraintLayout loading_layout;
    private TextView mine_user_phone;
    private LinearLayout publish_message_sale;
    private LinearLayout publish_message_product;
    private LinearLayout publish_message_sale_list;
    private LinearLayout publish_message_product_list;
    private TextView tv_logout;
    private ImageView iv_user_mine;
    private ConstraintLayout coo_mine;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        coo_mine = get(R.id.coo_mine);
        mine_user_phone = get(R.id.mine_user_phone);
        publish_message_sale = get(R.id.publish_message_sale);
        publish_message_product = get(R.id.publish_message_product);
        publish_message_sale_list = get(R.id.publish_message_sale_list);
        publish_message_product_list = get(R.id.publish_message_product_list);
        tv_logout = get(R.id.tv_logout);
        iv_user_mine = get(R.id.iv_user_mine);

        loading_layout = get(R.id.loading_layout);
        loading_layout.setVisibility(View.GONE);
        coo_mine.setPadding(0,
                Utils.getStatusBarHeight(this.getActivity()) + Utils.dip2px(this.getActivity(), 5f),
                0, Utils.dip2px(this.getActivity(), 10f));
        mine_user_phone.setText(SPHelper.getString("phone", "", true));
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出退出当前用户的dialog
                showExitDialog();
            }
        });
        publish_message_sale.setOnClickListener(v -> {
            //发布求购信息界面
            ARouter.getInstance().build(ARouterPath.PUBLISH_SALE_PATH).navigation();
        });

        publish_message_product.setOnClickListener(v -> {
            //首先检查售卖资格
            checkSaleQualification();
        });

        publish_message_sale_list.setOnClickListener(v -> {
            //发布的信息列表
            ARouter.getInstance().build(ARouterPath.PUBLISH_LIST).navigation();
        });

        publish_message_product_list.setOnClickListener(v -> {
            //资金方列表
            //ARouter.getInstance().build(ARouterPath.FUND_SIDE).navigation();
            //申请成为资金方
            checkApplyMoney();
        });
    }

    /**
     * 检查资金方资格
     */
    private void checkApplyMoney() {
        EasyHttp.post(HttpConstant.API_USER_FUND).timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        CheckJsonBean checkJsonBean = new Gson().fromJson(s, CheckJsonBean.class);
                        if (checkJsonBean.getCode() == 0) {
                            if (checkJsonBean.getData() == 0) {
                                //初始状态, 前往申请成为资金方
                                ARouter.getInstance().build(ARouterPath.MONEY_APPLY).navigation();
                            } else if (checkJsonBean.getData() == 1) {
                                //审核通过
                                //ARouter.getInstance().build(ARouterPath.PUBLISH_GOODS_PATH).navigation();
                                Snackbar.make(getRootView(), "您已经是资金方, 无需继续申请",
                                        Snackbar.LENGTH_SHORT).show();
                                publish_message_product_list.setVisibility(View.GONE);
                            } else if (checkJsonBean.getData() == 2) {
                                Snackbar.make(getRootView(), "资格正在审核中, 请您资格通过后再进行商品发布",
                                        Snackbar.LENGTH_SHORT).show();
                            } else if (checkJsonBean.getData() == -1) {
                                //审核被拒绝
                                showNoticeDialog();
                            }
                        } else {
                        }
                    }
                });
    }

    /**
     * 检查售卖资格
     */
    private void checkSaleQualification() {
        EasyHttp.post(HttpConstant.API_CAN_SALE).timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        CheckJsonBean checkJsonBean = new Gson().fromJson(s, CheckJsonBean.class);
                        if (checkJsonBean.getCode() == 0) {
                            if (checkJsonBean.getData() == 0) {
                                //初始状态
                                ARouter.getInstance().build(ARouterPath.APPLICATION_QUALI).navigation();
                            } else if (checkJsonBean.getData() == 1) {
                                //审核通过
                                ARouter.getInstance().build(ARouterPath.PUBLISH_GOODS_PATH).navigation();
                            } else if (checkJsonBean.getData() == 2) {
                                Snackbar.make(getRootView(), "资格正在审核中, 请您资格通过后再进行商品发布",
                                        Snackbar.LENGTH_SHORT).show();
                            } else if (checkJsonBean.getData() == -1) {
                                //审核被拒绝
                                showNoticeDialog();
                            }
                        } else {
                        }
                    }
                });
    }

    private void showNoticeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("温馨提示");
        builder.setMessage("当前您申请资质已经被拒绝, 请您上传有效证件, 核对公司名称和公司信用代码, 然后重新申请");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("申请", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ARouter.getInstance().build(ARouterPath.APPLICATION_QUALI).navigation();
                dialog.dismiss();
            }
        });
        //设置对话框是可取消的
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("温馨提示");
        builder.setMessage("是否退出当前用户");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //退出当前用户
                loading_layout.setVisibility(View.VISIBLE);
                requestLogout();
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

    /**
     * 退出当前用户
     */
    private void requestLogout() {
        EasyHttp.post(HttpConstant.API_LOGOUT_URL).timeStamp(true).execute(new SimpleCallBack<String>() {
            @Override
            public void onError(ApiException e) {
                ToastUtils.showShortToast("退出失败");
            }

            @Override
            public void onSuccess(String s) {
                ToastUtils.showShortToast("退出成功");
                LogoutJsonBean jsonBean = new Gson().fromJson(s, LogoutJsonBean.class);
                if (jsonBean.getCode() == 0 && jsonBean.isSuccess()) {
                    SPHelper.putString("token", "", true);
                    SPHelper.putString("phone", "", true);
                    ARouter.getInstance().build(ARouterPath.PHONE_LOGIN_PATH).navigation();
                    getActivity().finish();
                }
            }
        });
    }
}
