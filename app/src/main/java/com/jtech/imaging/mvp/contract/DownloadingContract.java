package com.jtech.imaging.mvp.contract;

import com.jtech.imaging.model.DownloadModel;
import com.jtechlib.contract.BaseContract;

import java.util.List;

/**
 * 下载页面协议
 * Created by JTech on 2017/1/6.
 */
public interface DownloadingContract {
    interface Presenter extends BaseContract.Presenter {
        void addDownloadingListener();

        void startDownload(long id);

        void stopDownload(long id);

        void deleteDownload(long id);

        boolean isIndeterminate(long id);
    }

    interface View extends BaseContract.View {
        void downloadingList(List<DownloadModel> downloadModels);
    }
}