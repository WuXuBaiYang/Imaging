package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.model.event.DownloadEvent;
import com.jtech.imaging.mvp.contract.DownloadingContract;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.util.Bus;

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
    private DownloadRealmManager downloadRealmManager;

    private RealmResults downloadingResults;

    public DownloadingPresenter(Context context, DownloadingContract.View view) {
        this.context = context;
        this.view = view;
        //实例化数据库操作
        downloadRealmManager = new DownloadRealmManager();
        //获取下载列表
        downloadingResults = downloadRealmManager.getDownloading();
    }

    @Override
    public void addDownloadingListener() {
        downloadingResults.addChangeListener(this);
    }

    @Override
    public void startDownload(final long id) {
        downloadRealmManager.startDownload(id, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //发送任务开始消息
                Bus.get().post(new DownloadEvent.StateEvent(id, DownloadState.DOWNLOAD_QUEUE));
            }
        });
    }

    @Override
    public void stopDownload(final long id) {
        downloadRealmManager.stopDownload(id, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //发送任务停止消息
                Bus.get().post(new DownloadEvent.StateEvent(id, DownloadState.DOWNLOAD_STOP));
            }
        });
    }

    @Override
    public void deleteDownload(final long id) {
        downloadRealmManager.removeDownload(id, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //发送任务删除消息
                Bus.get().post(new DownloadEvent.StateEvent(id, DownloadState.DOWNLOAD_DELETE));
            }
        });
    }

    @Override
    public void onChange(RealmResults<DownloadModel> element) {
        element.addChangeListener(this);
        view.downloadingList(Realm.getDefaultInstance().copyFromRealm(element));
    }

    @Override
    public boolean isIndeterminate(long id) {
        return downloadRealmManager.isIndeterminateDownload(id);
    }
}