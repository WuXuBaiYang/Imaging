package com.jtech.imaging.view.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.jtech.imaging.R;
import com.jtech.imaging.presenter.OauthPresenter;
import com.jtech.imaging.view.activity.base.BaseActivity;
import com.jtech.imaging.view.fragment.OauthFragment;

/**
 * 授权认证页面
 * Created by jianghan on 2016/8/30.
 */
public class OauthActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth);
        //添加frgament到activity
        addFragmentToActivity(new OauthPresenter(getActivity(),
                        newFragmentInstance(OauthFragment.class)),
                R.id.framelayout_content);
    }
}