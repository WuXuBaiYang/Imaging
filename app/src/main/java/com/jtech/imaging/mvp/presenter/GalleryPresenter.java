package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.mvp.contract.GalleryContract;

import java.util.List;

/**
 * 画廊P类
 * Created by jianghan on 2016/11/21.
 */

public class GalleryPresenter implements GalleryContract.Presenter {
    private Context context;
    private GalleryContract.View view;

    private List<String> imageUris;
    private int selectIndex;

    public GalleryPresenter(Context context, GalleryContract.View view, List<String> imageUris, int selectIndex) {
        this.context = context;
        this.view = view;
        this.imageUris = imageUris;
        this.selectIndex = selectIndex;
    }

    @Override
    public List<String> getImageUris() {
        return imageUris;
    }

    @Override
    public int getSelectIndex() {
        return selectIndex;
    }
}