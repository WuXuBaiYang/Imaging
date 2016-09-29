package com.jtech.imaging.contract;


import android.content.Context;

import com.jtech.imaging.model.PhotoModel;
import com.jtechlib.contract.BaseContract;

/**
 * 欢迎页，首屏协议
 * Created by jianghan on 2016/9/6.
 */
public interface WelcomeContract {
    interface View extends BaseContract.View {
        void success(PhotoModel photoModel);

        void fail(String message);
    }

    interface Presenter extends BaseContract.Presenter {

        void getWelcomePagePhoto(Context context, String category, String collections, String featured, String username, String query, int width, int height, String orientation);
    }
}