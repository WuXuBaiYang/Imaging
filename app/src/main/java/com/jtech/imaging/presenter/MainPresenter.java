package com.jtech.imaging.presenter;

import com.jtech.imaging.contract.MainContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.net.API;
import com.jtech.imaging.presenter.base.BasePresenter;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 演示用逻辑处理实现类
 * Created by wuxubaiyang on 16/4/16.
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void requestPhotoList(int pageIndex, int displayNumber, String orderBy, final boolean loadMore) {
        API.unsplashApi()
                .photos(pageIndex, displayNumber, orderBy)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PhotoModel>>() {
                    @Override
                    public void call(List<PhotoModel> photoModels) {
                        getView().success(photoModels, loadMore);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        getView().fail(throwable.getMessage());
                    }
                });
    }
}