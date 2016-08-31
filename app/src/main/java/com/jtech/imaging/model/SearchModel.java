package com.jtech.imaging.model;

import java.util.List;

/**
 * 搜索结果
 * Created by jianghan on 2016/8/31.
 */
public class SearchModel {

    private int total;
    private int totalPages;
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
        private String id;
        private String username;
        private String name;
        private Object portfolioUrl;
        private int totalLikes;
        private int totalPhotos;
        private int totalCollections;
        private ProfileImageModel profileImage;
        private LinksModel links;
        private List<PhotosModel> photos;

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

        public List<PhotosModel> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosModel> photos) {
            this.photos = photos;
        }

        public static class ProfileImageModel {
            private String small;
            private String medium;
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

        public static class PhotosModel {
            private String id;
            private String createdAt;
            private int width;
            private int height;
            private String color;
            private int likes;
            private boolean likedByUser;
            private UrlsModel urls;
            private LinksModel links;
            private List<?> currentUserCollections;

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

            public static class UrlsModel {
                private String raw;
                private String full;
                private String regular;
                private String small;
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
                private String self;
                private String html;
                private String download;

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
            }
        }
    }
}
