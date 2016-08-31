package com.jtech.imaging.model;

/**
 * 用户信息
 * Created by jianghan on 2016/8/31.
 */
public class UserModel {

    private String username;
    private String firstName;
    private String lastName;
    private Object portfolioUrl;
    private String bio;
    private String location;
    private int totalLikes;
    private int totalPhotos;
    private int totalCollections;
    private int downloads;
    private int uploadsRemaining;
    private String instagramUsername;
    private String email;
    private LinksModel links;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getUploadsRemaining() {
        return uploadsRemaining;
    }

    public void setUploadsRemaining(int uploadsRemaining) {
        this.uploadsRemaining = uploadsRemaining;
    }

    public String getInstagramUsername() {
        return instagramUsername;
    }

    public void setInstagramUsername(String instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LinksModel getLinks() {
        return links;
    }

    public void setLinks(LinksModel links) {
        this.links = links;
    }

    public static class LinksModel {
        private String self;
        private String html;
        private String photos;
        private String likes;

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
    }
}