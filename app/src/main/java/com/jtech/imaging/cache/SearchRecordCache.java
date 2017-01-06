package com.jtech.imaging.cache;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.jtech.imaging.common.Constants;
import com.jtechlib.cache.BaseCacheManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索记录缓存
 * Created by jianghan on 2016/10/9.
 */

public class SearchRecordCache extends BaseCacheManager {
    private static final String KEY_SEARCH_RECORD = "SEARCH_RECORD";

    private List<String> tempSearchRecords;

    public SearchRecordCache(Context context) {
        super(context);
    }

    public List<String> getSearchRecords() {
        if (null == tempSearchRecords) {
            tempSearchRecords = getList(KEY_SEARCH_RECORD, new TypeToken<List<String>>() {
            }.getType());
        }
        return tempSearchRecords;
    }

    /**
     * 添加一条记录
     *
     * @param record
     */
    public void addRecord(String record) {
        if (null == tempSearchRecords) {
            tempSearchRecords = new ArrayList<>();
        }
        //添加一条记录
        tempSearchRecords.add(record);
        //保存记录
        put(KEY_SEARCH_RECORD, tempSearchRecords);
    }

    /**
     * 移除一条数据
     *
     * @param record
     */
    public void removeRecord(String record) {
        if (null != tempSearchRecords) {
            for (int i = 0; i < tempSearchRecords.size(); i++) {
                if (tempSearchRecords.get(i).equals(record)) {
                    //移除一条数据
                    tempSearchRecords.remove(i);
                    //保存记录
                    put(KEY_SEARCH_RECORD, tempSearchRecords);
                    break;
                }
            }
        }
    }

    /**
     * 移除全部记录
     */
    public void removeAllRecord() {
        if (null != tempSearchRecords) {
            //清除集合
            tempSearchRecords.clear();
            //删除缓存
            deleteByKey(KEY_SEARCH_RECORD);
        }
    }

    @Override
    public String getCacheName() {
        return Constants.CACHE_NAME;
    }
}