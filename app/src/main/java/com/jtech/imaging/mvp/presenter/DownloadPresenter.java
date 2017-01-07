package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.mvp.contract.DownloadContract;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.realm.listener.OnDownloadTaskListener;

import java.util.List;

/**
 * 下载管理，P类
 * Created by jianghan on 2016/10/19.
 */

public class DownloadPresenter implements DownloadContract.Presenter {
    private Context context;
    private DownloadContract.View view;
    private DownloadRealmManager downloadRealmManager;

    public DownloadPresenter(Context context, DownloadContract.View view) {
        this.context = context;
        this.view = view;
        //实例化下载数据库管理
        downloadRealmManager = new DownloadRealmManager();
        //添加下载列表的数据变化监听
        addDownloadStateChangeListener();
    }

    @Override
    public void stopAllDownload() {
        downloadRealmManager.stopAllDownload();
    }

    @Override
    public void startAllDownload() {
        downloadRealmManager.startAllDownload();
    }

    @Override
    public boolean hasDownloading() {
        return downloadRealmManager.hasDownloading();
    }

    @Override
    public boolean isAllDownloading() {
        return downloadRealmManager.isAllDownloading();
    }

    @Override
    public void addDownloadStateChangeListener() {
        downloadRealmManager.getDownloading(new OnDownloadTaskListener() {
            @Override
            public void downloadTask(List<DownloadModel> downloadModels) {
                for (DownloadModel downloadModel : downloadModels) {
                    int state = downloadModel.getState();
                    if (state != DownloadState.DOWNLOAD_WAITING && state != DownloadState.DOWNLOAD_WAITING) {
                        view.setDownloadingState(false);
                        return;
                    }
                }
                view.setDownloadingState(downloadModels.size() > 0 ? true : false);
            }
        });
    }
}