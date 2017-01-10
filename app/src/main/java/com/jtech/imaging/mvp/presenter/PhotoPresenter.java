package com.jtech.imaging.mvp.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.jtech.imaging.mvp.contract.PhotoContract;
import com.jtech.imaging.strategy.PhotoResolutionStrategy;
import com.jtechlib.Util.ImageUtils;

import rx.functions.Action1;

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
    public String getUrl() {
        return isLocalImage() ? originUrl : originUrl + "?w=" + PhotoResolutionStrategy.getStrategyWidth(context);
    }

    @Override
    public boolean isLocalImage() {
        return !originUrl.startsWith("http") && !originUrl.startsWith("https");
    }

    @Override
    public void loadImage(int screenWidth) {
        //判断是否为本地图片
        if (isLocalImage()) {
            ImageUtils.requestLocalImage(context, getUrl(), screenWidth, -1, new Action1<Bitmap>() {
                @Override
                public void call(Bitmap bitmap) {
                    if (null != bitmap) {
                        view.loadSuccess(bitmap);
                    }
                }
            });
        } else {
            ImageUtils.requestImage(context, getUrl(), new Action1<Bitmap>() {
                @Override
                public void call(Bitmap bitmap) {
                    if (null != bitmap) {
                        view.loadSuccess(bitmap);
                    }
                }
            });
        }
    }
}