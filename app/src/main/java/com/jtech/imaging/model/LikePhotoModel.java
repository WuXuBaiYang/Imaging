package com.jtech.imaging.model;

import com.google.gson.annotations.SerializedName;
import com.jtechlib.model.BaseModel;

/**
 * 喜欢
 * Created by jianghan on 2016/8/31.
 */
public class LikePhotoModel extends BaseModel {

    private PhotoModel photo;
    private UserModel user;

    public PhotoModel getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoModel photo) {
        this.photo = photo;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public static class PhotoModel {
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

    public static class UserModel {
        private String id;
        private String username;
        private String name;
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
}