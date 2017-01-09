package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.model.event.DownloadEvent;
import com.jtech.imaging.mvp.contract.DownloadContract;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.util.Bus;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * 下载管理，P类
 * Created by jianghan on 2016/10/19.
 */

public class DownloadPresenter implements DownloadContract.Presenter, RealmChangeListener<RealmResults<DownloadModel>> {
    private Context context;
    private DownloadContract.View view;
    private DownloadRealmManager downloadRealmManager;

    public DownloadPresenter(Context context, DownloadContract.View view) {
        this.context = context;
        this.view = view;
        //实例化数据库操作
        downloadRealmManager = new DownloadRealmManager();
    }

    @Override
    public void stopAllDownload() {
        downloadRealmManager.stopAllDownload();
        //发送任务停止消息
        Bus.get().post(new DownloadEvent.StateEvent(0, DownloadState.DOWNLOAD_STOP_ALL));
    }

    @Override
    public void startAllDownload() {
        downloadRealmManager.startAllDownload();
        //发送任务开始消息
        Bus.get().post(new DownloadEvent.StateEvent(0, DownloadState.DOWNLOAD_START_ALL));
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
        downloadRealmManager
                .getDownloading()
                .addChangeListener(this);
    }

    @Override
    public void onChange(RealmResults<DownloadModel> element) {
        boolean isAllDownloading = element.size() > 0 ? true : false;
        for (DownloadModel downloadModel : element) {
            int state = downloadModel.getState();
            if (state != DownloadState.DOWNLOAD_WAITING && state != DownloadState.DOWNLOADING) {
                isAllDownloading = false;
                break;
            }
        }
        view.setDownloadingState(isAllDownloading);
    }
}