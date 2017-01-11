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
    private DownloadRealmManager downloadRealmManager;

    private RealmResults downloadedResults;

    public DownloadedPresenter(Context context, DownloadedContract.View view) {
        this.context = context;
        this.view = view;
        //实例化数据库操作
        downloadRealmManager = new DownloadRealmManager();
        //获取已下载列表
        downloadedResults = downloadRealmManager.getDownloaded();
    }

    @Override
    public void addDownloadedListener() {
        downloadedResults.addChangeListener(this);
    }

    @Override
    public void deleteDownloaded(long id) {
        downloadRealmManager.removeDownload(id, null);
    }

    @Override
    public void onChange(RealmResults<DownloadModel> element) {
        view.downloadedList(Realm.getDefaultInstance().copyFromRealm(element));
    }
}