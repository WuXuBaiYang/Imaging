package com.jtech.imaging.net;

import com.jtech.imaging.model.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * unsplash接口
 * Created by wuxubaiyang on 16/4/17.
 */
public interface UnsplashApi {
    @GET("/me")
    Call<UserModel> userProfile();
}