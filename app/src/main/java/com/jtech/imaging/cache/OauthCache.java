package com.jtech.imaging.cache;

import android.content.Context;

import com.jtech.imaging.common.Constants;
import com.jtech.imaging.model.OauthModel;
import com.jtechlib.cache.BaseCacheManager;

/**
 * 授权认证信息对象缓存管理
 * Created by jianghan on 2016/9/21.
 */
public class OauthCache extends BaseCacheManager {
    private static final String CACHE_KEY_OAUTH = "cacheKeyOauth";

    private static OauthCache INSTANCE;

    private OauthModel oauthModel;

    public OauthCache(Context context) {
        super(context);
    }

    public static OauthCache get(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new OauthCache(context);
        }
        return INSTANCE;
    }

    /**
     * 是否存在授权信息
     *
     * @return
     */
    public static boolean hasOauthModel(Context context) {
        return null != OauthCache.get(context).getOauthModel();
    }

    /**
     * 设置授权认证数据对象
     *
     * @param oauthModel
     * @return
     */
    public void setOauthModel(OauthModel oauthModel) {
        this.oauthModel = oauthModel;
        put(CACHE_KEY_OAUTH, oauthModel);
    }

    /**
     * 获取授权认证数据对象
     *
     * @return
     */
    public OauthModel getOauthModel() {
        if (null == oauthModel) {
            oauthModel = getSerializable(CACHE_KEY_OAUTH);
        }
        return oauthModel;
    }

    @Override
    public String getCacheName() {
        return Constants.CACHE_NAME;
    }
}