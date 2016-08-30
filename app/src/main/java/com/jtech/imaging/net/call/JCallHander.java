package com.jtech.imaging.net.call;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.jtech.imaging.net.CommonResponse;
import com.jtech.imaging.net.ErrorResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A sample showing a custom {@link CallAdapter} which adapts the built-in {@link Call} to a custom
 * version whose callback has more granular methods.
 */
public final class JCallHander {

    public static class JCallAdapterFactory extends CallAdapter.Factory {
        @Override
        public CallAdapter<JCall<?>> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
            if (getRawType(returnType) != JCall.class) {
                return null;
            }
            if (!(returnType instanceof ParameterizedType)) {
                throw new IllegalStateException("JCall must have generic type (e.g., JCall<ResponseBody>)");
            }
            final Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
            final Executor callbackExecutor = retrofit.callbackExecutor();

            return new CallAdapter<JCall<?>>() {
                @Override
                public Type responseType() {
                    return responseType;
                }

                @Override
                public <R> JCall<R> adapt(Call<R> call) {
                    return new JCallAdapter<>(call, callbackExecutor);
                }
            };
        }
    }

    /**
     * Adapts a {@link Call} to {@link JCall}.
     */
    private static class JCallAdapter<T> implements JCall<T> {
        private final Call<T> call;
        private final Executor callbackExecutor;
        private final Handler mainThreadHandler;

        JCallAdapter(Call<T> call, Executor callbackExecutor) {
            this.call = call;
            this.callbackExecutor = callbackExecutor;
            this.mainThreadHandler = new Handler(Looper.getMainLooper());
        }

        @Override
        public void cancel() {
            call.cancel();
        }

        @Override
        public <R> JCall<T> enqueue(final JCallback<R> callback) {
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, final Response<T> response) {
                    if (!call.isCanceled()) {
                        mainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                int code = response.code();
                                if (code >= 200 && code < 300) {
                                    T body = response.body();
                                    if (body instanceof CommonResponse) {
                                        CommonResponse<R> commonResponse = (CommonResponse<R>) body;
                                        if (200 == commonResponse.code) {
                                            callback.success(commonResponse.result);
                                        } else {
                                            callback.failure(new ErrorResponse(commonResponse.code, commonResponse.msg));
                                        }
                                    } else {
                                        callback.failure(new ErrorResponse(444, "请求类错误"));
                                    }
                                } else {
                                    callback.failure(new ErrorResponse(code, "HTTP状态错误"));
                                }
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<T> call, final Throwable t) {
                    if (!call.isCanceled()) {
                        mainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (t instanceof IOException) {
                                    callback.failure(new ErrorResponse(-1, "网络错误"));
                                } else {
                                    callback.failure(new ErrorResponse(-2, "未知错误"));
                                }
                                Log.d("请求错误：", t.getMessage());
                            }
                        });
                    }
                }
            });
            return this;
        }

        @Override
        public JCall<T> clone() {
            return new JCallAdapter<>(call.clone(), callbackExecutor);
        }
    }
}