package com.jtech.imaging.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jtech.adapter.BaseJAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtech.imaging.view.adapter.viewholder.ParallaxViewHolder;
import com.jtechlib.Util.ImageUtils;
import com.yayandroid.parallaxlistview.ParallaxImageView;

/**
 * 图片列表适配器
 * Created by jianghan on 2016/9/8.
 */
public class PhotoAdapter extends BaseJAdapter<ParallaxViewHolder, PhotoModel> {

    private int screenWidth;

    public PhotoAdapter(Context context, int screenWidth) {
        super(context);
        //获取屏幕宽度
        this.screenWidth = screenWidth;
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
        double ratio = (1.0 * screenWidth) / photoModel.getWidth();
        int itemHeight = (int) (ratio * photoModel.getHeight());
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = itemHeight / 3 * 2;
        holder.itemView.setLayoutParams(layoutParams);
        //设置背景主题色
        holder.itemView.setBackgroundColor(Color.parseColor(photoModel.getColor()));
        //显示图片
        ParallaxImageView parallaxImageView = holder.getImageView(R.id.imageview_photo);
        //根据图片加载策略，获取合适的图片url
        String imageUrl = PhotoLoadStrategy.getUrl(getContext(), photoModel.getUrls().getRaw(), screenWidth);
        ImageUtils.showImage(getContext(), imageUrl, parallaxImageView);
        //设置为背景视差滚动图片
        holder.setBackgroundImage(parallaxImageView);
        //设置作者
        holder.setText(R.id.textview_photo, photoModel.getUser().getUsername());
        //开启视差滚动
        holder.getBackgroundImage().reuse();
    }
}