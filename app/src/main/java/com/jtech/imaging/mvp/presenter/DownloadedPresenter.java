package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.mvp.contract.DownloadContract;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.realm.listener.OnDownloadTaskListener;

import java.util.List;

/**
 * 已下载列表P类
 * Created by jianghan on 2016/11/17.
 */

public class DownloadedPresenter implements DownloadContract.DownloadedPresenter {
    private Context context;
    private DownloadContract.DownloadedView view;
    private DownloadRealmManager downloadRealmManager;

    public DownloadedPresenter(Context context, DownloadContract.DownloadedView view) {
        this.context = context;
        this.view = view;
        //实例化下载数据库管理
        downloadRealmManager = new DownloadRealmManager();
    }

    @Override
    public void getDownloadedTask() {
        downloadRealmManager.getDownloaded(new OnDownloadTaskListener() {
            @Override
            public void downloadTask(List<DownloadModel> downloadModels) {
                view.downloadTask(downloadModels);
            }
        });
    }

    @Override
    public void removeDownloaded(long id) {
        downloadRealmManager.removeDownload(id);
    }
}