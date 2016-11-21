package com.jtech.imaging.contract;

import com.jtech.imaging.model.DownloadModel;
import com.jtechlib.contract.BaseContract;

import java.util.List;

/**
 * 下载管理页面协议
 * Created by jianghan on 2016/10/19.
 */

public interface DownloadContract {
    interface Presenter extends BaseContract.Presenter {
        void stopAllDownload();

        void startAllDownload();

        void listenDownloadingChange();
    }

    interface View extends BaseContract.View {
        void downloadingList(List<DownloadModel> downloadModels);
    }


    interface DownloadedPresenter extends BaseContract.Presenter {
        void getDownloadedTask();

        void removeDownloaded(long id);
    }

    interface DownloadedView extends BaseContract.View {
        void downloadTask(List<DownloadModel> downloadModels);
    }


    interface DownloadingPresenter extends BaseContract.Presenter {
        void getDownloadingTask();
    }

    interface DownloadingView extends BaseContract.View {
        void downloadTask(List<DownloadModel> downloadModels);
    }
}