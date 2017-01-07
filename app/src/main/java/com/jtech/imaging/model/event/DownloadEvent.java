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

        public StateEvent(long id, int state) {
            this.id = id;
            this.state = state;
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
    }
}