package com.jtech.imaging.mvp.contract;

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

        String getPhotoName();

        String getPhotoUrl();

        String getPhotoId();

        PhotoModel getPhotoModel();

        void startDownload();
    }

    interface View extends BaseContract.View {

        void showSheetDialog();

        void showResolutionDialog();

        void showPhotoExifDialog();

        void jumpToWallpaper();

        void success(PhotoModel photoModel);

        void fail(String message);

        void downloadFail(String error);

        void downloadSuccess();
    }
}