package com.jtech.imaging.view.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jtech.adapter.RecyclerAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.Util.ImageUtils;

/**
 * 已缓存列表适配器
 * Created by jianghan on 2016/11/1.
 */

public class DownloadedAdapter extends RecyclerAdapter<DownloadModel> {
    private int itemSize;

    public DownloadedAdapter(Activity activity, int spanCount) {
        super(activity);
        //计算item的宽度
        this.itemSize = DeviceUtils.getScreenWidth(activity) / spanCount;
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
        ImageView imageView = recyclerHolder.getImageView(R.id.imageview_photo);
        ImageUtils.showImage(getContext(), downloadModel.getPath(), imageView);
        //设置默认背景颜色
        imageView.setBackgroundColor(Color.parseColor(downloadModel.getColor()));
    }
}