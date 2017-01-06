package com.jtech.imaging.mvp.presenter;

import android.content.Context;

import com.jtech.imaging.mvp.contract.RandomContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.net.API;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 随机页面p类
 * Created by jianghan on 2016/9/23.
 */
public class RandomPresenter implements RandomContract.Presenter {
    private RandomContract.View view;
    private Context context;

    public RandomPresenter(Context context, RandomContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getRandomPhoto(String category, String collections, String featured, String username, String query, int width, int height, String orientation) {
        API.get()
                .unsplashApi(context)
                .randomPhoto(category, collections, featured, username, query, width, height, orientation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PhotoModel>() {
                    @Override
                    public void call(PhotoModel photoModel) {
                        view.success(photoModel);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.fail(throwable.getMessage());
                    }
                });
    }
}