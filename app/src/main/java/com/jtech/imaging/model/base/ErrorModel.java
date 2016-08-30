package com.jtech.imaging.model.base;

import io.realm.RealmObject;

/**
 * Created by wuxubaiyang on 2016/8/30.
 */
public class ErrorModel extends RealmObject {
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}