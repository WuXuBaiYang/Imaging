package com.jtech.imaging.presenter;

import android.support.v4.app.FragmentManager;
import android.transition.ChangeBounds;
import android.view.View;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.LoadingContract;
import com.jtech.imaging.presenter.base.BasePresenter;
import com.jtech.imaging.view.fragment.MainFragment;
import com.jtech.imaging.view.fragment.OauthFragment;

/**
 * 加载页业务类
 * Created by jianghan on 2016/9/6.
 */
public class LoadingPresenter extends BasePresenter<LoadingContract.View> implements LoadingContract.Presenter {

    public LoadingPresenter(LoadingContract.View view) {
        super(view);
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