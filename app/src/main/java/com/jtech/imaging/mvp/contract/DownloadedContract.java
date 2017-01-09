package com.jtech.imaging.mvp.contract;

import com.jtech.imaging.model.DownloadModel;
import com.jtechlib.contract.BaseContract;

import java.util.List;

/**
 * 已下载页面协议
 * Created by JTech on 2017/1/6.
 */
public interface DownloadedContract {
    interface Presenter extends BaseContract.Presenter {
        void getDownloadedList();

        void deleteDownloaded(long id);

        void redownload(long id);

        void removeListener();
    }

    interface View extends BaseContract.View {
        void downloadedList(List<DownloadModel> downloadModels);
    }
}