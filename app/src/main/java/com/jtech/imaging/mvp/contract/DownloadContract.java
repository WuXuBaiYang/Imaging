package com.jtech.imaging.mvp.contract;

import com.jtechlib.contract.BaseContract;

/**
 * 下载管理页面协议
 * Created by jianghan on 2016/10/19.
 */

public interface DownloadContract {
    interface Presenter extends BaseContract.Presenter {
        void stopAllDownload();

        void startAllDownload();

        boolean hasDownloading();

        boolean isAllDownloading();

        void addDownloadStateChangeListener();

        void removeListener();
    }

    interface View extends BaseContract.View {
        void setDownloadingState(boolean isAllDownloading);
    }
}