package com.jtech.imaging.net;

import com.jtech.imaging.model.OauthModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * unsplash授权接口
 * Created by wuxubaiyang on 16/4/17.
 */
public interface UnsplashOauthApi {
    @FormUrlEncoded
    @POST("/oauth/token")
    Call<OauthModel> unsplashOauth(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("redirect_uri") String redirectUri,
            @Field("code") String code,
            @Field("grant_type") String grantType);
}