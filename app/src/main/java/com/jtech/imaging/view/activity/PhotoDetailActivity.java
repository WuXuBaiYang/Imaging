package com.jtech.imaging.view.activity;

import android.os.Bundle;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.PhotoDetailContract;
import com.jtech.imaging.presenter.PhotoDetailPresenter;
import com.jtechlib.view.activity.BaseActivity;

/**
 * 图片详情
 * Created by jianghan on 2016/10/8.
 */

public class PhotoDetailActivity extends BaseActivity implements PhotoDetailContract.View {
    private PhotoDetailContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //p类实现
        this.presenter = new PhotoDetailPresenter(this);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_photo_detail);
    }

    @Override
    protected void loadData() {

    }
}