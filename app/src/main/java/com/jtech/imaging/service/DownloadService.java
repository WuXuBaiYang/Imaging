package com.jtech.imaging.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.model.event.DownloadEvent;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.util.Bus;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import org.greenrobot.eventbus.Subscribe;

/**
 * 下载服务
 * Created by jianghan on 2016/10/27.
 */

public class DownloadService extends Service {
    private BaseDownloadTask downloadTask;
    private DownloadRealmManager downloadRealmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        //上车
        Bus.getOn(this);
        //初始化task
        downloadTask = FileDownloader.getImpl().create("");
        //实例化数据库操作对象
        downloadRealmManager = new DownloadRealmManager();
    }

    @Subscribe
    public void onDownloadStateChange(DownloadEvent.StateEvent event) {
        switch (event.getState()) {
            case DownloadState.DOWNLOAD_WAITING://任务等待
                if (!downloadTask.isRunning()) {
                    //没有任务在运行则开启这个任务
                    startDownload(downloadRealmManager.getDownload(event.getId()));
                }
                break;
            case DownloadState.DOWNLOAD_START_ALL://全部任务开始
                if (!downloadTask.isRunning()) {
                    //如果没有任务在运行，则找到第一个任务并开启
                    startDownload(downloadRealmManager.getFirstDownload());
                }
                break;
            case DownloadState.DOWNLOAD_STOP://任务暂停
                if (downloadTask.isRunning() && downloadTask.getId() == event.getId()) {
                    //如果需要暂停的任务是当前正在运行的，则停止
                    downloadTask.pause();
                }
                break;
            case DownloadState.DOWNLOAD_STOP_ALL://全部任务暂停
                if (downloadTask.isRunning()) {
                    //如果有任务在运行，则停止
                    downloadTask.pause();
                }
                break;
            case DownloadState.DOWNLOAD_DELETE://任务删除
                if (downloadTask.isRunning() && downloadTask.getId() == event.getId()) {
                    //如果需要删除的任务是当前正在运行的，则停止
                    downloadTask.pause();
                }
                break;
        }
    }

    /**
     * 开始一个下载
     *
     * @param downloadModel
     */
    private void startDownload(DownloadModel downloadModel) {
        if (null != downloadModel) {
            //构造一个task
            downloadTask = FileDownloader
                    .getImpl()
                    .create(downloadModel.getUrl())
                    .setPath(downloadModel.getPath())
                    .setListener(new DownloadListener(downloadModel.getId()));
//                    .setWifiRequired(true);
            //开始下载
            downloadTask.start();
        }
    }

    /**
     * 下载监听
     */
    private class DownloadListener extends FileDownloadListener {
        private long downloadId;

        public DownloadListener(long downloadId) {
            this.downloadId = downloadId;
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            //将状态设置为下载中
            downloadRealmManager.downloading(downloadId);
            // TODO: 2017/1/9 显示通知栏
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            //更新下载文件的进度
            downloadRealmManager.updataDownloadingProgress(downloadId, soFarBytes, totalBytes);
            // TODO: 2017/1/9 更新通知栏
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            //设置下载任务为已完成
            downloadRealmManager.finishDownload(downloadId);
            // 找到下一个下载任务
            findNextDownloadTask();
            // TODO: 2017/1/9 取消通知栏消息
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            // 找到下一个下载任务
            findNextDownloadTask();
            // TODO: 2017/1/9 取消通知栏消息
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            //设置下载任务为未知错误
            downloadRealmManager.downloadFailUnknown(downloadId);
            // 找到下一个下载任务
            findNextDownloadTask();
            // TODO: 2017/1/9 取消通知栏消息
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            //设置下载任务为错误
            downloadRealmManager.downloadFailUnknown(downloadId);
            // 找到下一个下载任务
            findNextDownloadTask();
            // TODO: 2017/1/9 取消通知栏消息
        }

        /**
         * 找到下一个下载任务
         */
        private void findNextDownloadTask() {
            //找到下载列表中第一个状态为等待的任务
            DownloadModel downloadModel = downloadRealmManager.getFirstWaitingDownload();
            if (null != downloadModel) {
                Bus.get().post(new DownloadEvent.StateEvent(downloadModel.getId(), DownloadState.DOWNLOAD_WAITING));
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //下车
        Bus.getOff(this);
        //将所有下载中和等待中的任务置为停止状态
        downloadRealmManager.stopAllDownloadingTask();
    }
}