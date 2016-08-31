package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;

/**
 * 状态
 * Created by jianghan on 2016/8/31.
 */
public class StatsModel {

    private int totalPhotos;
    private int photoDownloads;
    private int batchDownloads;

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public int getPhotoDownloads() {
        return photoDownloads;
    }

    public void setPhotoDownloads(int photoDownloads) {
        this.photoDownloads = photoDownloads;
    }

    public int getBatchDownloads() {
        return batchDownloads;
    }

    public void setBatchDownloads(int batchDownloads) {
        this.batchDownloads = batchDownloads;
    }
}