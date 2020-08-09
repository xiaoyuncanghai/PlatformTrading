package com.pt.lib_common.util;

import android.text.style.ClickableSpan;
import android.view.View;


/**
 * Author: Jeffer on 2017/8/29 12:12.
 * Email: jeffer7150@163.com
 * Description:
 */

public class MyUrlSpan extends ClickableSpan {
    private String mUrl;
    public MyUrlSpan(String url) {
        mUrl =url;
    }
    @Override
    public void onClick(View widget) {

    }
}
