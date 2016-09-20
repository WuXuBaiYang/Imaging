package com.jtech.imaging.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;
import com.jtech.imaging.R;
import com.jtech.imaging.contract.WelcomeContract;
import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.presenter.WelcomePresenter;
import com.jtech.imaging.realm.OauthRealm;
import com.jtechlib.view.activity.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 欢迎页，首屏
 * Created by jianghan on 2016/9/20.
 */
public class WelcomeActivity extends BaseActivity implements WelcomeContract.View {

    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.imageview_welcome)
    ImageView imageView;

    private WelcomeContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定VP类
        presenter = new WelcomePresenter(this);
        //暂时使用已存在数据，不经过授权登陆
        if (!OauthRealm.hasOauthModel()) {
            OauthModel oauthModel = new OauthModel();
            oauthModel.setAccessToken("da20b124d815a82ef0cb79226e991559e6e4c9cdf411fcef4e51acc718c0e44a");
            oauthModel.setCreatedAt(1473643598);
            oauthModel.setScope("public read_user write_user read_photos write_photos write_likes read_collections write_collections");
            oauthModel.setTokenType("bearer");
            OauthRealm.getInstance().setOauthModel(oauthModel);
        }
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_welcome);
        //设置fab的点击事件
        RxView.clicks(floatingActionButton)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new FabClick());
    }

    @Override
    protected void loadData() {

    }

    /**
     * fab点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            if (OauthRealm.hasOauthModel()) {
                //跳转到主页
            } else {
                //跳转到授权登陆页
            }
        }
    }
}