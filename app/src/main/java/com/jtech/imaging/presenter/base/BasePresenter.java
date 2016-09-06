package com.jtech.imaging.presenter.base;

import android.support.v4.app.Fragment;

import com.jtech.imaging.contract.base.BaseContract;

/**
 * P类基类
 * Created by wuxubaiyang on 16/5/5.
 */
public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {
    private T view;

    public BasePresenter(T view) {
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

    @SuppressWarnings("unchecked")
    public <R extends Object> R getViewImpl() {
        return null != view ? (R) view : null;
    }

    @SuppressWarnings("unchecked")
    public <R extends Fragment> R getViewImplAsFragment() {
        return null != view ? (R) view : null;
    }
}