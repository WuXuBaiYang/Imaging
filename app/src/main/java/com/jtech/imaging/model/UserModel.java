package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

/**
 * 用户信息
 * Created by jianghan on 2016/8/31.
 */
public class UserModel extends BaseModel {

    private int downloads;
    @SerializedName("badge")
    private BadgeModel badge;
    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("name")
    private String name;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("portfolio_url")
    private Object portfolioUrl;
    @SerializedName("bio")
    private String bio;
    @SerializedName("location")
    private String location;
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

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public BadgeModel getBadge() {
        return badge;
    }

    public void setBadge(BadgeModel badge) {
        this.badge = badge;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Object getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(Object portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
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

    public LinksModel getLinks() {
        return links;
    }

    public void setLinks(LinksModel links) {
        this.links = links;
    }
}