package com.jtech.imaging.event;

/**
 * 下载状态监听
 * Created by jianghan on 2016/11/1.
 */

public class DownloadStateEvent {
    private int state;

    public DownloadStateEvent(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}