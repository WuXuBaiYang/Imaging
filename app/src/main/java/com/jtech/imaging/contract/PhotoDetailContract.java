package com.jtech.imaging.contract;

import android.content.Context;

import com.jtech.imaging.model.PhotoModel;
import com.jtechlib.contract.BaseContract;

/**
 * 图片详情协议
 * Created by jianghan on 2016/10/8.
 */

public interface PhotoDetailContract {
    interface Presenter extends BaseContract.Presenter {
        void getPhotoDetailCache(Context context, String imageId);

        void getPhotoDetail(Context context, String imageId, int imageWidth, int imageHeight, String rect);
    }

    interface View extends BaseContract.View {

        void showSheetDialog();

        void showResolutionDialog();

        void success(PhotoModel photoModel);

        void fail(String message);
    }
}