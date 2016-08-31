package com.jtech.imaging.model;

import java.util.List;

/**
 * 图片
 * Created by jianghan on 2016/8/31.
 */
public class PhotoModel {

    private String id;
    private String createdAt;
    private int width;
    private int height;
    private String color;
    private int likes;
    private boolean likedByUser;
    private UserModel user;
    private UrlsModel urls;
    private LinksModel links;
    private List<CurrentUserCollectionsModel> currentUserCollections;

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

    public static class UserModel {
        private String id;
        private String username;
        private String name;
        private ProfileImageModel profileImage;
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

    public static class CurrentUserCollectionsModel {
        private int id;
        private String title;
        private String publishedAt;
        private boolean curated;
        private CoverPhotoModel coverPhoto;
        private UserModel user;
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
            private String id;
            private int width;
            private int height;
            private String color;
            private int likes;
            private boolean likedByUser;
            private UserModel user;
            private UrlsModel urls;
            private LinksModel links;
            private List<CategoriesModel> categories;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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
                private int id;
                private String title;
                private int photoCount;
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
            private String id;
            private String username;
            private String name;
            private String bio;
            private PhotoModel.UserModel.ProfileImageModel profileImage;
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

            public String getBio() {
                return bio;
            }

            public void setBio(String bio) {
                this.bio = bio;
            }

            public PhotoModel.UserModel.ProfileImageModel getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(PhotoModel.UserModel.ProfileImageModel profileImage) {
                this.profileImage = profileImage;
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