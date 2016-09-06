package com.jtech.imaging.presenter;

import android.app.Activity;

import com.jtech.imaging.contract.LoadingContract;
import com.jtech.imaging.presenter.base.BasePresenter;

/**
 * 加载页业务类
 * Created by jianghan on 2016/9/6.
 */
public class LoadingPresenter extends BasePresenter<LoadingContract.View> implements LoadingContract.Presenter {

    public LoadingPresenter(Activity activity, LoadingContract.View view) {
        super(activity, view);
    }
}