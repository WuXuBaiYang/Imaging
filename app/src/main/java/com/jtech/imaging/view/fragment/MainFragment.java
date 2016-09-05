package com.jtech.imaging.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.MainContract;
import com.jtech.imaging.model.PhotoModel;
import com.jtech.imaging.net.API;
import com.jtech.imaging.realm.OauthRealm;
import com.jtech.imaging.view.activity.OauthActivity;
import com.jtech.imaging.view.fragment.base.BaseFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 测试用fragment
 * Created by wuxubaiyang on 16/4/16.
 */
public class MainFragment extends BaseFragment<MainContract.Presenter> implements MainContract.View {
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
        if (OauthRealm.hasOauthModel()) {
            api();
        } else {
            startActivity(new Intent(getActivity(), OauthActivity.class));
        }
    }

    private void api() {
        API.unsplashApi().photos(1, 15, "").enqueue(new Callback<List<PhotoModel>>() {
            @Override
            public void onResponse(Call<List<PhotoModel>> call, Response<List<PhotoModel>> response) {
                Log.d("", "");
            }

            @Override
            public void onFailure(Call<List<PhotoModel>> call, Throwable t) {
                Log.d("", "");
            }
        });
    }
}