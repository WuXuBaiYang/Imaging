package com.jtech.imaging.mvp.contract;

/**
 * 图片协议
 * Created by jianghan on 2016/11/23.
 */

public interface PhotoContract {
    interface Presenter {
        String getUrl(int width);

        boolean isLocalImage();
    }

    interface View {
    }
}