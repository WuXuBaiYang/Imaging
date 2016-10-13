package com.jtech.imaging.cache;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jtech.imaging.common.Constants;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.net.API;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
import com.jtech.imaging.strategy.PhotoResolutionStrategy;
import com.jtechlib.cache.ACache;
import com.jtechlib.cache.BaseCacheManager;

import java.util.List;

/**
 * 图片缓存
 * Created by jianghan on 2016/9/22.
 */
public class PhotoCache extends BaseCacheManager {
    //首页的首屏图片列表数据缓存key
    private static final String PHOTO_CACHE_FIRSTPAGE = "photosFirstpage";
    //图片缓存时间，10分钟
    private static final int PHOTO_CACHE_TIME = 60 * 10;
    //图片加载策略，缓存key
    private static final String PHOTO_LOAD_STRATEGY = "photoLoadStrategy";
    //图片清晰度加载策略，缓存key(图片详情)
    private static final String PHOTO_RESOLUTION_STRATEGY = "photoResolutionStrategy";
    //欢迎页图片缓存Key
    private static final String PHOTO_WELCOME_PAGE = "photoWelcomePage";

    private static PhotoCache INSTANCE;

    //图片加载策略
    private int photoLoadStrategy;
    //图片清晰度加载策略(图片详情页)
    private int photoResolutionStrategy;

    private Context context;

    public PhotoCache(Context context) {
        super(context);
        this.context = context;
    }

    public static PhotoCache get(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new PhotoCache(context);
        }
        return INSTANCE;
    }

    /**
     * 清除欢迎页图片url
     *
     * @return
     */
    public boolean clearWelcomePhoto() {
        return delete(PHOTO_WELCOME_PAGE);
    }

    /**
     * 获取欢迎页图片
     *
     * @return
     */
    public PhotoModel getWelcomePhoto() {
        return querySerializable(PHOTO_WELCOME_PAGE);
    }

    /**
     * 保存欢迎页图片（一天）
     *
     * @param photoModel
     * @return
     */
    public boolean setWelcomePhoto(PhotoModel photoModel) {
        return insert(PHOTO_WELCOME_PAGE, photoModel, ACache.TIME_DAY);
    }

    /**
     * 写入首页图片缓存
     *
     * @param photoModels
     * @return
     */
    public boolean setFirstPagePhotos(List<PhotoModel> photoModels) {
        Gson gson = API.gson;
        if (null == gson) {
            gson = new Gson();
        }
        return insert(PHOTO_CACHE_FIRSTPAGE, gson.toJson(photoModels), PHOTO_CACHE_TIME);
    }

    /**
     * 读取首页图片缓存
     *
     * @return
     */
    public List<PhotoModel> getFirstPagePhotos() {
        Gson gson = API.gson;
        if (null == gson) {
            gson = new Gson();
        }
        String json = queryString(PHOTO_CACHE_FIRSTPAGE);
        if (!TextUtils.isEmpty(json)) {
            return gson
                    .fromJson(json, new TypeToken<List<PhotoModel>>() {
                    }.getType());
        }
        return null;
    }

    /**
     * 获取图片缓存策略
     *
     * @return
     */
    public int getPhotoLoadStrategy() {
        if (photoLoadStrategy == 0) {
            this.photoLoadStrategy = queryInt(PHOTO_LOAD_STRATEGY, PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_480);
        }
        return photoLoadStrategy;
    }

    /**
     * 设置并存储策略
     *
     * @param strategy
     */
    public void setPhotoLoadStrategy(int strategy) {
        this.photoLoadStrategy = strategy;
        insertInt(PHOTO_LOAD_STRATEGY, strategy);
    }

    /**
     * 获取图片清晰度缓存策略(图片详情)
     *
     * @return
     */
    public int getPhotoResolution() {
        if (photoResolutionStrategy == 0) {
            this.photoResolutionStrategy = queryInt(PHOTO_RESOLUTION_STRATEGY, PhotoResolutionStrategy.PHOTO_RESOLUTION_480);
        }
        return photoResolutionStrategy;
    }

    /**
     * 设置并存储图片清晰度缓存策略
     *
     * @param strategy
     */
    public void setPhotoResolutionStrategy(int strategy) {
        this.photoResolutionStrategy = strategy;
        insertInt(PHOTO_RESOLUTION_STRATEGY, strategy);
    }

    /**
     * 获取本地缓存的图片详情
     *
     * @param imageId
     * @return
     */
    public PhotoModel getPhotoDetail(String imageId) {
        if (TextUtils.isEmpty(imageId)) {
            return null;
        }
        return querySerializable(imageId);
    }

    /**
     * 缓存图片详情
     *
     * @param imageId
     * @param photoModel
     */
    public void setPhotoDetail(String imageId, PhotoModel photoModel) {
        if (TextUtils.isEmpty(imageId) || null == photoModel) {
            return;
        }
        insertSerializable(imageId, photoModel);
    }

    @Override
    public String getCacheName() {
        return Constants.CACHE_NAME;
    }
}