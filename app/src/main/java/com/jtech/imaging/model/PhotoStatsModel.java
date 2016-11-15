package com.jtech.imaging.model;

import com.jtechlib.model.BaseModel;

/**
 * 照片属性
 * Created by jianghan on 2016/8/31.
 */
public class PhotoStatsModel extends BaseModel {

    private int downloads;
    private int likes;
    private int views;
    private String url;
    private LinksModel links;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public LinksModel getLinks() {
        return links;
    }

    public void setLinks(LinksModel links) {
        this.links = links;
    }
}