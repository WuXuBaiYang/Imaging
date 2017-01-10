package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.model.SearchPhotoModel;
import com.jtech.imaging.mvp.contract.SearchContract;
import com.jtech.imaging.net.API;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 搜索P类
 * Created by jianghan on 2016/9/27.
 */

public class SearchPresenter implements SearchContract.Presenter {
    private Context context;
    private SearchContract.View view;
    private String query;

    public SearchPresenter(Context context, SearchContract.View view, String query) {
        this.context = context;
        this.view = view;
        this.query = query;
    }

    @Override
    public void searchPhotoList(String query, int page, final boolean loadMore) {
        API.get().unsplashApi(context)
                .searchPhotos(query, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<SearchPhotoModel>() {
                    @Override
                    public void call(SearchPhotoModel searchPhotoModel) {
                        view.success(searchPhotoModel, loadMore);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.fail(throwable.getMessage());
                    }
                });
    }

    @Override
    public String getSearchQuery() {
        return query;
    }

    @Override
    public void setSearchQuery(String query) {
        this.query = query;
    }
}