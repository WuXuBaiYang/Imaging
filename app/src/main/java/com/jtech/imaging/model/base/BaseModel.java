package com.jtech.imaging.model.base;

import io.realm.RealmList;
import io.realm.RealmModel;

/**
 * 数据对象基类
 * Created by wuxubaiyang on 16/4/16.
 */
public class BaseModel implements RealmModel {
    private RealmList<ErrorModel> errors;

    public boolean isSuccess() {
        return null == errors || errors.size() == 0;
    }

    public RealmList<ErrorModel> getErrors() {
        return errors;
    }

    public String getError() {
        return getErrors().get(0).getError();
    }

    public void setErrors(RealmList<ErrorModel> errors) {
        this.errors = errors;
    }
}