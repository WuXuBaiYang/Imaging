package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.mvp.contract.DownloadedContract;
import com.jtech.imaging.realm.DownloadRealmManager;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * 已下载页面P类
 * Created by JTech on 2017/1/6.
 */
public class DownloadedPresenter implements DownloadedContract.Presenter, RealmChangeListener<RealmResults<DownloadModel>> {
    private Context context;
    private DownloadedContract.View view;

    public DownloadedPresenter(Context context, DownloadedContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getDownloadedList() {
        DownloadRealmManager.get().getDownloaded(this);
    }

    @Override
    public void deleteDownloaded(long id) {
        DownloadRealmManager.get().removeDownload(id);
    }

    @Override
    public void redownload(long id) {
        DownloadRealmManager.get().startDownload(id);
    }

    @Override
    public void removeListener() {
        DownloadRealmManager.get().removeListener(this);
    }

    @Override
    public void onChange(RealmResults<DownloadModel> element) {
        view.downloadedList(Realm.getDefaultInstance().copyFromRealm(element));
    }
}