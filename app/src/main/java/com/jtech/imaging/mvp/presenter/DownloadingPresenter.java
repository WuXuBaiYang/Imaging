package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.mvp.contract.DownloadingContract;
import com.jtech.imaging.realm.DownloadRealmManager;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * 下载页面P类
 * Created by JTech on 2017/1/6.
 */
public class DownloadingPresenter implements DownloadingContract.Presenter, RealmChangeListener<RealmResults<DownloadModel>> {
    private Context context;
    private DownloadingContract.View view;

    public DownloadingPresenter(Context context, DownloadingContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getDownloadingList() {
        DownloadRealmManager.get().getDownloading(this);
    }

    @Override
    public void startDownload(long id) {
        DownloadRealmManager.get().startDownload(id);
    }

    @Override
    public void stopDownload(long id) {
        DownloadRealmManager.get().stopDownload(id);
    }

    @Override
    public void deleteDownload(long id) {
        DownloadRealmManager.get().removeDownload(id);
    }

    @Override
    public void removeListener() {
        DownloadRealmManager.get().removeListener(this);
    }

    @Override
    public void onChange(RealmResults<DownloadModel> element) {
        view.downloadingList(Realm.getDefaultInstance().copyFromRealm(element));
    }
}