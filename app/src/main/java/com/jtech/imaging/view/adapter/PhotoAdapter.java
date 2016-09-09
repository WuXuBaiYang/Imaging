package com.jtech.imaging.view.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jtech.adapter.BaseJAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.util.DeviceUtils;
import com.jtech.imaging.util.ImageUtils;
import com.jtech.imaging.view.adapter.viewholder.ParallaxViewHolder;
import com.yayandroid.parallaxlistview.ParallaxImageView;

/**
 * 首页的图片列表适配器
 * Created by jianghan on 2016/9/8.
 */
public class PhotoAdapter extends BaseJAdapter<ParallaxViewHolder, PhotoModel> {

    private static final float PARALLAX_RATIO = 1.5f;
    private int screenHeight;

    public PhotoAdapter(Activity activity) {
        super(activity);
        //获取屏幕宽度
        screenHeight = DeviceUtils.getScreenHeight(getActivity());
    }

    /**
     * 使图片开始视差滚动
     */
    public void animateImage(RecyclerView view) {
        for (int i = 0; i < view.getChildCount(); i++) {
            RecyclerView.ViewHolder viewHolder = view.getChildViewHolder(view.getChildAt(i));
            if (viewHolder instanceof ParallaxViewHolder) {
                ((ParallaxViewHolder) viewHolder).animateImage();
            }
        }
    }

    @Override
    public ParallaxViewHolder createHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return new ParallaxViewHolder(layoutInflater.inflate(R.layout.view_photo_item, viewGroup, false));
    }

    @Override
    public void convert(ParallaxViewHolder holder, int viewType, int position) {
        PhotoModel photoModel = getItem(position);
        //设置item的高度
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = screenHeight / 3;
        holder.itemView.setLayoutParams(layoutParams);
        //设置背景主题色
        holder.itemView.setBackgroundColor(Color.parseColor(photoModel.getColor()));
        //显示图片
        ParallaxImageView parallaxImageView = holder.getImageView(R.id.imageview_photo);
        ImageUtils.showImage(getActivity(), photoModel.getUrls().getThumb(), parallaxImageView);
        parallaxImageView.setParallaxRatio(PARALLAX_RATIO);
        holder.setBackgroundImage(parallaxImageView);
        //设置作者
        holder.setText(R.id.textview_photo, photoModel.getUser().getUsername());
        //开启视差滚动
        holder.getBackgroundImage().reuse();
    }
}