package com.jtech.imaging.model.base;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * 数据对象基类
 * Created by wuxubaiyang on 16/4/16.
 */
public class BaseModel implements RealmModel {

    @Ignore
    private List<String> errors;

    public boolean isSuccess() {
        return null == errors || errors.size() == 0;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}