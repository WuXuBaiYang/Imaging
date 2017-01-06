package com.jtech.imaging.mvp.contract;

import android.graphics.Bitmap;

/**
 * 图片协议
 * Created by jianghan on 2016/11/23.
 */

public interface PhotoContract {
    interface Presenter {
        void getImage(String uri,int width,int height, int targetWidth);
    }

    interface View {
        void setPhoto(Bitmap bitmap);
    }
}