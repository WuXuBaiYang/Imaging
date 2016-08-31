package com.jtech.imaging.model;

import io.realm.RealmObject;

/**
 * unsplash的授权认证权限数据对象
 * Created by jianghan on 2016/8/30.
 */
public class ScopeModel extends RealmObject {
    private String scopeValue;
    private String scopeDescribe;
    private boolean isChecked;
    private boolean cantChange;

    public ScopeModel() {
    }

    public String getScopeValue() {
        return scopeValue;
    }

    public void setScopeValue(String scopeValue) {
        this.scopeValue = scopeValue;
    }

    public String getScopeDescribe() {
        return scopeDescribe;
    }

    public void setScopeDescribe(String scopeDescribe) {
        this.scopeDescribe = scopeDescribe;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isCantChange() {
        return cantChange;
    }

    public void setCantChange(boolean cantChange) {
        this.cantChange = cantChange;
    }
}