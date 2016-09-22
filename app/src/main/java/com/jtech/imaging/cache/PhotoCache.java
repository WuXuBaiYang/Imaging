package com.jtech.imaging.cache;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jtech.imaging.common.Constants;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.net.API;
import com.jtechlib.cache.BaseCacheManager;

import java.util.List;

/**
 * 图片缓存
 * Created by jianghan on 2016/9/22.
 */
public class PhotoCache extends BaseCacheManager {
    //首页的首屏图片列表数据缓存key
    private static final String PHOTO_CACHE_FIRSTPAGE = "photos_firstpage";
    //图片缓存时间，10分钟
    private static final int PHOTO_CACHE_TIME = 60 * 10;

    private static PhotoCache INSTANCE;

    public PhotoCache(Context context) {
        super(context);
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

    @Override
    public String getCacheName() {
        return Constants.CACHE_NAME;
    }
}