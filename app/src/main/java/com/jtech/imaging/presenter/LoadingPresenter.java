package com.jtech.imaging.presenter;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.view.View;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.LoadingContract;
import com.jtech.imaging.presenter.base.BasePresenter;
import com.jtech.imaging.view.fragment.MainFragment;
import com.jtech.imaging.view.fragment.OauthFragment;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 加载页业务类
 * Created by jianghan on 2016/9/6.
 */
public class LoadingPresenter extends BasePresenter<LoadingContract.View> implements LoadingContract.Presenter {

    public LoadingPresenter(LoadingContract.View view) {
        super(view);
    }

    @Override
    public void setLoadingImage(final Activity activity, String fileName, Action1<Bitmap> action1) {
        Observable.just(fileName)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        if (!TextUtils.isEmpty(s)) {
                            try {
                                AssetManager assetManager = activity.getAssets();
                                InputStream inputStream = assetManager.open(s);
                                return BitmapFactory.decodeStream(inputStream);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1);
    }

    @Override
    public void jumpToMainPage(FragmentManager fragmentManager, View view, String name) {
        MainPresenter mainPresenter = new MainPresenter(MainFragment.newInstance());
        mainPresenter.getViewImplAsFragment().setSharedElementEnterTransition(new ChangeBounds());
        fragmentManager.beginTransaction()
                .addSharedElement(view, name)
                .replace(R.id.framelayout_content, mainPresenter.getViewImplAsFragment())
                .commit();
    }

    @Override
    public void jumpToOauthPage(FragmentManager fragmentManager, View view, String name) {
        OauthPresenter oauthPresenter = new OauthPresenter(OauthFragment.newInstance());
        oauthPresenter.getViewImplAsFragment().setSharedElementEnterTransition(new ChangeBounds());
        fragmentManager.beginTransaction()
                .addSharedElement(view, name)
                .replace(R.id.framelayout_content, oauthPresenter.getViewImplAsFragment())
                .commit();
    }
}