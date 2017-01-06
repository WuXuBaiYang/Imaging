package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.mvp.contract.DownloadContract;
import com.jtech.imaging.realm.DownloadRealmManager;

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
}