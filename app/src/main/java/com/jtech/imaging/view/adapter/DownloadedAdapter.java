package com.jtech.imaging.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.adapter.RecyclerSwipeAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.Util.ImageUtils;

/**
 * 已缓存列表适配器
 * Created by jianghan on 2016/11/1.
 */

public class DownloadedAdapter extends RecyclerSwipeAdapter<DownloadModel> {

    private int itemSize;

    public DownloadedAdapter(Context context) {
        super(context);
    }

    /**
     * 设置item的宽高
     *
     * @param screenWidth
     */
    public void setupItemWidth(int screenWidth) {
        int defaultMargen = getContext().getResources().getDimensionPixelSize(R.dimen.margin_default_leftright);
        this.itemSize = (screenWidth - defaultMargen * 4) / 3;
    }

    @Override
    public void clearView(RecyclerHolder recyclerHolder) {
        recyclerHolder.setImageResource(R.id.imageview_state, 0);
    }

    @Override
    public View getSwipeView(RecyclerHolder recyclerHolder) {
        return recyclerHolder.getView(R.id.imageview_photo);
    }

    @Override
    public void onSwipe(RecyclerHolder recyclerHolder, int i, float v) {

    }

    @Override
    protected View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return layoutInflater.inflate(R.layout.view_downloaded, viewGroup, false);
    }

    @Override
    protected void convert(RecyclerHolder recyclerHolder, DownloadModel downloadModel, int i) {
        //设置item的宽高
        ViewGroup.LayoutParams layoutParams = recyclerHolder.itemView.getLayoutParams();
        layoutParams.width = itemSize;
        layoutParams.height = itemSize;
        recyclerHolder.itemView.setLayoutParams(layoutParams);
        //设置图片
        ImageUtils.showImage(getContext(), downloadModel.getPath(), recyclerHolder.getImageView(R.id.imageview_photo));
    }
}