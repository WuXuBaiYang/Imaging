package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

import java.util.List;

/**
 * 图片对象
 * Created by jianghan on 2016/8/31.
 */
public class PhotoModel extends BaseModel {

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
    @SerializedName("current_user_collections")
    private List<CurrentUserCollectionsModel> currentUserCollections;
    @SerializedName("categories")
    private List<?> categories;

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

    public List<CurrentUserCollectionsModel> getCurrentUserCollections() {
        return currentUserCollections;
    }

    public void setCurrentUserCollections(List<CurrentUserCollectionsModel> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
    }

    public List<?> getCategories() {
        return categories;
    }

    public void setCategories(List<?> categories) {
        this.categories = categories;
    }

    public static class UserModel {
        @SerializedName("id")
        private String id;
        @SerializedName("username")
        private String username;
        @SerializedName("name")
        private String name;
        @SerializedName("portfolio_url")
        private Object portfolioUrl;
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

        public Object getPortfolioUrl() {
            return portfolioUrl;
        }

        public void setPortfolioUrl(Object portfolioUrl) {
            this.portfolioUrl = portfolioUrl;
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

    public static class UrlsModel {
        @SerializedName("raw")
        private String raw;
        @SerializedName("full")
        private String full;
        @SerializedName("regular")
        private String regular;
        @SerializedName("small")
        private String small;
        @SerializedName("thumb")
        private String thumb;

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getRegular() {
            return regular;
        }

        public void setRegular(String regular) {
            this.regular = regular;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

    public static class LinksModel {
        @SerializedName("self")
        private String self;
        @SerializedName("html")
        private String html;
        @SerializedName("download")
        private String download;
        @SerializedName("download_location")
        private String downloadLocation;

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

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public String getDownloadLocation() {
            return downloadLocation;
        }

        public void setDownloadLocation(String downloadLocation) {
            this.downloadLocation = downloadLocation;
        }
    }

    public static class CurrentUserCollectionsModel {
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

        public static class CoverPhotoModel {
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
            private List<CategoriesModel> categories;

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

            public List<CategoriesModel> getCategories() {
                return categories;
            }

            public void setCategories(List<CategoriesModel> categories) {
                this.categories = categories;
            }

            public static class CategoriesModel {
                @SerializedName("id")
                private int id;
                @SerializedName("title")
                private String title;
                @SerializedName("photo_count")
                private int photoCount;
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

                public int getPhotoCount() {
                    return photoCount;
                }

                public void setPhotoCount(int photoCount) {
                    this.photoCount = photoCount;
                }

                public LinksModel getLinks() {
                    return links;
                }

                public void setLinks(LinksModel links) {
                    this.links = links;
                }
            }
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
}