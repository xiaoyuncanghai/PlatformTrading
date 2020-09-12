package com.pt.lib_common.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.pt.lib_common.R;
import com.pt.lib_common.constants.Constant;


/**
 * Author: Jeffer on 2017/7/24 16:20.
 * Email: jeffer7150@163.com
 * Description:
 */

public class DialogHelp {


    private static DialogHelp dialogHelp;

    private DialogHelp(){
    }


    public static synchronized DialogHelp getInstance(){
        if (dialogHelp == null){
            dialogHelp = new DialogHelp();
        }
        return dialogHelp;
    }

    private Dialog dialog;
    public View contentView;

   /* public Dialog showInputDialog(Context context, boolean canceledOnTouchOutside) {
        contentView = View.inflate(context, R.layout.dilog_input_user_phone,null);
    }*/

    /*
    * 上传头像时条目选择提示框
    * */
    public Dialog makeItemSelectDialog(Context context, boolean canceledOnTouchOutside){
        contentView = View.inflate(context, R.layout.layout_item_select_dialog,null);
        dialog = new Dialog(context, R.style.DialogStyle);
        dialog.setContentView(contentView);
        WindowManager.LayoutParams dialogAtt = dialog.getWindow().getAttributes();
        dialogAtt.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogAtt.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogAtt.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(dialogAtt);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.show();
        return dialog;
    }

    public void dismissDialog(){
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }




}
