package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.mvp.contract.DownloadContract;
import com.jtech.imaging.realm.DownloadRealmManager;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * 下载管理，P类
 * Created by jianghan on 2016/10/19.
 */

public class DownloadPresenter implements DownloadContract.Presenter, RealmChangeListener<RealmResults<DownloadModel>> {
    private Context context;
    private DownloadContract.View view;

    public DownloadPresenter(Context context, DownloadContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void stopAllDownload() {
        DownloadRealmManager.get().stopAllDownload();
    }

    @Override
    public void startAllDownload() {
        DownloadRealmManager.get().startAllDownload();
    }

    @Override
    public boolean hasDownloading() {
        return DownloadRealmManager.get().hasDownloading();
    }

    @Override
    public boolean isAllDownloading() {
        return DownloadRealmManager.get().isAllDownloading();
    }

    @Override
    public void addDownloadStateChangeListener() {
        DownloadRealmManager.get().getDownloading(this);
    }

    @Override
    public void removeListener() {
        DownloadRealmManager.get().removeListener(this);
    }

    @Override
    public void onChange(RealmResults<DownloadModel> element) {
        for (DownloadModel downloadModel : element) {
            int state = downloadModel.getState();
            if (state != DownloadState.DOWNLOAD_WAITING && state != DownloadState.DOWNLOAD_WAITING) {
                view.setDownloadingState(false);
                return;
            }
        }
        view.setDownloadingState(element.size() > 0 ? true : false);
    }
}