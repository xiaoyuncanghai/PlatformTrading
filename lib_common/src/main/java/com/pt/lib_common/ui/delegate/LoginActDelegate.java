package com.pt.lib_common.ui.delegate;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.google.android.material.snackbar.Snackbar;
import com.pt.lib_common.R;
import com.pt.lib_common.bean.SendSmsJsonBean;
import com.pt.lib_common.constants.HttpConstant;
import com.pt.lib_common.http.EasyHttpClient;
import com.pt.lib_common.http.callback.EasyCustomCallback;
import com.pt.lib_common.http.manager.EasyHttpClientManager;
import com.pt.lib_common.http.request.EasyRequestParams;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.DeviceUuidFactory;
import com.pt.lib_common.util.SPHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.xw.repo.XEditText;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActDelegate extends AppDelegate {

    private SmartRefreshLayout srl_login_acc;
    private TextView tv_acc;
    private XEditText et_acc;
    private TextView tv_del_acc;
    private TextView tv_pwd;
    private EditText et_pwd;
    private TextView tv_show_pwd;
    private TextView tv_send_code;
    private Button bt_login;
    private Typeface typeface;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        initView();
        initEvent();
    }

    private void initView() {
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "iconfont/iconfont.ttf");
        srl_login_acc = get(R.id.srl_login_acc);
        tv_acc = get(R.id.tv_acc);
        et_acc = get(R.id.et_acc);
        tv_del_acc = get(R.id.tv_del_acc);
        tv_pwd = get(R.id.tv_pwd);
        et_pwd = get(R.id.et_pwd);
        tv_send_code = get(R.id.tv_send_code);
        bt_login = get(R.id.bt_login);

        //手机号码 按照3 4 4切割排列
        et_acc.setPattern(new int[]{3, 4, 4}, " ");

        tv_del_acc.setTypeface(typeface);
        tv_del_acc.setText("\ue7bf");
        tv_send_code.setTextColor(getActivity().getResources().getColorStateList(R.color.color_slt_send_code));

        et_acc.setInputType(InputType.TYPE_CLASS_PHONE);
        et_pwd.setInputType(InputType.TYPE_CLASS_NUMBER);
        tv_acc.setText("手机号");
        tv_pwd.setText("验证码");
        et_acc.setHint("请输入手机号");
        et_pwd.setHint("请输入验证码");

        String phoneText = SPHelper.getString("phone", "", true);
        if (!TextUtils.isEmpty(phoneText)) {
            et_acc.setText(phoneText);
        }
    }


    private void initEvent() {
        srl_login_acc.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(0);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(0);
            }
        });


        et_acc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_acc.getText().toString().length() > 0 && et_pwd.getText().toString().length() > 0) {
                    bt_login.setEnabled(true);
                } else {
                    bt_login.setEnabled(false);
                }
            }
        });

        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_acc.getText().toString().length() > 0 && et_pwd.getText().toString().length() > 0) {
                    bt_login.setEnabled(true);
                } else {
                    bt_login.setEnabled(false);
                }
            }
        });

        tv_del_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_acc.setText("");
            }
        });

        tv_send_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyRequestParams params = new EasyRequestParams();
                params.put("phone", et_acc.getText().toString().replace(" ", ""));
                params.put("machineCode", new DeviceUuidFactory(getActivity()).getUuid().toString());
                LogUtils.d("machineCode = " + new DeviceUuidFactory(getActivity()).getUuid().toString());
                EasyHttpClient.post(HttpConstant.API_SEND_SMS_URL, params, new EasyCustomCallback<SendSmsJsonBean>(){
                    @Override
                    public void onSuccess(SendSmsJsonBean content) {
                        if (content.getCode() == 0) {
                            Snackbar.make(srl_login_acc,"验证码发送成功，请注意查收！",Snackbar.LENGTH_SHORT).show();
                            startTime();
                        } else {
                            Snackbar.make(srl_login_acc,content.getMessage(),Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
                        Snackbar.make(srl_login_acc, error.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public static final int MAX_TIME = 120;
    private int count = MAX_TIME;
    private TimerTask task;
    private Timer timer;
    /**
     * 验证码计时
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (msg.arg1 == 0) {
                        stopLoop();
                        count = MAX_TIME;
                        tv_send_code.setEnabled(true);
                        tv_send_code.setText("获取验证码");
                    } else {
                        tv_send_code.setEnabled(false);
                        tv_send_code.setText("" + msg.arg1 + "秒后重试");
                    }
                    break;
            }
        }
    };
    private void startTime() {
        stopLoop();

        task = new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = 0;
                msg.arg1 = count--;
                handler.sendMessage(msg);

            }
        };
        timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    private void stopLoop() {
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }
        timer = null;
        task = null;
    }
}
