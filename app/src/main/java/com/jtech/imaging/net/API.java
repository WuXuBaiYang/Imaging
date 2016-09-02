package com.jtech.imaging.net;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.jtech.imaging.common.Constants;
import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.realm.OauthRealm;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.concurrent.Executor;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
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
    public static UnsplashApi.OauthApi unsplashOauthApi() {
        //创建retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(new CallAdapterFactory())
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
            //创建okhttp
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new MyInterceptor(getAuthToken())).build();
            //创建retrofit
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(new CallAdapterFactory())
                    .baseUrl(Constants.BASE_UNSPLASH_URL)
                    .client(okHttpClient)
                    .build();
            //创建retrofit
            unsplashApi = retrofit.create(UnsplashApi.class);
        }
        return unsplashApi;
    }

    /**
     * 获取token
     *
     * @return
     */
    private static String getAuthToken() {
        String authToken = "";
        if (OauthRealm.hasOauthModel()) {
            OauthModel oauthModel = OauthRealm.getInstance().getOauthModel();
            authToken = oauthModel.getTokenType() + " " + oauthModel.getAccessToken();
        }
        return authToken;
    }

    /**
     * 请求处理
     */
    private static class CallAdapterFactory extends CallAdapter.Factory {
        @Override
        public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
            final Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
            final Executor callbackExecutor = retrofit.callbackExecutor();
            return new CallAdapter<Call<?>>() {
                @Override
                public Type responseType() {
                    return responseType;
                }

                @Override
                public Call adapt(Call call) {
                    return new MyCallAdapter<>(callbackExecutor, call);
                }
            };
        }
    }

    /**
     * 适配器
     *
     * @param
     */
    private static class MyCallAdapter<T> implements Call<T> {
        private Call call;
        private Executor callbackExecutor;
        private Handler mainThreadHandler;

        public MyCallAdapter(Executor callbackExecutor, Call call) {
            this.callbackExecutor = callbackExecutor;
            this.call = call;
            //创建主线程handler
            mainThreadHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public Response<T> execute() throws IOException {
            return null;
        }

        @Override
        public void enqueue(final Callback<T> callback) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(final Call<T> call, final Response<T> response) {
                    if (!call.isCanceled()) {
                        mainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (200 == response.code()) {
                                    callback.onResponse(call, response);
                                } else {
                                    callback.onFailure(call, new Throwable(response.message()));
                                }
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    callback.onFailure(call, t);
                }
            });
        }

        @Override
        public boolean isExecuted() {
            return false;
        }

        @Override
        public void cancel() {
            if (null != call) {
                call.cancel();
            }
        }

        @Override
        public boolean isCanceled() {
            return null != call ? call.isCanceled() : true;
        }

        @Override
        public Call clone() {
            return new MyCallAdapter<>(callbackExecutor, call.clone());
        }

        @Override
        public Request request() {
            return null;
        }
    }

    /**
     * 拦截器实现
     */
    private static class MyInterceptor implements Interceptor {

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
}