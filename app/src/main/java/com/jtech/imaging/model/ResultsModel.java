package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

import java.util.List;

/**
 * 搜索的结果对象
 */
public class ResultsModel extends BaseModel {
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private Object description;
    @SerializedName("published_at")
    private String publishedAt;
    @SerializedName("curated")
    private boolean curated;
    @SerializedName("featured")
    private boolean featured;
    @SerializedName("private")
    private boolean privateX;
    @SerializedName("share_key")
    private String shareKey;
    @SerializedName("cover_photo")
    private CoverPhotoModel coverPhoto;
    @SerializedName("user")
    private UserModel user;
    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("name")
    private String name;
    @SerializedName("portfolio_url")
    private String portfolioUrl;
    @SerializedName("total_likes")
    private int totalLikes;
    @SerializedName("total_photos")
    private int totalPhotos;
    @SerializedName("total_collections")
    private int totalCollections;
    @SerializedName("profile_image")
    private ProfileImageModel profileImage;
    @SerializedName("links")
    private LinksModel links;
    @SerializedName("photos")
    private List<PhotoModel> photos;
    private int width;
    private int height;
    private String color;
    private UrlsModel urls;

    public UrlsModel getUrls() {
        return urls;
    }

    public void setUrls(UrlsModel urls) {
        this.urls = urls;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public int getTotalCollections() {
        return totalCollections;
    }

    public void setTotalCollections(int totalCollections) {
        this.totalCollections = totalCollections;
    }

    public ProfileImageModel getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImageModel profileImage) {
        this.profileImage = profileImage;
    }

    public List<PhotoModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoModel> photos) {
        this.photos = photos;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
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