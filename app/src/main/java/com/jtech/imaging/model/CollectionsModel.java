package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;

/**
 * 用户收藏对象
 * Created by jianghan on 2016/8/31.
 */
public class CollectionsModel {

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
    private Object coverPhoto;
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

    public Object getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(Object coverPhoto) {
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

    public static class UserModel {
        @SerializedName("id")
        private String id;
        @SerializedName("username")
        private String username;
        @SerializedName("name")
        private String name;
        @SerializedName("portfolio_url")
        private String portfolioUrl;
        @SerializedName("bio")
        private String bio;
        @SerializedName("profile_image")
        private ProfileImageModel profileImage;
        @SerializedName("links")
        private LinksModel links;

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

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
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

        public static class ProfileImageModel {
            @SerializedName("small")
            private String small;
            @SerializedName("medium")
            private String medium;
            @SerializedName("large")
            private String large;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }
        }

        public static class LinksModel {
            @SerializedName("self")
            private String self;
            @SerializedName("html")
            private String html;
            @SerializedName("photos")
            private String photos;
            @SerializedName("likes")
            private String likes;
            @SerializedName("portfolio")
            private String portfolio;

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
        }
    }

    public static class LinksModel {
        @SerializedName("self")
        private String self;
        @SerializedName("html")
        private String html;
        @SerializedName("photos")
        private String photos;
        @SerializedName("related")
        private String related;

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

        public String getRelated() {
            return related;
        }

        public void setRelated(String related) {
            this.related = related;
        }
    }
}