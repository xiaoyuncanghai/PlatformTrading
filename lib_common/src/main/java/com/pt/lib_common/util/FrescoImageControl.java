package com.pt.lib_common.util;

import android.graphics.drawable.Animatable;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.fresco.helper.utils.DensityUtil;
import com.facebook.imagepipeline.image.ImageInfo;


/**
 * Author: Jeffer on 2018/4/24 10:43.
 * Email: jeffer7150@163.com
 * Description:
 */

public class FrescoImageControl extends BaseControllerListener<ImageInfo> {

    private final SimpleDraweeView draweeView;

    public FrescoImageControl(SimpleDraweeView draweeView) {
        this.draweeView = draweeView;
    }

    @Override
    public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
        if (imageInfo == null || draweeView == null) {
            return;
        }

        ViewGroup.LayoutParams vp = draweeView.getLayoutParams();
        int maxWidthSize = DensityUtil.getDisplayWidth(draweeView.getContext())-DensityUtil.dip2px(draweeView.getContext(),32f);
        int maxHeightSize = DensityUtil.getDisplayHeight(draweeView.getContext());
        int width = imageInfo.getWidth();
        int height = imageInfo.getHeight();


        if (width > height) {
            int maxWidth = DensityUtil.dipToPixels(draweeView.getContext(), maxWidthSize);
          /*  if (width > maxWidth) {
                width = maxWidth;
            }
            vp.width = width;*/

            vp.width = maxWidthSize;
            vp.height = (int) (imageInfo.getHeight() / (float) imageInfo.getWidth() * vp.width);
        } else {
            // width <= height
            int maxHeight = DensityUtil.dipToPixels(draweeView.getContext(), maxHeightSize);
            if (height > maxHeightSize) {
                height = maxHeightSize;
                vp.height = height;
                vp.width = (int) ((float) imageInfo.getWidth() / imageInfo.getHeight() * vp.height);
            }else{
                int maxWidth = maxWidthSize;
                vp.width = maxWidth;
                vp.height = (int) (imageInfo.getHeight() / (float) imageInfo.getWidth() * vp.width);
            }


        }

        draweeView.requestLayout();
    }

}
