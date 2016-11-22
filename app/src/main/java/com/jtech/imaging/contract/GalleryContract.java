package com.jtech.imaging.contract;

import android.graphics.Bitmap;

import com.jtech.imaging.model.DownloadModel;

import java.util.List;

import rx.functions.Action1;

/**
 * 画廊浏览协议
 * Created by jianghan on 2016/11/21.
 */

public interface GalleryContract {
    interface Presenter {
        void getImage(DownloadModel downloadModel, int targetWidth, Action1<Bitmap> action1);

        void getDownloadedList();
    }

    interface View {
        void downloadTaskList(List<DownloadModel> downloadModels);
    }
}