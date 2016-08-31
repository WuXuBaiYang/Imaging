package com.jtech.imaging.model;

import io.realm.RealmObject;

/**
 * unsplash的授权认证数据对象
 * Created by jianghan on 2016/8/30.
 */
public class OauthModel extends RealmObject {
    private String accessToken;
    private String tokenType;
    private String scope;
    private int createdAt;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }
}