package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.mvp.contract.GalleryContract;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.realm.listener.OnDownloadTaskListener;

import java.util.List;

/**
 * 画廊P类
 * Created by jianghan on 2016/11/21.
 */

public class GalleryPresenter implements GalleryContract.Presenter {
    private Context context;
    private GalleryContract.View view;
    private DownloadRealmManager downloadRealmManager;

    public GalleryPresenter(Context context, GalleryContract.View view) {
        this.context = context;
        this.view = view;
        //实例化数据库管理
        this.downloadRealmManager = new DownloadRealmManager();
    }

    @Override
    public void getDownloadedList() {
        downloadRealmManager.getDownloaded(new OnDownloadTaskListener() {
            @Override
            public void downloadTask(List<DownloadModel> downloadModels) {
                view.downloadTaskList(downloadModels);
            }
        });
    }
}