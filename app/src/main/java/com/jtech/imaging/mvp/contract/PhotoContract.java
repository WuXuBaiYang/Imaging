package com.jtech.imaging.mvp.contract;

import android.graphics.Bitmap;

/**
 * 图片协议
 * Created by jianghan on 2016/11/23.
 */

public interface PhotoContract {
    interface Presenter {
        String getUrl();

        boolean isLocalImage();

        void loadImage(int screenWidth);
    }

    interface View {
        void loadSuccess(Bitmap bitmap);

        void looadFail(String error);
    }
}