package com.jtech.imaging.contract;

import com.jtech.imaging.model.PhotoModel;
import com.jtechlib.contract.BaseContract;

/**
 * 随机页面协议
 * Created by jianghan on 2016/9/23.
 */
public interface RandomContract {
    interface Presenter extends BaseContract.Presenter {
        void getRandomPhoto(String category, String collections, String featured, String username, String query, int width, int height, String orientation);
    }

    interface View extends BaseContract.View {
        void success(PhotoModel photoModel);

        void fail(String message);
    }
}