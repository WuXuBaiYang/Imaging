package com.jtech.imaging.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jtech.imaging.common.Constants;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.net.API;
import com.jtech.imaging.strategy.PhotoLoadStrategy;
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

    private static PhotoCache INSTANCE;
    private SharedPreferences sharedPreferences;

    //图片加载策略
    private int photoLoadStrategy;

    private Context context;

    public PhotoCache(Context context) {
        super(context);
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(getCacheName(), Context.MODE_PRIVATE);
    }

    public static PhotoCache get(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new PhotoCache(context);
        }
        return INSTANCE;
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
            this.photoLoadStrategy = sharedPreferences.getInt(PHOTO_LOAD_STRATEGY, PhotoLoadStrategy.PHOTO_LOAD_STRATEGY_FIXED_480);
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PHOTO_LOAD_STRATEGY, strategy);
        editor.commit();
    }

    @Override
    public String getCacheName() {
        return Constants.CACHE_NAME;
    }
}