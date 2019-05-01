package com.ax.customgallery;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ax.customgallery.utils.DisplayUtils;

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
        new CustomPagerSnapHelper().attachToRecyclerView(recyclerView);
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
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                slideInAnimation(recyclerView);
            }
        }

        /**
         * 当前第二页的动画
         */
        private void slideInAnimation(RecyclerView recyclerView) {
            float percent = getScrollPercent(recyclerView);
            if (percent > 0 && percent < 1) {
                View targetView = getTargetView(recyclerView, 1);
                if (targetView == null) {
                    return;
                }
                ViewGroup.MarginLayoutParams lp = ((ViewGroup.MarginLayoutParams) targetView.getLayoutParams());
                lp.width = (int) (DisplayUtils.getItemWidth(targetView.getContext()) * (0.85 + 0.15 * percent));
                lp.height = (int) (DisplayUtils.getItemHeight(targetView.getContext()) * (0.85 + 0.15 * percent));
                targetView.setLayoutParams(lp);
            }
        }

        private float getScrollPercent(RecyclerView recyclerView) {
            View firstItem = recyclerView.getChildAt(0);
            if (firstItem == null) {
                return 0;
            }
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int scrollDistance = firstItem.getWidth() - layoutManager.getDecoratedRight(firstItem);
            return scrollDistance * 1.0f / firstItem.getWidth();
        }

        private View getTargetView(RecyclerView recyclerView, int index) {
            View view = recyclerView.getChildAt(index);
            if (view == null) {
                return null;
            }
            return view.findViewById(R.id.itemContent);
        }
    }
}
