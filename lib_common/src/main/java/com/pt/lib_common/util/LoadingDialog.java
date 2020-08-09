package com.pt.lib_common.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.pt.lib_common.R;


/**
 * Author: Jeffer on 2018/3/19 14:34.
 * Email: jeffer7150@163.com
 * Description:
 */

public class LoadingDialog {

    private static Dialog dh;

    /**
     * 加载对话框
     * @param act
     */
    public static void showLoading(Context act) {
        dh = new Dialog(act, R.style.LoadingStyle);
        dh.setContentView(R.layout.layout_loading_dialog);
        dh.setCancelable(true);
        dh.setCanceledOnTouchOutside(false);
        if(!dh.isShowing()){
            dh.show();
        }
    }

    /**
     * 加载对话框
     * @param act
     * @param tipInfo
     */
    public static void showLoading(Context act , String tipInfo) {
        dh = new Dialog(act, R.style.LoadingStyle);
        View view = View.inflate(act,R.layout.layout_loading_dialog,null);
        TextView tv_content = (TextView) view.findViewById(R.id.dialog_content_tv);
        tv_content.setText(tipInfo);
        dh.setContentView(view);
        dh.setCancelable(true);
        dh.setCanceledOnTouchOutside(false);
        if(!dh.isShowing()){
            dh.show();
        }
    }

    /**
     * 隐藏加载对话框
     */
    public static void hideLoading() {
        if (dh != null && dh.isShowing()) {
            dh.dismiss();
        }
    }
}
