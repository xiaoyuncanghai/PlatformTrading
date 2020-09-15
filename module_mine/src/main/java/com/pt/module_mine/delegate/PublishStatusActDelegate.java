package com.pt.module_mine.delegate;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.pt.lib_common.constants.Constant;
import com.pt.lib_common.themvp.view.AppDelegate;
import com.pt.module_mine.R;

public class PublishStatusActDelegate extends AppDelegate {

    private ImageView status_publish_image;
    private TextView status_publish_text;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_status_show;
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        status_publish_image = get(R.id.status_publish_image);
        status_publish_text = get(R.id.status_publish_text);
        int status = getActivity().getIntent().getIntExtra(Constant.KEY_PUBLISH_STATUS, 1);
        if (status == 1) {
            status_publish_image.setImageResource(R.drawable.ic_confirmation);
            status_publish_text.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
            status_publish_text.setText("发布成功");
        } else if (status == 2) {
            status_publish_image.setImageResource(R.drawable.ic_error);
            status_publish_text.setTextColor(ContextCompat.getColor(getActivity(), R.color.icon_error_color));
            status_publish_text.setText("发布失败");
        }
    }
}
