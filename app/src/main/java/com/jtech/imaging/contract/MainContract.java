package com.jtech.imaging.contract;

import com.jtech.imaging.model.PhotoModel;
import com.jtechlib.contract.BaseContract;

import java.util.List;

/**
 * 主页面协议
 * Created by wuxubaiyang on 16/4/16.
 */
public interface MainContract {

    interface Presenter extends BaseContract.Presenter {
        void requestPhotoList(int pageIndex, int displayNumber, String orderBy, boolean loadMore);

        void requestCachePhotoList();
    }

    interface View extends BaseContract.View {
        void showSortDialog();

        void showImageLoadStrategyDialog();

        void showImageLoadStrategyFixedDialog(int photoLoadStrategy);

        void success(List<PhotoModel> photoModels, boolean loadMore);

        void fail(String message);

        void cacheSuccess(List<PhotoModel> photoModels);

        void jumpToDownloadManager();
    }
}