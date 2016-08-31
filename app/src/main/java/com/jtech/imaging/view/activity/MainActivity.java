package com.jtech.imaging.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jtech.imaging.R;
import com.jtech.imaging.presenter.MainPresenter;
import com.jtech.imaging.realm.OauthRealm;
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
        //如果用户授权信息不存在，则跳转到授权页面
        if (!OauthRealm.hasOauthModel()) {
            startActivity(new Intent(getActivity(), OauthActivity.class));
        } else {

        }
    }
}