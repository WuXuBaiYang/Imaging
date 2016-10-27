package com.jtech.imaging.event;

/**
 * 下载事件，视图操作
 * Created by jianghan on 2016/10/27.
 */

public class DownloadEvent {
    private String id;
    private int maxSize;
    private int downloadSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(int downloadSize) {
        this.downloadSize = downloadSize;
    }
}