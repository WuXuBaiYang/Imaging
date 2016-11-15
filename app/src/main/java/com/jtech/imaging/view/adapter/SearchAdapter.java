package com.jtech.imaging.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jtech.adapter.BaseJAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.model.ResultsModel;
import com.jtech.imaging.model.SearchPhotoModel;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtech.imaging.view.adapter.viewholder.ParallaxViewHolder;
import com.jtech.view.RecyclerHolder;
import com.jtechlib.Util.ImageUtils;
import com.yayandroid.parallaxlistview.ParallaxImageView;

/**
 * 图片列表适配器
 * Created by jianghan on 2016/9/8.
 */
public class SearchAdapter extends BaseJAdapter<ParallaxViewHolder, ResultsModel> {

    private int screenWidth;
    private SearchPhotoModel searchPhotoModel;

    public SearchAdapter(Context context, int screenWidth) {
        super(context);
        this.screenWidth = screenWidth;
    }

    /**
     * 根据holder获取图片视图对象
     *
     * @param recyclerHolder
     * @return
     */
    public ParallaxImageView getParallaxView(RecyclerHolder recyclerHolder) {
        ParallaxImageView parallaxImageView = recyclerHolder.getImageView(R.id.imageview_photo);
        parallaxImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return parallaxImageView;
    }

    public void setSearchPhotoModel(SearchPhotoModel searchPhotoModel, boolean loadMore) {
        this.searchPhotoModel = searchPhotoModel;
        //设置数据
        setDatas(searchPhotoModel.getResults(), loadMore);
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotalPage() {
        return null != searchPhotoModel ? searchPhotoModel.getTotalPages() : 0;
    }

    /**
     * 获取总数
     *
     * @return
     */
    public int getTotalCount() {
        return null != searchPhotoModel ? searchPhotoModel.getTotal() : 0;
    }

    /**
     * 使图片开始视差滚动
     */
    public void animateImage(RecyclerView view) {
        for (int i = 0; i < view.getChildCount(); i++) {
            RecyclerView.ViewHolder viewHolder = view.getChildViewHolder(view.getChildAt(i));
            if (viewHolder instanceof ParallaxViewHolder) {
                ParallaxViewHolder parallaxViewHolder = (ParallaxViewHolder) viewHolder;
                parallaxViewHolder.getBackgroundImage().setScaleType(ImageView.ScaleType.MATRIX);
                parallaxViewHolder.animateImage();
            }
        }
    }

    @Override
    public ParallaxViewHolder createHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return new ParallaxViewHolder(layoutInflater.inflate(R.layout.view_photo_item, viewGroup, false));
    }

    @Override
    public void convert(ParallaxViewHolder holder, int viewType, int position) {
        ResultsModel resultsModel = getItem(position);
        //设置item的高度
        double ratio = (1.0 * screenWidth) / resultsModel.getWidth();
        int itemHeight = (int) (ratio * resultsModel.getHeight());
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = itemHeight / 3 * 2;
        holder.itemView.setLayoutParams(layoutParams);
        //显示图片
        ParallaxImageView parallaxImageView = holder.getImageView(R.id.imageview_photo);
        //设置背景主题色
        parallaxImageView.setBackgroundColor(Color.parseColor(resultsModel.getColor()));
        //根据图片加载策略，获取合适的图片url
        String imageUrl = PhotoLoadStrategy.getUrl(getContext(), resultsModel.getUrls().getRaw(), screenWidth);
        ImageUtils.showImage(getContext(), imageUrl, parallaxImageView);
        //设置为背景视差滚动图片
        holder.setBackgroundImage(parallaxImageView);
        //设置作者
        holder.setText(R.id.textview_photo, resultsModel.getUser().getUsername());
        //开启视差滚动
        holder.getBackgroundImage().reuse();
    }
}