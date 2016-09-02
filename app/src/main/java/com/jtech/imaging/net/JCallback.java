package com.jtech.imaging.net;

import com.jtech.imaging.model.ErrorModel;

/**
 * 融合业务逻辑与请求逻辑
 * Created by jianghan on 2016/9/2.
 */
public interface JCallback<T> {
    void onSuccess(T response);

    void onFailure(int errorCode, ErrorModel errorModel);
}