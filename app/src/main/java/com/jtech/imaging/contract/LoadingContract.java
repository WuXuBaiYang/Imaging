package com.jtech.imaging.contract;


import android.support.v4.app.FragmentManager;

import com.jtech.imaging.contract.base.BaseContract;

/**
 * 加载页协议
 * Created by jianghan on 2016/9/6.
 */
public interface LoadingContract {
    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {
        void jumpToMainPage(FragmentManager fragmentManager, android.view.View view, String name);

        void jumpToOauthPage(FragmentManager fragmentManager,android.view.View view, String name);
    }
}