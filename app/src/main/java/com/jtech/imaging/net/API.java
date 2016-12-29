package com.jtech.imaging.net;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.jtech.imaging.cache.OauthCache;
import com.jtech.imaging.common.Constants;
import com.jtech.imaging.model.OauthModel;
import com.jtechlib.net.BaseApi;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 接口的实现类
 * Created by wuxubaiyang on 16/4/17.
 */
public class API extends BaseApi {
    /**
     * 持有演示用api对象
     */
    private UnsplashApi unsplashApi;
    private static API api;

    public static API get() {
        if (null == api) {
            api = new API();
        }
        return api;
    }


    /**
     * 获取unsplash接口对象
     *
     * @return
     */
    public UnsplashApi.OauthApi oauthApi() {
        //实例化gson
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        //创建retrofit
        return createRxApi(GsonConverterFactory.create(gson), Constants.BASE_UNSPLASH_OAUTH_URL, UnsplashApi.OauthApi.class);
    }

    /**
     * 获取unsplash接口对象
     *
     * @return
     */
    public UnsplashApi unsplashApi(Context context) {
        if (null == unsplashApi) {
            //获取token
            Map<String, String> headerMap = new HashMap<>();
            if (OauthCache.hasOauthModel(context)) {
                OauthModel oauthModel = OauthCache.get(context).getOauthModel();
                headerMap.put("Authorization", oauthModel.getTokenType() + " " + oauthModel.getAccessToken());
            }
            //实例化gson
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateTypeAdapter())
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();
            //创建retrofit
            unsplashApi = createRxApi(headerMap, GsonConverterFactory.create(gson), Constants.BASE_UNSPLASH_URL, UnsplashApi.class);
        }
        return unsplashApi;
    }
}