package com.jtech.imaging.view.activity;

import android.os.Bundle;

import com.jtech.imaging.R;
import com.jtech.imaging.presenter.LoadingPresenter;
import com.jtech.imaging.view.activity.base.BaseActivity;
import com.jtech.imaging.view.fragment.LoadingFragment;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置loading页
        setupLoadingPage();
    }

    /**
     * 设置loading页
     */
    private void setupLoadingPage() {
        LoadingPresenter loadingPresenter = new LoadingPresenter(LoadingFragment.newInstance());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout_content, loadingPresenter.getViewImplAsFragment())
                .addToBackStack(null)
                .commit();
    }
}