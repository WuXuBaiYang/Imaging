package com.jtech.imaging.model.event;

/**
 * 下载事件
 * Created by JTech on 2017/1/6.
 */

public class DownloadEvent {
    /**
     * 状态变化事件
     */
    public static class StateEvent {
        private long id;
        private int state;
        private boolean isAllDownloadStart;
        private boolean hasDownload;

        public StateEvent(long id, int state, boolean isAllDownloadStart, boolean hasDownload) {
            this.id = id;
            this.state = state;
            this.isAllDownloadStart = isAllDownloadStart;
            this.hasDownload = hasDownload;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public boolean isAllDownloadStart() {
            return isAllDownloadStart;
        }

        public void setAllDownloadStart(boolean allDownloadStart) {
            isAllDownloadStart = allDownloadStart;
        }

        public boolean hasDownload() {
            return hasDownload;
        }

        public void setHasDownload(boolean hasDownload) {
            this.hasDownload = hasDownload;
        }
    }
}