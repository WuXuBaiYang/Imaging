package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jakewharton.rxbinding.view.RxView;
import com.jtech.imaging.R;
import com.jtech.imaging.contract.LoadingContract;
import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.realm.OauthRealm;
import com.jtech.imaging.view.fragment.base.BaseFragment;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 加载页视图
 * Created by jianghan on 2016/9/6.
 */
public class LoadingFragment extends BaseFragment<LoadingContract.Presenter> implements LoadingContract.View {

    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.imageview_loading)
    ImageView imageView;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_loading, container, false);
    }

    public static LoadingFragment newInstance() {
        Bundle args = new Bundle();
        LoadingFragment fragment = new LoadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init(Bundle bundle) {
        if (!OauthRealm.hasOauthModel()){
            OauthModel oauthModel = new OauthModel();
            oauthModel.setAccessToken("da20b124d815a82ef0cb79226e991559e6e4c9cdf411fcef4e51acc718c0e44a");
            oauthModel.setCreatedAt(1473643598);
            oauthModel.setScope("public read_user write_user read_photos write_photos write_likes read_collections write_collections");
            oauthModel.setTokenType("bearer");
            OauthRealm.getInstance().setOauthModel(oauthModel);
        }
        getPresenter().jumpToMainPage(getActivity().getSupportFragmentManager(), floatingActionButton, getString(R.string.fab));

        //设置fab的点击事件
        RxView.clicks(floatingActionButton)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new FabClick());
//        //加载图片
//        getPresenter().setLoadingImage(getActivity(), "photo_loading.jpg", new Action1<Bitmap>() {
//            @Override
//            public void call(Bitmap bitmap) {
//                if (null != bitmap) {
//                    imageView.setImageBitmap(bitmap);
//                    ViewAnimationUtils
//                            .createCircularReveal(imageView, imageView.getWidth() / 2, imageView.getHeight() / 2, 0.f, 1.f)
//                            .setDuration(300)
//                            .start();
//                }
//            }
//        });
    }

    /**
     * fab点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            if (OauthRealm.hasOauthModel()) {
                getPresenter().jumpToMainPage(getActivity().getSupportFragmentManager()
                        , floatingActionButton
                        , getString(R.string.fab));
            } else {
                getPresenter().jumpToOauthPage(getActivity().getSupportFragmentManager()
                        , floatingActionButton
                        , getString(R.string.fab));
            }
        }
    }
}