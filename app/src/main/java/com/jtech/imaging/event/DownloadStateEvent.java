package com.jtech.imaging.event;

/**
 * 下载状态事件
 * Created by jianghan on 2016/10/27.
 */

public class DownloadStateEvent {
    private String id;
    private int downloadState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }
}