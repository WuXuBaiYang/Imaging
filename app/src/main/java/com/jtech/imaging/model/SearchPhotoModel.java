package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 图片搜索结果
 * Created by jianghan on 2016/9/5.
 */
public class SearchPhotoModel {

    @SerializedName("total")
    private int total;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<ResultsModel> results;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<ResultsModel> getResults() {
        return results;
    }

    public void setResults(List<ResultsModel> results) {
        this.results = results;
    }

    public static class ResultsModel {
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
        private List<?> currentUserCollections;
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

        public List<?> getCurrentUserCollections() {
            return currentUserCollections;
        }

        public void setCurrentUserCollections(List<?> currentUserCollections) {
            this.currentUserCollections = currentUserCollections;
        }

        public List<CategoriesModel> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoriesModel> categories) {
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
            private String portfolioUrl;
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
}