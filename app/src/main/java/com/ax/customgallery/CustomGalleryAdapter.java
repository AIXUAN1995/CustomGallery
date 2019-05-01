package com.ax.customgallery;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ax.customgallery.utils.CollectionsUtils;
import com.ax.customgallery.utils.DisplayUtils;
import com.bumptech.glide.Glide;

import java.util.List;

public class CustomGalleryAdapter extends RecyclerView.Adapter<CustomGalleryAdapter.ViewHolder> {
    private static final float SCALE = 0.85f;
    private List<GalleryItemBean> data;
    private Context context;

    public CustomGalleryAdapter(List<GalleryItemBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            return null;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (context == null || holder == null || CollectionsUtils.isEmptyList(data)) {
            return;
        }
        ViewGroup.MarginLayoutParams lp = ((ViewGroup.MarginLayoutParams) holder.itemContent.getLayoutParams());
        if (position == 0) {
            lp.width = DisplayUtils.getItemWidth(context);
            lp.height = DisplayUtils.getItemHeight(context);
            holder.itemContent.setLayoutParams(lp);
        } else {
            lp.width = (int) (DisplayUtils.getItemWidth(context) * SCALE);
            lp.height = (int) (DisplayUtils.getItemHeight(context) * SCALE);
            holder.itemContent.setLayoutParams(lp);
        }
        Glide.with(context)
                .load(data.get(position).getImage())
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView itemContent;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            itemContent = itemView.findViewById(R.id.itemContent);
            imageView = itemView.findViewById(R.id.headView);
        }
    }
}
