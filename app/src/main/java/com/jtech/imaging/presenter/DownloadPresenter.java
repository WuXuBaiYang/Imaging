package com.jtech.imaging.presenter;

import android.content.Context;

import com.jtech.imaging.contract.DownloadContract;

/**
 * 下载管理，P类
 * Created by jianghan on 2016/10/19.
 */

public class DownloadPresenter implements DownloadContract.Presenter {
    private Context context;
    private DownloadContract.View view;

    public DownloadPresenter(Context context, DownloadContract.View view) {
        this.context = context;
        this.view = view;
    }
}