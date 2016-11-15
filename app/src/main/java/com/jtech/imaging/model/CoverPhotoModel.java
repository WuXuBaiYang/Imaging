package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

import java.util.List;

/**
 * 封面图片
 * Created by jianghan on 2016/11/15.
 */

public class CoverPhotoModel extends BaseModel {

    @SerializedName("id")
    private String id;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;
    @SerializedName("color")
    private String color;
    @SerializedName("likes")
    private int likes;
    @SerializedName("liked_by_user")
    private boolean likedByUser;
    @SerializedName("user")
    private UserModel user;
    @SerializedName("urls")
    private UrlsModel urls;
    @SerializedName("links")
    private LinksModel links;
    @SerializedName("categories")
    private List<CategoryModel> categories;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UrlsModel getUrls() {
        return urls;
    }

    public void setUrls(UrlsModel urls) {
        this.urls = urls;
    }

    public LinksModel getLinks() {
        return links;
    }

    public void setLinks(LinksModel links) {
        this.links = links;
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }
}