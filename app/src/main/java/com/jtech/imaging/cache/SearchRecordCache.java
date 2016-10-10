package com.jtech.imaging.cache;

import android.content.Context;
import android.text.TextUtils;

import com.jtech.imaging.common.Constants;
import com.jtechlib.cache.BaseCacheManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 搜索记录缓存
 * Created by jianghan on 2016/10/9.
 */

public class SearchRecordCache extends BaseCacheManager {

    private static final String SEARCH_RECORD_CACHE_KEY = "searchRecordCache";

    private List<String> searchRecords;

    public SearchRecordCache(Context context) {
        super(context);
    }

    /**
     * 获取搜索记录
     *
     * @return
     */
    public List<String> getSearchRecords() {
        if (null == searchRecords) {
            this.searchRecords = convertToList(queryString(SEARCH_RECORD_CACHE_KEY));
        }
        return searchRecords;
    }

    /**
     * 获取最新的缓存数据
     *
     * @return
     */
    public List<String> getCurrentSearchRecords() {
        this.searchRecords = convertToList(queryString(SEARCH_RECORD_CACHE_KEY));
        return searchRecords;
    }

    /**
     * 添加搜索记录(添加到首位)
     *
     * @param keyword
     */
    public void addSearchRecord(String keyword) {
        if (getSearchRecords().size() > 0) {
            getSearchRecords().add(0, keyword);
        } else {
            getSearchRecords().add(keyword);
        }
        //保存记录
        saveSearchRecord();
    }

    /**
     * 根据搜索的关键字，移除搜索记录
     *
     * @param keyword
     */
    public void removeSearchRecord(String keyword) {
        for (int i = 0; i < getSearchRecords().size(); i++) {
            if (keyword.equals(getSearchRecords().get(i))) {
                //移除对象
                getSearchRecords().remove(i);
                //保存记录
                saveSearchRecord();
                break;
            }
        }
    }


    /**
     * 移除全部的记录
     */
    public void removeAllRecord() {
        this.searchRecords = null;
        delete(SEARCH_RECORD_CACHE_KEY);
    }

    /**
     * 保存搜索记录
     */
    private void saveSearchRecord() {
        insertString(SEARCH_RECORD_CACHE_KEY, convertToString(getSearchRecords()));
    }

    /**
     * 将字符转换为集合
     *
     * @param origin
     * @return
     */
    private List<String> convertToList(String origin) {
        List<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(origin)) {
            list.addAll(Arrays.asList(origin.split("\\|")));
        }
        return list;
    }

    /**
     * 将集合转换为字符
     *
     * @param origin
     * @return
     */
    private String convertToString(List<String> origin) {
        if (null != origin && origin.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < origin.size(); i++) {
                sb.append(origin.get(i));
                if (i != origin.size() - 1) {
                    sb.append("|");
                }
            }
            return sb.toString();
        }
        return "";
    }

    @Override
    public String getCacheName() {
        return Constants.CACHE_NAME;
    }
}