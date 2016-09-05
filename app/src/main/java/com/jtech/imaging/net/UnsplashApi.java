package com.jtech.imaging.net;

import com.jtech.imaging.model.CollectionsModel;
import com.jtech.imaging.model.LikePhotoModel;
import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.model.PhotoStats;
import com.jtech.imaging.model.SearchCollectionModel;
import com.jtech.imaging.model.SearchPhotoModel;
import com.jtech.imaging.model.SearchUserModel;
import com.jtech.imaging.model.StatsModel;
import com.jtech.imaging.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * unsplash接口
 * Created by wuxubaiyang on 16/4/17.
 */
public interface UnsplashApi {
    /**
     * 获取用户信息 PASS
     *
     * @return
     */
    @GET("/me")
    Call<UserModel> userProfile();

    /**
     * 更新用户信息 PASS
     *
     * @param userName
     * @param firstName
     * @param lastName
     * @param email
     * @param url
     * @param location
     * @param bio
     * @param instagramUsername
     * @return
     */
    @FormUrlEncoded
    @PUT("/me")
    Call<UserModel> updateUserProfile(
            @Field("username") String userName,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("email") String email,
            @Field("url") String url,
            @Field("location") String location,
            @Field("bio") String bio,
            @Field("instagram_username") String instagramUsername);

    /**
     * 获取用户的公共信息 PASS
     *
     * @param username
     * @param width
     * @param height
     * @return
     */
    @GET("/users/{username}")
    Call<UserModel> userPublishProfile(
            @Path("username") String username,
            @Query("w") int width,
            @Query("h") int height);

    /**
     * 用户的图片列表 PASS
     *
     * @param username
     * @param page
     * @param perPage
     * @param orderBy  (Valid values: latest, oldest, popular; default: latest)
     * @return
     */
    @GET("/users/{username}/photos")
    Call<List<PhotoModel>> userPhotoList(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("order_by") String orderBy);

    /**
     * 用户的喜欢列表 PASS
     *
     * @param username
     * @param page
     * @param perPage
     * @param orderBy  (Valid values: latest, oldest, popular; default: latest)
     * @return
     */
    @GET("/users/{username}/likes")
    Call<List<PhotoModel>> userLikePhotoList(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("order_by") String orderBy);

    /**
     * 用户的收藏列表 PASS
     *
     * @param username
     * @param page
     * @param perPage
     * @return
     */
    @GET("/users/{username}/collections")
    Call<List<CollectionsModel>> userCollectionList(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int perPage);

    /**
     * 图片列表 PASS
     *
     * @param page
     * @param perPage
     * @param orderBy
     * @return
     */
    @GET("/photos")
    Call<List<PhotoModel>> photos(
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("order_by") String orderBy);

    /**
     * 照片精选 PASS
     *
     * @param page
     * @param perPage
     * @param orderBy (Valid values: latest, oldest, popular; default: latest)
     * @return
     */
    @GET("/photos/curated")
    Call<List<PhotoModel>> curatedPhotos(
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("order_by") String orderBy);

    /**
     * 图片详情 PASS
     *
     * @param id
     * @param width
     * @param height
     * @param rect   4 comma-separated（逗号分割） integers representing x, y, width, height of the cropped rectangle.
     * @return
     */
    @GET("/photos/{id}")
    Call<PhotoModel> photoDetail(
            @Path("id") String id,
            @Query("width") int width,
            @Query("height") int height,
            @Query("rect") int rect);

    /**
     * 获取一张随机的图片 PASS
     *
     * @param category
     * @param collections
     * @param featured
     * @param username
     * @param query
     * @param width
     * @param height
     * @param orientation Valid values are landscape, portrait, and squarish.
     * @return
     */
    @GET("/photos/random")
    Call<PhotoModel> randomPhoto(
            @Query("category") String category,
            @Query("collections") String collections,
            @Query("featured") String featured,
            @Query("username") String username,
            @Query("query") String query,
            @Query("w") int width,
            @Query("h") int height,
            @Query("orientation") String orientation);

    /**
     * 获取照片属性 ERROR
     *
     * @param id
     * @return
     */
    @GET("/photos/{id}/stats")
    Call<PhotoStats> photoStats(
            @Path("id") String id);

    /**
     * 只获取图片的下载地址 PASS
     *
     * @param id
     * @return
     */
    @GET("/photos/{id}/download")
    Call<PhotoStats> photoLink(
            @Path("id") String id);

    /**
     * 上传图片 UNCHECK
     *
     * @param id
     * @param latitude
     * @param longitude
     * @param name
     * @param city
     * @param country
     * @param confidential
     * @param make
     * @param model
     * @param exposureTime
     * @param apertureValue
     * @param focalLength
     * @param isoSpeedRatings
     * @return
     */
    @FormUrlEncoded
    @PUT("/photos/{id}")
    Call<PhotoModel> updatePhoto(
            @Path("id") String id,
            @Field("location[latitude]") String latitude,
            @Field("location[longitude]") String longitude,
            @Field("location[name]") String name,
            @Field("location[city]") String city,
            @Field("location[country]") String country,
            @Field("location[confidential]") String confidential,
            @Field("exif[make]") String make,
            @Field("exif[model]") String model,
            @Field("exif[exposure_time]") String exposureTime,
            @Field("exif[aperture_value]") String apertureValue,
            @Field("exif[focal_length]") String focalLength,
            @Field("exif[iso_speed_ratings]") String isoSpeedRatings);

