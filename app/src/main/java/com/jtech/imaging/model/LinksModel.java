package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

/**
 * 链接对象
 * Created by jianghan on 2016/11/15.
 */

public class LinksModel extends BaseModel {
    @SerializedName("related")
    private String related;
    @SerializedName("photos")
    private String photos;
    @SerializedName("likes")
    private String likes;
    @SerializedName("portfolio")
    private String portfolio;
    @SerializedName("download_location")
    private String downloadLocation;
    private String self;
    private String html;
    private String download;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getRelated() {
        return related;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public String getDownloadLocation() {
        return downloadLocation;
    }

    public void setDownloadLocation(String downloadLocation) {
        this.downloadLocation = downloadLocation;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }
}