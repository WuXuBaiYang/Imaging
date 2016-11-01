package com.jtech.imaging.event;

import java.util.List;

/**
 * 下载事件，视图操作
 * Created by jianghan on 2016/10/27.
 */

public class DownloadScheduleEvent {
    private List<Schedule> schedules;

    public DownloadScheduleEvent(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    /**
     * 下载进度
     */
    public class Schedule {
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
}