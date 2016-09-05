package com.jtech.imaging.net;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.jtech.imaging.common.Constants;
import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.realm.OauthRealm;

import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 接口的实现类
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
    public static UnsplashApi.OauthApi oauthApi() {
        //创建retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.BASE_UNSPLASH_OAUTH_URL)
                .client(new OkHttpClient())
                .build();
        return retrofit.create(UnsplashApi.OauthApi.class);
    }

    /**
     * 获取unsplash接口对象
     *
     * @return
     */
    public static UnsplashApi unsplashApi() {
        if (null == unsplashApi) {
            //获取token
            String authToken = "";
            if (OauthRealm.hasOauthModel()) {
                OauthModel oauthModel = OauthRealm.getInstance().getOauthModel();
                authToken = oauthModel.getTokenType() + " " + oauthModel.getAccessToken();
            }
            //创建okhttp
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new MyInterceptor(authToken))
                    .build();
            //创建retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(Constants.BASE_UNSPLASH_URL)
                    .client(okHttpClient)
                    .build();
            //创建retrofit
            unsplashApi = retrofit.create(UnsplashApi.class);
        }
        return unsplashApi;
    }
}