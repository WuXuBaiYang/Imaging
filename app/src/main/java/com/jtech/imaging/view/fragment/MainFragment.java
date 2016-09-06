package com.jtech.imaging.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.MainContract;
import com.jtech.imaging.model.LikePhotoModel;
import com.jtech.imaging.net.API;
import com.jtech.imaging.realm.OauthRealm;
import com.jtech.imaging.view.activity.OauthActivity;
import com.jtech.imaging.view.fragment.base.BaseFragment;

import butterknife.Bind;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 测试用fragment
 * Created by wuxubaiyang on 16/4/16.
 */
public class MainFragment extends BaseFragment<MainContract.Presenter> implements MainContract.View {

    @Bind(R.id.button)
    Button button;

    /**
     * 创建视图的方法
     *
     * @param inflater
     * @param container
     * @return
     */
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    /**
     * 入口方法
     *
     * @param bundle
     */
    @Override
    public void init(Bundle bundle) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                step();
            }
        });
    }

    private void step() {
        if (OauthRealm.hasOauthModel()) {
            Subscription subscription = API.unsplashApi().likePhoto("Gacd_XeSGQk")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<LikePhotoModel>() {
                        @Override
                        public void call(LikePhotoModel likePhotoModel) {
                            Log.d("", "");
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.d("", "");
                        }
                    });
        } else {
            startActivity(new Intent(getActivity(), OauthActivity.class));
        }
    }
}