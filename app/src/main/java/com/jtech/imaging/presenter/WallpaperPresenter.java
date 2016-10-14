package com.jtech.imaging.presenter;

import com.jtech.imaging.contract.WallpaperContract;

/**
 * 壁纸设置P类
 * Created by wuxubaiyang on 16/4/16.
 */
public class WallpaperPresenter implements WallpaperContract.Presenter {

    private WallpaperContract.View view;

    public WallpaperPresenter(WallpaperContract.View view) {
        this.view = view;
    }

    @Override
    public String getUrl(String originUrl, String width) {
        return null;
    }
}