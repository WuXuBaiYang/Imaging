package com.jtech.imaging.contract.base;

/**
 * 协议基类
 * Created by wuxubaiyang on 16/5/5.
 */
public interface BaseContract {

    interface Presenter {
        void start();
    }

    interface View {
        void setPresenter(BaseContract.Presenter presenter);
    }
}