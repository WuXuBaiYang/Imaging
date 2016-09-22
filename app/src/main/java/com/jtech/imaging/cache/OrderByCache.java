package com.jtech.imaging.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.jtech.imaging.R;
import com.jtech.imaging.common.Constants;
import com.jtechlib.cache.BaseCacheManager;

/**
 * 图片缓存
 * Created by jianghan on 2016/9/22.
 */
public class OrderByCache extends BaseCacheManager {
    //排序缓存key
    private static final String ORDER_BY_KEY = "orderByKey";

    private static OrderByCache INSTANCE;
    private SharedPreferences sharedPreferences;

    private int orderBy = -1;
    private String[] sorts;

    public OrderByCache(Context context) {
        super(context);
        this.sorts = context.getResources().getStringArray(R.array.sort);
        this.sharedPreferences = context.getSharedPreferences(getCacheName(), Context.MODE_PRIVATE);
    }

    public static OrderByCache get(Context context) {
        if (null == INSTANCE) {
            INSTANCE = new OrderByCache(context);
        }
        return INSTANCE;
    }

    /**
     * 获取排序的缓存key
     *
     * @return
     */
    public int getOrderByIndex() {
        if (-1 == orderBy) {
            this.orderBy = sharedPreferences.getInt(ORDER_BY_KEY, 0);
        }
        return orderBy;
    }

    /**
     * 获取排序的值
     *
     * @return
     */
    public String getOrderByString() {
        return sorts[getOrderByIndex()];
    }

    /**
     * 获取排序的值，小写
     *
     * @return
     */
    public String getOrderByLowerCase() {
        return getOrderByString().toLowerCase();
    }

    /**
     * 存储排序位置
     *
     * @param orderBy
     */
    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ORDER_BY_KEY, orderBy);
        editor.commit();
    }

    @Override
    public String getCacheName() {
        return Constants.CACHE_NAME;
    }
}