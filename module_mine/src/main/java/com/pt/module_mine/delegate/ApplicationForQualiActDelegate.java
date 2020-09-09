package com.pt.module_mine.delegate;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.module_mine.R;
import com.xw.repo.XEditText;

/**
 * 申请资格
 */
public class ApplicationForQualiActDelegate extends AppDelegate {

    private ImageView upload_business_license;
    private XEditText et_input_company_name;
    private XEditText et_input_company_code;
    private TextView tv_upload_application_quality;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_application_quality;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        upload_business_license = get(R.id.upload_business_license);
        et_input_company_name = get(R.id.et_input_company_name);
        et_input_company_code = get(R.id.et_input_company_code);
        tv_upload_application_quality = get(R.id.tv_upload_application_quality);
    }
}
