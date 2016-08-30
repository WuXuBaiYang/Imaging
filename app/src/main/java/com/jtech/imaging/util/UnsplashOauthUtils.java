package com.jtech.imaging.util;

import com.jtech.imaging.common.Constants;

/**
 * unsplase认证通用方法
 * Created by jianghan on 2016/8/30.
 */
public class UnsplashOauthUtils {
    /**
     * 拼接认证地址
     *
     * @param scopes
     * @return
     */
    public static String getOauthUrl(String[] scopes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < scopes.length; i++) {
            stringBuffer.append(scopes[i]);
            if (i != scopes.length - 1) {
                stringBuffer.append("+");
            }
        }
        return Constants.BASE_UNSPLASH_OAUTH_URL + "/oauth/authorize?"
                + "client_id=" + Constants.UNSPLASH_CLIENT_ID
                + "redirect_uri=" + Constants.UNSPLASH_REDIRECT_URI
                + "response_type=" + Constants.UNSPLASH_RESPONSE_TYPE
                + "scope=" + stringBuffer.toString();
    }
}