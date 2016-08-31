package com.jtech.imaging.net;

import com.jtech.imaging.model.OauthModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * unsplash授权接口
 * Created by wuxubaiyang on 16/4/17.
 */
public interface UnsplashOauthApi {
    @GET("/oauth/token")
    Call<OauthModel> unsplashOauth(
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret,
            @Query("redirect_uri") String redirectUri,
            @Query("code") String code,
            @Query("grant_type") String grantType);
}