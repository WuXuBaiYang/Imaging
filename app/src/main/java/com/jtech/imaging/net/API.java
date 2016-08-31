package com.jtech.imaging.net;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.jtech.imaging.common.Constants;

import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作为接口的实现类
 * Created by wuxubaiyang on 16/4/17.
 */
public class API {
    /**
     * 持有演示用api对象
     */
    private static UnsplashApi unsplashApi;

    public static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .create();

    /**
     * 获取unsplash接口对象
     *
     * @return
     */
    public static UnsplashOauthApi unsplashOauthApi() {
        //创建retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(Constants.BASE_UNSPLASH_OAUTH_URL)
                .client(new OkHttpClient())
                .build();
        return retrofit.create(UnsplashOauthApi.class);
    }

    /**
     * 获取unsplash接口对象
     *
     * @return
     */
    public static UnsplashApi unsplashApi() {
        if (null == unsplashApi) {
            //创建okhttp
            OkHttpClient okHttpClient = new OkHttpClient();
            //创建retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(Constants.BASE_UNSPLASH_URL)
                    .client(okHttpClient)
                    .build();
            //创建retrofit
            unsplashApi = retrofit.create(UnsplashApi.class);
        }
        return unsplashApi;
    }
}