    /**
     * 喜欢 PASS
     *
     * @param id
     * @return
     */
    @POST("/photos/{id}/like")
    Call<LikePhotoModel> likePhoto(
            @Path("id") String id);

    /**
     * 不喜欢 PASS
     *
     * @param id
     * @return
     */
    @DELETE("/photos/{id}/like")
    Call<LikePhotoModel> unlikePhoto(
            @Path("id") String id);

    /**
     * 搜索图片 PASS
     *
     * @param query
     * @param page
     * @return
     */
    @GET("/search/photos")
    Call<SearchPhotoModel> searchPhotos(
            @Query("query") String query,
            @Query("page") int page);

    /**
     * 在合集中搜索 PASS
     *
     * @param query
     * @param page
     * @return
     */
    @GET("/search/collections")
    Call<SearchCollectionModel> searchCollections(
            @Query("query") String query,
            @Query("page") int page);

    /**
     * 搜索用户 PASS
     *
     * @param query
     * @param page
     * @return
     */
    @GET("/search/users")
    Call<SearchUserModel> searchUsers(
            @Query("query") String query,
            @Query("page") int page);

    /**
     * 收藏列表 PASS
     *
     * @param page
     * @param perPage
     * @return
     */
    @GET("/collections")
    Call<List<CollectionsModel>> listOfCollections(
            @Query("page") int page,
            @Query("per_page") int perPage);

    /**
     * 特色收藏列表 PASS
     *
     * @param page
     * @param perPage
     * @return
     */
    @GET("/collections/featured")
    Call<List<CollectionsModel>> listOfFeaturedCollections(
            @Query("page") int page,
            @Query("per_page") int perPage);

    /**
     * 精选收藏列表 PASS
     *
     * @param page
     * @param perPage
     * @return
     */
    @GET("/collections/curated")
    Call<List<CollectionsModel>> listOfCuratedCollections(
            @Query("page") int page,
            @Query("per_page") int perPage);

    /**
     * 收藏详情 PASS
     *
     * @param id
     * @return
     */
    @GET("/collections/{id}")
    Call<CollectionsModel> collectionsDetail(
            @Path("id") String id);

    /**
     * 精选详情 PASS
     *
     * @param id
     * @return
     */
    @GET("/collections/curated/{id}")
    Call<CollectionsModel> curatedDetail(
            @Path("id") String id);

    /**
     * 收藏图片列表 PASS
     *
     * @param id
     * @param page
     * @param perPage
     * @return
     */
    @GET("/collections/{id}/photos")
    Call<List<PhotoModel>> collectionsPhotos(
            @Path("id") String id,
            @Query("page") int page,
            @Query("per_page") int perPage);

    /**
     * 精选图片列表 PASS
     *
     * @param id
     * @param page
     * @param perPage
     * @return
     */
    @GET("/collections/curated/{id}/photos")
    Call<List<PhotoModel>> curatedPhotos(
            @Path("id") String id,
            @Query("page") int page,
            @Query("per_page") int perPage);

    /**
     * 集合的相关集合 PASS
     *
     * @param id
     * @return
     */
    @GET("/collections/{id}/related")
    Call<List<CollectionsModel>> relatedCollections(
            @Path("id") String id);

    /**
     * 创建收藏 PASS
     *
     * @param title
     * @param description
     * @param priv        私有(Optional; default false)
     * @return
     */
    @FormUrlEncoded
    @POST("/collections")
    Call<CollectionsModel> createCollection(
            @Field("title") String title,
            @Field("description") String description,
            @Field("private") String priv);

    /**
     * 更新收藏信息 PASS
     *
     * @param id
     * @param title
     * @param description
     * @param priv
     * @return
     */
    @FormUrlEncoded
    @PUT("/collections/{id}")
    Call<CollectionsModel> updateCollection(
            @Path("id") String id,
            @Field("title") String title,
            @Field("description") String description,
            @Field("private") String priv);

    /**
     * 删除一个收藏 PASS
     * Responds with a 204 status and an empty body.
     *
     * @param id
     * @return
     */
    @DELETE("/collections/{id}")
    Call<Object> deleteCollection(
            @Path("id") String id);

    /**
     * 向收藏中添加一个图片 PASS
     *
     * @param collectionId
     * @param photoId
     * @return
     */
    @FormUrlEncoded
    @POST("/collections/{collectionId}/add")
    Call<CollectionsModel> addPhotoCollection(
            @Path("collectionId") String collectionId,
            @Field("photo_id") String photoId);

    /**
     * 从收藏中删除一个图片 PASS
     *
     * @param collectionId
     * @param photoId
     * @return
     */
    @DELETE("/collections/{collectionId}/remove")
    Call<CollectionsModel> removePhotoCollection(
            @Path("collectionId") String collectionId,
            @Query("photo_id") String photoId);

    /**
     * 状态 PASS
     *
     * @return
     */
    @GET("/stats/total")
    Call<StatsModel> totalPhotos();

    /**
     * oauth2.0授权
     */
    interface OauthApi {
        @FormUrlEncoded
        @POST("/oauth/token")
        Call<OauthModel> unsplashOauth(
                @Field("client_id") String clientId,
                @Field("client_secret") String clientSecret,
                @Field("redirect_uri") String redirectUri,
                @Field("code") String code,
                @Field("grant_type") String grantType);
    }
}