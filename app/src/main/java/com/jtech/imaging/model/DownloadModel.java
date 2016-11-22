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
    private String md5;
    private long size;
    private String url;
    private int state = DownloadState.DOWNLOAD_STOP;
    private long downloadSize;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadModel that = (DownloadModel) o;

        if (id != that.id) return false;
        if (size != that.size) return false;
        if (state != that.state) return false;
        if (downloadSize != that.downloadSize) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (md5 != null ? !md5.equals(that.md5) : that.md5 != null) return false;
        return url != null ? url.equals(that.url) : that.url == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (md5 != null ? md5.hashCode() : 0);
        result = 31 * result + (int) (size ^ (size >>> 32));
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + state;
        result = 31 * result + (int) (downloadSize ^ (downloadSize >>> 32));
        return result;
    }
}