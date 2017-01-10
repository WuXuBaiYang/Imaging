package com.jtech.imaging.mvp.contract;

import com.jtech.imaging.model.SearchPhotoModel;
import com.jtechlib.contract.BaseContract;

/**
 * 搜索页面协议
 * Created by jianghan on 2016/9/27.
 */

public interface SearchContract {
    interface Presenter extends BaseContract.Presenter {
        void searchPhotoList(String query, int page, boolean loadMore);

        String getSearchQuery();

        void setSearchQuery(String query);
    }

    interface View extends BaseContract.View {
        void success(SearchPhotoModel searchPhotoModel, boolean loadMore);

        void fail(String message);
    }
}