package com.jtech.imaging.common;

/**
 * 下载状态
 * Created by jianghan on 2016/10/21.
 */

public class DownloadState {
    //下载中
    public static final int DOWNLOADING = 343;
    //队列中
    public static final int DOWNLOAD_QUEUE = 339;
    //下载暂停，默认状态
    public static final int DOWNLOAD_STOP = 814;
    //不确定状态
    public static final int DOWNLOAD_INDETERMINATE = 722;

    //下载完成
    public static final int DOWNLOADED = 319;

    //下载失败
    public static final int DOWNLOAD_ERROR = 600;

    //下载任务删除
    public static final int DOWNLOAD_DELETE = 572;
    //全部开始
    public static final int DOWNLOAD_START_ALL = 35;
    //全部停止
    public static final int DOWNLOAD_STOP_ALL = 879;
    //恢复下载
    public static final int DOWNLOAD_RESUME = 341;
}