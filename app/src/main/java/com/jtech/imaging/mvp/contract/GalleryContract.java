package com.jtech.imaging.mvp.contract;

import com.jtech.imaging.model.DownloadModel;

import java.util.List;

/**
 * 画廊浏览协议
 * Created by jianghan on 2016/11/21.
 */

public interface GalleryContract {
    interface Presenter {
        void getDownloadedList();
    }

    interface View {
        void downloadTaskList(List<DownloadModel> downloadModels);
    }
}