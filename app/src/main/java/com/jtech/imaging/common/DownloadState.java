package com.jtech.imaging.common;

/**
 * 下载状态
 * Created by jianghan on 2016/10/21.
 */

public class DownloadState {
    /*下载中*/
    public static final int DOWNLOADING = 0x01;
    /*默认状态*/
    public static final int DOWNLOAD_STOP = 0x02;


    /*下载完成*/
    public static final int DOWNLOADED = 0x03;
    /*下载完成，文件不在目标位置*/
    public static final int DOWNLOADED_NOT_FOUND = 0x04;


    /*下载失败,未知原因*/
    public static final int DOWNLOAD_FAIL_UNKNOWN = 0x07;
    /*下载失败，网络状态变化*/
    public static final int DOWNLOAD_FAIL_INTENT_CHANGE = 0x08;
    /*下载失败，网络错误*/
    public static final int DOWNLOAD_FAIL_INTENT_ERROR = 0x09;
}