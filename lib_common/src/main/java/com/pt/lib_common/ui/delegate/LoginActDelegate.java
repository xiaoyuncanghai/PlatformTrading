package com.pt.lib_common.ui.delegate;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.pt.lib_common.R;
import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.http.exception.CommonException;
import com.pt.lib_common.http.router.Mango;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.lib_common.util.RSAUtil;
import com.pt.lib_common.util.SPHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.xw.repo.XEditText;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;

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
        typeface = Typeface.createFromAsset(getActivity().getAssets(),"iconfont/iconfont.ttf");
        srl_login_acc = get(R.id.srl_login_acc);
        tv_acc = get(R.id.tv_acc);
        et_acc = get(R.id.et_acc);
        tv_del_acc = get(R.id.tv_del_acc);
        tv_pwd = get(R.id.tv_pwd);
        et_pwd = get(R.id.et_pwd);
        tv_send_code = get(R.id.tv_send_code);
        bt_login = get(R.id.bt_login);

        //手机号码 按照3 4 4切割排列
        et_acc.setPattern(new int[]{3,4,4} , " ");

        tv_del_acc.setTypeface(typeface);
        tv_del_acc.setText("\ue7bf");
        tv_send_code.setTextColor(getActivity().getResources().getColorStateList(R.color.color_slt_send_code));

        et_acc.setInputType(InputType.TYPE_CLASS_PHONE);
        et_pwd.setInputType(InputType.TYPE_CLASS_NUMBER);
        tv_acc.setText("手机号");
        tv_pwd.setText("验证码");
        et_acc.setHint("请输入手机号");
        et_pwd.setHint("请输入验证码");

        String phoneText = SPHelper.getString("phone","",true);
        if (!TextUtils.isEmpty(phoneText)){
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
                if (et_acc.getText().toString().length()>0 && et_pwd.getText().toString().length()>0){
                    bt_login.setEnabled(true);
                }else{
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
                if (et_acc.getText().toString().length()>0&&et_pwd.getText().toString().length()>0){
                    bt_login.setEnabled(true);
                }else{
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
    }

}
