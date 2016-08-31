package com.jtech.imaging.net;

import com.jtech.imaging.model.LikePhotoModel;
import com.jtech.imaging.model.PhotoStats;
import com.jtech.imaging.model.SearchModel;
import com.jtech.imaging.model.CollectionsModel;
import com.jtech.imaging.model.StatsModel;
import com.jtech.imaging.model.UserModel;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.model.UserPublicModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * unsplash接口
 * Created by wuxubaiyang on 16/4/17.
 */
public interface UnsplashApi {
    /**
     * 获取用户信息
     *
     * @return
     */
    @GET("/me")
    Call<UserModel> getUserProfile();

    /**
     * 更新用户信息
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
    @PUT("/me")
    Call<UserModel> updateUserProfile(
            @Part("username") String userName,
            @Part("first_name") String firstName,
            @Part("last_name") String lastName,
            @Part("email") String email,
            @Part("url") String url,
            @Part("location") String location,
            @Part("bio") String bio,
            @Part("instagram_username") String instagramUsername);

    /**
     * 获取用户的公共信息
     *
     * @param username
     * @param width
     * @param height
     * @return
     */
    @GET("/users/{username}")
    Call<UserPublicModel> getUserPublishProfile(
            @Path("username") String username,
            @Query("w") int width,
            @Query("h") int height);

    /**
     * 用户的图片列表
     *
     * @param username
     * @param page
     * @param perPage
     * @param orderBy  (Valid values: latest, oldest, popular; default: latest)
     * @return
     */
    @GET("/users/{username}/photos")
    Call<List<PhotoModel>> listOfUserPhotos(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("order_by") String orderBy);

    /**
     * 用户的喜欢列表
     *
     * @param username
     * @param page
     * @param perPage
     * @param orderBy  (Valid values: latest, oldest, popular; default: latest)
     * @return
     */
    @GET("/users/{username}/likes")
    Call<List<PhotoModel>> listOfLikePhotos(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int perPage,
            @Query("order_by") String orderBy);

    /**
     * 用户的收藏列表
     *
     * @param username
     * @param page
     * @param perPage
     * @return
     */
    @GET("/users/{username}/collections")
    Call<List<CollectionsModel>> listOfCollectionPhotos(
            @Path("username") String username,
            @Query("page") int page,
            @Query("per_page") int perPage);

    /**
     * 图片列表
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
     * 照片精选
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
     * 图片详情
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
     * 获取一张随机的图片
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
            @Path("category") String category,
            @Query("collections") String collections,
            @Query("featured") String featured,
            @Query("username") String username,
            @Query("query") String query,
            @Query("w") int width,
            @Query("h") int height,
            @Query("orientation") String orientation);

    /**
     * 获取照片属性
     *
     * @param id
     * @return
     */
    @GET("/photos/{id}/stats")
    Call<PhotoStats> photoStats(
            @Path("id") String id);

    /**
     * 上传图片
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
    @PUT("/photos/{id}")
    Call<PhotoModel> updatePhoto(
            @Path("id") String id,
            @Part("location[latitude]") String latitude,
            @Part("location[longitude]") String longitude,
            @Part("location[name]") String name,
            @Part("location[city]") String city,
            @Part("location[country]") String country,
            @Part("location[confidential]") String confidential,
            @Part("exif[make]") String make,
            @Part("exif[model]") String model,
            @Part("exif[exposure_time]") String exposureTime,
            @Part("exif[aperture_value]") String apertureValue,
            @Part("exif[focal_length]") String focalLength,
            @Part("exif[iso_speed_ratings]") String isoSpeedRatings);

    /**
     * 喜欢
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/photos/{id}/like")
    Call<LikePhotoModel> likePhoto(
            @Path("id") String id);

    /**
     * 不喜欢
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @DELETE("/photos/{id}/like")
    Call<LikePhotoModel> unlikePhoto(
            @Path("id") String id);

    /**
     * 搜索图片
     *
     * @param query
     * @param page
     * @return
     */
    @GET("/search/photos")
    Call<SearchModel> searchPhotos(
            @Query("query") String query,
            @Query("page") int page);

    /**
     * 在合集中搜索
     *
     * @param query
     * @param page
     * @return
     */
    @GET("/search/collections")
    Call<SearchModel> searchCollections(
            @Query("query") String query,
            @Query("page") int page);

    /**
     * 搜索用户
     *
     * @param query
     * @param page
     * @return
     */
    @GET("/search/users")
    Call<SearchModel> searchUsers(
            @Query("query") String query,
            @Query("page") int page);

    /**
     * 收藏列表
     *
     * @param query
     * @param page
     * @return
     */
    @GET("/collections")
    Call<List<CollectionsModel>> listOfCollections(
            @Query("query") String query,
            @Query("page") int page);

    /**
     * 特色收藏列表
     *
     * @param query
     * @param page
     * @return
     */
    @GET("/collections/featured")
    Call<List<CollectionsModel>> listOfFeaturedCollections(
            @Query("query") String query,
            @Query("page") int page);

    /**
     * 精选收藏列表
     *
     * @param query
     * @param page
     * @return
     */
    @GET("/collections/curated")
    Call<List<CollectionsModel>> listOfCuratedCollections(
            @Query("query") String query,
            @Query("page") int page);

    /**
     * 收藏详情
     *
     * @param id
     * @return
     */
    @GET("/collections/{id}")
    Call<CollectionsModel> collectionsDetail(
            @Path("id") String id);

    /**
     * 精选收藏详情
     *
     * @param id
     * @return
     */
    @GET("/collections/curated/{id}")
    Call<CollectionsModel> curatedCollectionsDetail(
            @Path("id") String id);

    /**
     * 收藏图片列表
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
     * 精选收藏图片列表
     *
     * @param id
     * @param page
     * @param perPage
     * @return
     */
    @GET("/collections/curated/{id}/photos")
    Call<List<PhotoModel>> curatedCollectionsPhotos(
            @Path("id") String id,
            @Query("page") int page,
            @Query("per_page") int perPage);

    /**
     * 集合的相关集合
     *
     * @param id
     * @return
     */
    @GET("/collections/{id}/related")
    Call<List<CollectionsModel>> relatedCollections(
            @Path("id") String id);

    /**
     * 创建收藏
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
     * 更新收藏信息
     *
     * @param id
     * @param title
     * @param description
     * @param priv
     * @return
     */
    @PUT("/collections/{id}")
    Call<CollectionsModel> updateCollection(
            @Path("id") String id,
            @Part("title") String title,
            @Part("description") String description,
            @Part("private") String priv);

    /**
     * 删除一个收藏
     * Responds with a 204 status and an empty body.
     *
     * @param id
     * @return
     */
    @DELETE("/collections/{id}")
    Call<Object> deleteCollection(
            @Path("id") String id);

    /**
     * 向收藏中添加一个图片
     *
     * @param id
     * @param collectionId
     * @param photoId
     * @return
     */
    @FormUrlEncoded
    @POST("/collections/{id}/add")
    Call<CollectionsModel> addPhotoCollection(
            @Path("id") String id,
            @Field("collection_id") String collectionId,
            @Field("photo_id") String photoId);

    /**
     * 从收藏中删除一个图片
     *
     * @param id
     * @param collectionId
     * @param photoId
     * @return
     */
    @DELETE("/collections/{id}/remove")
    Call<CollectionsModel> removePhotoCollection(
            @Path("id") String id,
            @Part("collection_id") String collectionId,
            @Part("photo_id") String photoId);

    /**
     * 状态
     *
     * @return
     */
    @GET("/stats/total")
    Call<StatsModel> totalPhotos();
}