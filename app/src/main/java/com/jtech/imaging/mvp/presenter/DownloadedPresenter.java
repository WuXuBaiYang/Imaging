package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.mvp.contract.DownloadedContract;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.realm.listener.OnDownloadTaskListener;

import java.util.List;

/**
 * 已下载页面P类
 * Created by JTech on 2017/1/6.
 */
public class DownloadedPresenter implements DownloadedContract.Presenter {
    private Context context;
    private DownloadedContract.View view;
    private DownloadRealmManager downloadRealmManager;

    public DownloadedPresenter(Context context, DownloadedContract.View view) {
        this.context = context;
        this.view = view;
        //实例化数据库管理
        downloadRealmManager = new DownloadRealmManager();
    }

    @Override
    public void getDownloadedList() {
        downloadRealmManager.getDownloaded(new OnDownloadTaskListener() {
            @Override
            public void downloadTask(List<DownloadModel> downloadModels) {
                view.downloadedList(downloadModels);
            }
        });
    }

    @Override
    public void deleteDownloaded(long id) {
        downloadRealmManager.removeDownload(id);
    }
}