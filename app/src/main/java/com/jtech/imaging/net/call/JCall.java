package com.jtech.imaging.net.call;

/**
 * Created by wuxubaiyang on 16/3/7.
 */
public interface JCall<T> {
    void cancel();

    <R> JCall<T> enqueue(JCallback<R> callback);

    JCall<T> clone();
}