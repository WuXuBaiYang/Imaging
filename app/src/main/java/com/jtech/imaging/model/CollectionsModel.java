package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

/**
 * 用户收藏对象
 * Created by jianghan on 2016/8/31.
 */
public class CollectionsModel extends BaseModel {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("published_at")
    private String publishedAt;
    @SerializedName("curated")
    private boolean curated;
    @SerializedName("featured")
    private boolean featured;
    @SerializedName("total_photos")
    private int totalPhotos;
    @SerializedName("private")
    private boolean privateX;
    @SerializedName("share_key")
    private String shareKey;
    @SerializedName("cover_photo")
    private CoverPhotoModel coverPhoto;
    @SerializedName("user")
    private UserModel user;
    @SerializedName("links")
    private LinksModel links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isCurated() {
        return curated;
    }

    public void setCurated(boolean curated) {
        this.curated = curated;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public boolean isPrivateX() {
        return privateX;
    }

    public void setPrivateX(boolean privateX) {
        this.privateX = privateX;
    }

    public String getShareKey() {
        return shareKey;
    }

    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }

    public CoverPhotoModel getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(CoverPhotoModel coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public LinksModel getLinks() {
        return links;
    }

    public void setLinks(LinksModel links) {
        this.links = links;
    }
}