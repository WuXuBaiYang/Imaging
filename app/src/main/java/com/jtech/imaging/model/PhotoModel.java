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
    @SerializedName("downloads")
    private int downloads;
    @SerializedName("likes")
    private int likes;
    @SerializedName("liked_by_user")
    private boolean likedByUser;
    @SerializedName("exif")
    private ExifModel exif;
    @SerializedName("location")
    private LocationModel location;
    @SerializedName("urls")
    private UrlsModel urls;
    @SerializedName("links")
    private LinksModel links;
    @SerializedName("user")
    private UserModel user;
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

    public static class ExifModel extends BaseModel {
        @SerializedName("make")
        private String make;
        @SerializedName("model")
        private String model;
        @SerializedName("exposure_time")
        private String exposureTime;
        @SerializedName("aperture")
        private String aperture;
        @SerializedName("focal_length")
        private String focalLength;
        @SerializedName("iso")
        private int iso;

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getExposureTime() {
            return exposureTime;
        }

        public void setExposureTime(String exposureTime) {
            this.exposureTime = exposureTime;
        }

        public String getAperture() {
            return aperture;
        }

        public void setAperture(String aperture) {
            this.aperture = aperture;
        }

        public String getFocalLength() {
            return focalLength;
        }

        public void setFocalLength(String focalLength) {
            this.focalLength = focalLength;
        }

        public int getIso() {
            return iso;
        }

        public void setIso(int iso) {
            this.iso = iso;
        }
    }

    public static class LocationModel extends BaseModel{
        @SerializedName("city")
        private String city;
        @SerializedName("country")
        private String country;
        @SerializedName("position")
        private PositionModel position;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public PositionModel getPosition() {
            return position;
        }

        public void setPosition(PositionModel position) {
            this.position = position;
        }

        public static class PositionModel extends BaseModel{
            @SerializedName("latitude")
            private double latitude;
            @SerializedName("longitude")
            private double longitude;

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }
        }
    }

    public static class UrlsModel extends BaseModel{
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
        @SerializedName("custom")
        private String custom;

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

        public String getCustom() {
            return custom;
        }

        public void setCustom(String custom) {
            this.custom = custom;
        }
    }

    public static class LinksModel extends BaseModel{
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

    public static class UserModel extends BaseModel{
        @SerializedName("id")
        private String id;
        @SerializedName("username")
        private String username;
        @SerializedName("name")
        private String name;
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

        public static class ProfileImageModel extends BaseModel {
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

        public static class LinksModel extends BaseModel{
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
            @SerializedName("following")
            private String following;
            @SerializedName("followers")
            private String followers;

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

            public String getFollowing() {
                return following;
            }

            public void setFollowing(String following) {
                this.following = following;
            }

            public String getFollowers() {
                return followers;
            }

            public void setFollowers(String followers) {
                this.followers = followers;
            }
        }
    }

    public static class CurrentUserCollectionsModel extends BaseModel {
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

        public static class CoverPhotoModel extends BaseModel{
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

            public static class CategoriesModel extends BaseModel {
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

        public static class LinksModel extends BaseModel{
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