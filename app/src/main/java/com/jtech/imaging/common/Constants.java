package com.jtech.imaging.common;

/**
 * 全局参数
 * Created by wuxubaiyang on 16/4/17.
 */
public class Constants {
    /**
     * realm数据库名称
     */
    public static final String REALM_DB_NAME = "JTechImaging";
    /**
     * http
     */
    public static final String HTTP = "http://";
    /**
     * https
     */
    public static final String HTTPS = "https://";
    /**
     * unsplash地址
     */
    public static final String BASE_UNSPLASH_URL = HTTPS + "api.unsplash.com";
    /**
     * unsplash认证地址
     */
    public static final String BASE_UNSPLASH_OAUTH_URL = HTTPS + "unsplash.com";

    /**
     * unsplash的client_id
     */
    public static final String UNSPLASH_CLIENT_ID = "4fe8ac07119cbafb8539d609d83dfa4174c25ddbeee722d1f724e3230389985d";
    /**
     * unsplash的回调用地址
     */
    public static final String UNSPLASH_REDIRECT_URI = "https://twitter.com/ChT8uY6wTC1LlfU";
    /**
     * unsplash的请求类型
     */
    public static final String UNSPLASH_RESPONSE_TYPE = "code";
    /**
     * unsplash的SECRET
     */
    public static final String UNSPLASH_SECRET = "f44341303cc2cafc8c1c53ebf4162c52c9ad3fe69ad08035a4ba096d643aebf8";
    /**
     * 类型
     */
    public static final String GRANT_TYPE = "authorization_code";
    /**
     * 排序，最新
     */
    public static final String ORDER_BY_LATEST = "latest";
    /**
     * 排序，反序
     */
    public static final String ORDER_BY_OLDEST = "oldest";
    /**
     * 排序，受欢迎
     */
    public static final String ORDER_BY_POPULAR = "popular";
}