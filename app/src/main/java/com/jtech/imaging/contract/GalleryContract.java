package com.jtech.imaging.contract;

import android.graphics.Bitmap;

import rx.functions.Action1;

/**
 * 画廊浏览协议
 * Created by jianghan on 2016/11/21.
 */

public interface GalleryContract {
    interface Presenter {
        void getImage(String uri, int targetHeight, Action1<Bitmap> action1);
    }

    interface View {

    }
}