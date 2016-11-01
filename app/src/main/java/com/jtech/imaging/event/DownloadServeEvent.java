package com.jtech.imaging.event;

/**
 * 操作下载服务事件
 * Created by jianghan on 2016/11/1.
 */

public class DownloadServeEvent {
    private boolean download;

    public DownloadServeEvent(boolean download) {
        this.download = download;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }
}