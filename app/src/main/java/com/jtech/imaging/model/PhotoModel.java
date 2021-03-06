package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

import java.util.List;

/**
 * 图片对象
 * Created by jianghan on 2016/8/31.
 */
public class PhotoModel extends BaseModel {

    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("downloads")
    private int downloads;
    @SerializedName("exif")
    private ExifModel exif;
    @SerializedName("location")
    private LocationModel location;
    @SerializedName("user")
    private UserModel user;
    @SerializedName("current_user_collections")
    private List<CollectionsModel> currentUserCollections;
    @SerializedName("categories")
    private List<CategoryModel> categories;
    private String id;
    private int width;
    private int height;
    private String color;
    private int likes;
    @SerializedName("liked_by_user")
    private boolean likedByUser;
    private UrlsModel urls;
    private LinksModel links;

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

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public ExifModel getExif() {
        return exif;
    }

    public void setExif(ExifModel exif) {
        this.exif = exif;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<CollectionsModel> getCurrentUserCollections() {
        return currentUserCollections;
    }

    public void setCurrentUserCollections(List<CollectionsModel> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }
}