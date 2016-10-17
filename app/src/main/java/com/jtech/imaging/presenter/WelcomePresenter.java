package com.jtech.imaging.presenter;

import android.content.Context;
import android.util.Log;

import com.jtech.imaging.cache.PhotoCache;
import com.jtech.imaging.contract.WelcomeContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.net.API;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 加载页业务类
 * Created by jianghan on 2016/9/6.
 */
public class WelcomePresenter implements WelcomeContract.Presenter {

    private WelcomeContract.View view;
    private Context context;

    public WelcomePresenter(Context context, WelcomeContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * @param category
     * @param collections
     * @param featured
     * @param username
     * @param query
     * @param width
     * @param height
     * @param orientation Valid values are landscape, portrait, and squarish
     */
    @Override
    public void getWelcomePagePhoto(String category, String collections, String featured, String username, String query, int width, int height, String orientation) {
        //请求图片
        PhotoModel photoModel = PhotoCache.get(context).getWelcomePhoto();
        if (null != photoModel) {
            view.success(photoModel);
        } else {
            API.get()
                    .unsplashApi(context)
                    .randomPhoto(category, collections, featured, username, query, width, height, orientation)
                    .subscribeOn(Schedulers.io())
                    .map(new Func1<PhotoModel, PhotoModel>() {
                        @Override
                        public PhotoModel call(PhotoModel photoModel) {
                            //存储图片信息
                            PhotoCache.get(context).setWelcomePhoto(photoModel);
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
                            Log.e("WelcomePresenter", throwable.getMessage());
                        }
                    });
        }
    }
}