package com.jtech.imaging.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jtech.imaging.R;
import com.jtech.imaging.common.DownloadState;
import com.jtech.imaging.model.DownloadModel;
import com.jtech.imaging.model.event.DownloadEvent;
import com.jtech.imaging.realm.DownloadRealmManager;
import com.jtech.imaging.util.Bus;
import com.jtechlib.Util.JNotify;
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
            case DownloadState.DOWNLOAD_QUEUE://任务加入队列
                if (!downloadTask.isRunning()) {
                    //没有任务在运行则开启这个任务
                    startDownload(downloadRealmManager.getDownload(event.getId()));
                }
                break;
            case DownloadState.DOWNLOAD_START_ALL://全部任务开始
                if (!downloadTask.isRunning()) {
                    //如果没有任务在运行，则找到第一个任务并开启
                    startDownload(downloadRealmManager.getFirstDownloadWaiting());
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
            case DownloadState.DOWNLOAD_RESUME://下载恢复
                if (!downloadTask.isRunning()) {
                    //查找是否有下载中任务，如果有则恢复下载
                    DownloadModel downloadModel = downloadRealmManager.getFirstDownloading();
                    //如果没有下载中任务，则继续查找第一个等待任务
                    if (null == downloadModel) {
                        downloadModel = downloadRealmManager.getFirstDownloadWaiting();
                    }
                    //开始任务
                    startDownload(downloadModel);
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
                    .setListener(new DownloadListener(downloadModel))
                    .setWifiRequired(true);
            //开始下载
            downloadTask.start();
        }
    }

    /**
     * 下载监听
     */
    private class DownloadListener extends FileDownloadListener {
        private DownloadModel downloadModel;
        private boolean isDownloading;

        public DownloadListener(DownloadModel downloadModel) {
            this.downloadModel = downloadModel;
        }

        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            //设置为不确定状态
            downloadRealmManager.downloadIndeterminate(downloadModel.getId());
            //显示通知栏
            JNotify.build(getApplicationContext())
                    .setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setTicker(downloadModel.getName() + " downloading")
                    .setContentTitle(downloadModel.getName())
                    .setProgress(0, 0, true)
                    .notify(task.getId());
        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            if (!isDownloading) {
                //将状态设置为下载中
                downloadRealmManager.downloading(downloadModel.getId());
            }
            //更新下载文件的进度
            downloadRealmManager.updataDownloadingProgress(downloadModel.getId(), soFarBytes, totalBytes);
            //更新下载进度
            JNotify.build(getApplicationContext())
                    .setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setTicker(downloadModel.getName() + " downloading")
                    .setContentTitle(downloadModel.getName())
                    .setProgress(totalBytes, soFarBytes, false)
                    .notify(task.getId());
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            //设置下载任务为已完成
            downloadRealmManager.finishDownload(downloadModel.getId());
            // 找到下一个下载任务
            findNextDownloadTask();
            //设置消息为可以取消
            JNotify.build(getApplicationContext())
                    .setOngoing(false)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setTicker(downloadModel.getName() + " download complete")
                    .setContentTitle(downloadModel.getName())
                    .setContentText(" download complete")
                    .notify(task.getId());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            // 找到下一个下载任务
            findNextDownloadTask();
            //取消消息
            JNotify.build(getApplicationContext())
                    .cancel(task.getId());
        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            //设置下载任务为未知错误
            downloadRealmManager.downloadError(downloadModel.getId());
            // 找到下一个下载任务
            findNextDownloadTask();
            //设置消息为可以取消
            JNotify.build(getApplicationContext())
                    .setOngoing(false)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setTicker(downloadModel.getName() + " download error")
                    .setContentTitle(downloadModel.getName())
                    .setContentText(" download error")
                    .notify(task.getId());
        }

        @Override
        protected void warn(BaseDownloadTask task) {
            //设置下载任务为错误
            downloadRealmManager.downloadError(downloadModel.getId());
            // 找到下一个下载任务
            findNextDownloadTask();
            //设置消息为可以取消
            JNotify.build(getApplicationContext())
                    .setOngoing(false)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setTicker(downloadModel.getName() + " download error")
                    .setContentTitle(downloadModel.getName())
                    .setContentText(" download error")
                    .notify(task.getId());
        }

        /**
         * 找到下一个下载任务
         */
        private void findNextDownloadTask() {
            //找到下载列表中第一个状态为等待的任务
            DownloadModel downloadModel = downloadRealmManager.getFirstDownloadWaiting();
            if (null != downloadModel) {
                Bus.get().post(new DownloadEvent.StateEvent(downloadModel.getId(), DownloadState.DOWNLOAD_QUEUE));
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
    }
}