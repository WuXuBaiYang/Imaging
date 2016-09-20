package com.jtech.imaging.presenter;

import com.jtech.imaging.contract.WelcomeContract;

/**
 * 加载页业务类
 * Created by jianghan on 2016/9/6.
 */
public class WelcomePresenter implements WelcomeContract.Presenter {

    private WelcomeContract.View view;

    public WelcomePresenter(WelcomeContract.View view) {
        this.view = view;
    }
}