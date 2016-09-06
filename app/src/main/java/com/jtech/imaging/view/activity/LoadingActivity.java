package com.jtech.imaging.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jtech.imaging.R;
import com.jtech.imaging.presenter.LoadingPresenter;
import com.jtech.imaging.view.activity.base.BaseActivity;
import com.jtech.imaging.view.fragment.LoadingFragment;

/**
 * 加载页Activity
 * Created by jianghan on 2016/9/6.
 */
public class LoadingActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        //绑定视图与presenter
        addFragmentToActivity(new LoadingPresenter(getActivity()
                        , newFragmentInstance(LoadingFragment.class))
                , R.id.framelayout_content);
    }
}