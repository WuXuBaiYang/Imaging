package com.jtech.imaging.presenter;

import android.content.Context;

import com.jtech.imaging.cache.PhotoCache;
import com.jtech.imaging.contract.PhotoDetailContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.net.API;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 图片详情P类
 * Created by jianghan on 2016/10/8.
 */

public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {
    private PhotoDetailContract.View view;

    public PhotoDetailPresenter(PhotoDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void getPhotoDetailCache(final Context context, final String imageId) {
        Observable
                .just(imageId)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, PhotoModel>() {
                    @Override
                    public PhotoModel call(String s) {
                        return PhotoCache.get(context).getPhotoDetail(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PhotoModel>() {
                    @Override
                    public void call(PhotoModel photoModel) {
                        if (null != photoModel) {
                            view.success(photoModel);
                            return;
                        }
                        getPhotoDetail(context, imageId, 0, 0, "0");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.fail(throwable.getMessage());
                    }
                });
    }

    @Override
    public void getPhotoDetail(final Context context, final String imageId, int imageWidth, int imageHeight, String rect) {
        API
                .get()
                .unsplashApi()
                .photoDetail(imageId, imageWidth, imageHeight, rect)
                .subscribeOn(Schedulers.io())
                .map(new Func1<PhotoModel, PhotoModel>() {
                    @Override
                    public PhotoModel call(PhotoModel photoModel) {
                        if (null != photoModel) {
                            PhotoCache.get(context).setPhotoDetail(imageId, photoModel);
                        }
                        return photoModel;
                    }
                })
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