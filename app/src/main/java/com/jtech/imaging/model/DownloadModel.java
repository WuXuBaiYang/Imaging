package com.jtech.imaging.model;

import com.jtech.imaging.common.DownloadState;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 下载数据对象
 * Created by jianghan on 2016/10/21.
 */

public class DownloadModel extends RealmObject implements Serializable {
    @PrimaryKey
    private long id;
    private String name;
    private String path;
    private String color;
    private int width;
    private int height;
    private String md5;
    private long size;
    private String url;
    private int state = DownloadState.DOWNLOAD_STOP;
    private long downloadSize;

    public DownloadModel() {
    }

    public DownloadModel(long id, String name, String color, int width, int height, String md5, String url, String path) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.width = width;
        this.height = height;
        this.md5 = md5;
        this.url = url;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public long getDownloadSize() {
        return downloadSize;
    }

    public void setDownloadSize(long downloadSize) {
        this.downloadSize = downloadSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}