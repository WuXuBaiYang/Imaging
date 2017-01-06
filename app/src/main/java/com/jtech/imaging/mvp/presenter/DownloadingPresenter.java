package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.mvp.contract.DownloadingContract;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.realm.listener.OnDownloadTaskListener;

import java.util.List;

/**
 * 下载页面P类
 * Created by JTech on 2017/1/6.
 */
public class DownloadingPresenter implements DownloadingContract.Presenter {
    private Context context;
    private DownloadingContract.View view;
    private DownloadRealmManager downloadRealmManager;

    public DownloadingPresenter(Context context, DownloadingContract.View view) {
        this.context = context;
        this.view = view;
        //实例化数据库操作
        downloadRealmManager = new DownloadRealmManager();
    }

    @Override
    public void getDownloadingList() {
        downloadRealmManager.getDownloading(new OnDownloadTaskListener() {
            @Override
            public void downloadTask(List<DownloadModel> downloadModels) {
                view.downloadingList(downloadModels);
            }
        });
    }
}