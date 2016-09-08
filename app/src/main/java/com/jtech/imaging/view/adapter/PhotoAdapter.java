package com.jtech.imaging.view.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jtech.adapter.RecyclerAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.util.DeviceUtils;
import com.jtech.imaging.util.ImageUtils;
import com.jtech.view.RecyclerHolder;

/**
 * 首页的图片列表适配器
 * Created by jianghan on 2016/9/8.
 */
public class PhotoAdapter extends RecyclerAdapter<PhotoModel> {
    private int screenWidth;

    public PhotoAdapter(Activity activity) {
        super(activity);
        //获取屏幕宽度
        screenWidth = DeviceUtils.getScreenWidth(getActivity());
    }

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int viewType) {
        return layoutInflater.inflate(R.layout.view_photo_item, viewGroup, false);
    }

    @Override
    public void convert(RecyclerHolder holder, PhotoModel model, int position) {
        //显示图片
        ImageView imageView = holder.getImageView(R.id.imageview_photo);
        ImageUtils.showImage(getActivity(), model.getUrls().getThumb(), imageView);
        //设置图片宽高
        double ratio = (screenWidth * 1.0) / model.getWidth();
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = (int) (model.getHeight() * ratio);
        imageView.setLayoutParams(layoutParams);
        //设置主题背景色
        holder.itemView.setBackgroundColor(Color.parseColor(model.getColor()));
        //设置图片信息
        StringBuffer photoMessage = new StringBuffer();
        photoMessage.append(model.getUser().getUsername());
        holder.setText(R.id.textview_photo, photoMessage);
    }
}