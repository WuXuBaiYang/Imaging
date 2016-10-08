package com.jtech.imaging.presenter;

import com.jtech.imaging.contract.PhotoDetailContract;

/**
 * 图片详情P类
 * Created by jianghan on 2016/10/8.
 */

public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {
    private PhotoDetailContract.View view;

    public PhotoDetailPresenter(PhotoDetailContract.View view) {
        this.view = view;
    }
}