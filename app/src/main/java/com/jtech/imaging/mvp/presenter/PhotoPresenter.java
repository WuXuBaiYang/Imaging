package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.mvp.contract.PhotoContract;
import com.jtech.imaging.strategy.PhotoResolutionStrategy;

/**
 * 图片P类
 * Created by jianghan on 2016/11/23.
 */

public class PhotoPresenter implements PhotoContract.Presenter {

    private Context context;
    private PhotoContract.View view;
    private String originUrl;

    public PhotoPresenter(Context context, PhotoContract.View view, String originUrl) {
        this.context = context;
        this.view = view;
        this.originUrl = originUrl;
    }

    @Override
    public String getUrl(int width) {
        return isLocalImage() ? originUrl : originUrl + "?w=" + PhotoResolutionStrategy.getStrategyWidth(context);
    }

    @Override
    public boolean isLocalImage() {
        return originUrl.startsWith("http") || originUrl.startsWith("https");
    }
}