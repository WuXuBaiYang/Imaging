package com.jtech.imaging.presenter.base;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.jtech.imaging.contract.base.BaseContract;

/**
 * P类基类
 * Created by wuxubaiyang on 16/5/5.
 */
public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {
    private Activity activity;
    private T view;

    public BasePresenter(Activity activity, T view) {
        this.activity = activity;
        this.view = view;
        //设置视图的所对应的P类
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    public T getView() {
        return view;
    }

    public Activity getActivity() {
        return activity;
    }

    @SuppressWarnings("unchecked")
    public <R extends Object> R getViewImpl() {
        return null != view ? (R) view : null;
    }

    @SuppressWarnings("unchecked")
    public <R extends Fragment> R getViewImplAsFragment() {
        return null != view ? (R) view : null;
    }
}