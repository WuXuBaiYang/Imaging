package com.jtech.imaging.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * 拦截器实现
 */
class MyInterceptor implements Interceptor {

    private String authToken;

    public MyInterceptor(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        //拼接请求，添加头
        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", authToken)
                .build();
        return chain.proceed(request);
    }
}