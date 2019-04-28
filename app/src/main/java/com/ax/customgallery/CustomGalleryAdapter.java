package com.ax.customgallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ax.customgallery.utils.CollectionsUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class CustomGalleryAdapter extends RecyclerView.Adapter<CustomGalleryAdapter.ViewHolder> {
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
		if (context != null && holder != null && !CollectionsUtils.isEmptyList(data)) {
			Glide.with(context)
					.load(data.get(position).getImage())
					.centerCrop()
					.into(holder.imageView);
			Log.i(TAG, "onBindViewHolder: " + data.get(position).getImage());
		}
	}

	@Override
	public int getItemCount() {
		return data == null ? 0 : data.size();
	}

	static class ViewHolder extends RecyclerView.ViewHolder {
		ImageView imageView;

		public ViewHolder(View itemView) {
			super(itemView);
			imageView = itemView.findViewById(R.id.headView);
		}
	}
}
