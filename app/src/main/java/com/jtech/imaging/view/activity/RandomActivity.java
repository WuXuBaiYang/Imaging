package com.jtech.imaging.view.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.RandomContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.presenter.RandomPresenter;
import com.jtechlib.view.activity.BaseActivity;

import butterknife.Bind;

/**
 * 随机页面
 * Created by jianghan on 2016/9/23.
 */
public class RandomActivity extends BaseActivity implements RandomContract.View {

    @Bind(R.id.imageview_random)
    ImageView imageView;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    private RandomContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定P类
        presenter = new RandomPresenter(this);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_random);
        //动画
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionButton.hide();
                int centerX = imageView.getWidth() / 2;
                int centerY = imageView.getHeight() / 2;
                Animator animator = ViewAnimationUtils.createCircularReveal(imageView, centerX, centerY, 0, imageView.getWidth());
                animator.setDuration(800);
                animator.start();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void success(PhotoModel photoModel) {

    }

    @Override
    public void fail(String message) {

    }
}