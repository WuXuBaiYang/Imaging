package com.jtech.imaging.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 下载服务
 * Created by jianghan on 2016/10/27.
 */

public class DownloadService extends Service {
    private DownloadBinder downloadBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        //实例化binder
        this.downloadBinder = new DownloadBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }

    /**
     * 下载服务的binder
     */
    public class DownloadBinder extends Binder {

    }
}