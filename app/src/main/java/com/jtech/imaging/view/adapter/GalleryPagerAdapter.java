package com.jtech.imaging.view.adapter;

import com.jtechlib.view.adapter.BasePagerAdapter;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * 画廊的viewpager的适配器
 * Created by jianghan on 2016/11/21.
 */

public class GalleryPagerAdapter extends BasePagerAdapter<PhotoView> {
    private List<PhotoView> photoViews;

    public GalleryPagerAdapter(List<PhotoView> photoViews) {
        super(photoViews);
        this.photoViews = photoViews;
    }

    public PhotoView getPhotoView(int position) {
        return photoViews.get(position);
    }
}