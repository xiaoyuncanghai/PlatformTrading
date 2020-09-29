package com.pt.module_mine.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pt.module_mine.R;
import com.pt.module_mine.bean.ImageBean;
import com.pt.module_mine.utils.AndroidLifecycleUtils;

import java.util.ArrayList;


/**
 * Created by donglua on 15/5/31.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private ArrayList<ImageBean> imageInfos = new ArrayList<>();
    private LayoutInflater inflater;

    private Context mContext;

    public final static int TYPE_ADD = 1;
    public final static int TYPE_PHOTO = 2;

    final static int MAX = 3;

    public PhotoAdapter(Context mContext, ArrayList<ImageBean> imageBeans) {
        this.imageInfos = imageBeans;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);

    }


    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_ADD:
                itemView = inflater.inflate(R.layout.item_add, parent, false);
                break;
            case TYPE_PHOTO:
                itemView = inflater.inflate(R.layout.picker_item_photo, parent, false);
                break;
        }
        return new PhotoViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {

        if (getItemViewType(position) == TYPE_PHOTO) {
            boolean canLoadImage = AndroidLifecycleUtils.canLoadImage(holder.ivPhoto.getContext());
            if (canLoadImage) {
                Glide.with(mContext)
                        .load(imageInfos.get(position).getImageUri())
                        .apply(new RequestOptions().centerCrop())
                        .thumbnail(0.1f)
                        .into(holder.ivPhoto);
            }
        }
    }


    @Override
    public int getItemCount() {
        int count = imageInfos.size() + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == imageInfos.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private View vSelected;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            if (vSelected != null) vSelected.setVisibility(View.GONE);
        }
    }

}
