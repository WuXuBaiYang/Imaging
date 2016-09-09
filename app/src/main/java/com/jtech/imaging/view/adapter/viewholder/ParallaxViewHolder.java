package com.jtech.imaging.view.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jtech.view.RecyclerHolder;
import com.yayandroid.parallaxlistview.ParallaxImageView;

/**
 * 视差滚动适配器
 * Created by jianghan on 2016/9/9.
 */
public class ParallaxViewHolder extends RecyclerHolder implements ParallaxImageView.ParallaxImageListener {

    private ParallaxImageView backgroundImage;

    public ParallaxViewHolder(View itemView) {
        super(itemView);
    }

    public void setBackgroundImage(ParallaxImageView backgroundImage) {
        this.backgroundImage = backgroundImage;
        this.backgroundImage.setListener(this);
    }

    public ParallaxImageView getBackgroundImage() {
        return backgroundImage;
    }

    @Override
    public int[] requireValuesForTranslate() {
        if (itemView.getParent() == null)
            return null;

        int[] itemPosition = new int[2];
        itemView.getLocationOnScreen(itemPosition);

        int[] recyclerPosition = new int[2];
        ((RecyclerView) itemView.getParent()).getLocationOnScreen(recyclerPosition);

        return new int[]{itemView.getMeasuredHeight(), itemPosition[1], ((RecyclerView) itemView.getParent()).getMeasuredHeight(), recyclerPosition[1]};
    }

    public void animateImage() {
        getBackgroundImage().doTranslate();
    }
}
