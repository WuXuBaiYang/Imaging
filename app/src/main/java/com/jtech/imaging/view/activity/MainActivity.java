package com.jtech.imaging.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jtech.imaging.R;
import com.jtech.imaging.presenter.MainPresenter;
import com.jtech.imaging.view.activity.base.BaseActivity;
import com.jtech.imaging.view.fragment.MainFragment;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //将fragment添加到当前的activity中
        addFragmentToActivity(new MainPresenter(getActivity(),
                        newFragmentInstance(MainFragment.class)),
                R.id.framelayout_content);

        startActivity(new Intent(getActivity(),OauthActivity.class));
    }
}