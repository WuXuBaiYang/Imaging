package com.jtech.imaging.net.call;

import com.jtech.imaging.net.ErrorResponse;

/**
 * Created by wuxubaiyang on 16/3/7.
 */
public interface JCallback<T>  {
    /** success responses. */
    void success(T response);
    /** Failure */
    void failure(ErrorResponse error);
}