package com.jtech.imaging.common;

/**
 * 下载状态
 * Created by jianghan on 2016/10/21.
 */

public class DownloadState {
    /*下载中*/
    public static final int DOWNLOADING = 0x01;
    /*队列中等待下载*/
    public static final int DOWNLOAD_WAITING = 0x10;//开启一个下载任务会将任务状态修改为等待，加入下载队列之后，开始下载的任务才会被修改为下载中
    /*默认状态*/
    public static final int DOWNLOAD_STOP = 0x02;


    /*下载完成*/
    public static final int DOWNLOADED = 0x03;
    /*下载完成，文件不在目标位置*/
    public static final int DOWNLOADED_NOT_FOUND = 0x04;


    /*下载失败,未知原因*/
    public static final int DOWNLOAD_FAIL_UNKNOWN = 0x07;
    /*下载失败，网络状态变化*/
    public static final int DOWNLOAD_FAIL_NETWORK_CHANGE = 0x08;
    /*下载失败，网络错误*/
    public static final int DOWNLOAD_FAIL_NETWORK_ERROR = 0x09;

    /*下载任务删除*/
    public static final int DOWNLOAD_DELETE = 0x11;
    /*全部开始*/
    public static final int DOWNLOAD_START_ALL = 0x12;
    /*全部停止*/
    public static final int DOWNLOAD_STOP_ALL = 0x13;
}