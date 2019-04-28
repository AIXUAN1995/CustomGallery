package com.ax.customgallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private String[] pics = {"http://gss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/bd315c6034a85edfca479e4f48540923dc547596.jpg",
			"http://ww2.sinaimg.cn/large/005ImClmjw1ern9ybe83yj30zk0k01cm.jpg",
			"http://b-ssl.duitang.com/uploads/item/201802/13/20180213202427_vcykg.jpg",
			"http://i0.hdslb.com/bfs/article/3de475241c65c721c242f4f3f854fbd115fc0dd0.jpg",
			"http://b-ssl.duitang.com/uploads/item/201410/25/20141025181818_SSvPY.jpeg"};
	private RecyclerView recyclerView;
	private CustomGalleryAdapter adapter;
	private String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		recyclerView = findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
		adapter = new CustomGalleryAdapter(getData(), this);
		recyclerView.setAdapter(adapter);
		recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
			@Override
			public boolean onFling(int i, int i1) {
				return true;
			}
		});
		recyclerView.addOnScrollListener(new GalleryOnScrollListener());
	}

	private List<GalleryItemBean> getData() {
		List<GalleryItemBean> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			GalleryItemBean itemBean = new GalleryItemBean();
			itemBean.setImage(pics[i]);
			list.add(itemBean);
		}
		return list;
	}

	class GalleryOnScrollListener extends RecyclerView.OnScrollListener {
		private int x = 0;//记录当前x轴坐标
		private int y = 0;
		private int scrollX = 0;//记录每次延x轴滚动的距离
		private int scrollY = 0;

		@Override
		public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
			super.onScrollStateChanged(recyclerView, newState);
			Log.i(TAG, "newState:" + newState);
			if (newState == 0) {//停止滚动
				int dx;
				Log.i(TAG, "scrollX:" + scrollX + "recyclerView.getWidth()" + recyclerView.getWidth());
				if (scrollX > recyclerView.getWidth() / 2) {
					dx = recyclerView.getWidth() - scrollX;
				} else if (-scrollX > recyclerView.getWidth() / 2) {
					dx = scrollX - recyclerView.getWidth();
				} else {
					dx = -scrollX;
				}
				Log.i(TAG, "dx:" + dx);
				recyclerView.scrollBy(dx, 0);
				scrollX = 0;
			}
		}

		@Override
		public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
			scrollX += dx;
		}
	}
}
