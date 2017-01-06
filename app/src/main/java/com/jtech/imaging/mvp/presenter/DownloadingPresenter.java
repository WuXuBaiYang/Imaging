package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.mvp.contract.DownloadContract;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.realm.listener.OnDownloadTaskListener;

import java.util.List;

/**
 * 下载列表P类
 * Created by jianghan on 2016/11/17.
 */

public class DownloadingPresenter implements DownloadContract.DownloadingPresenter {
    private Context context;
    private DownloadContract.DownloadingView view;
    private DownloadRealmManager downloadRealmManager;

    public DownloadingPresenter(Context context, DownloadContract.DownloadingView view) {
        this.context = context;
        this.view = view;
        //实例化下载数据库管理
        downloadRealmManager = new DownloadRealmManager();
    }

    @Override
    public void getDownloadingTask() {
        downloadRealmManager.getDownloading(new OnDownloadTaskListener() {
            @Override
            public void downloadTask(List<DownloadModel> downloadModels) {
                view.downloadTask(downloadModels);
            }
        });
    }
}