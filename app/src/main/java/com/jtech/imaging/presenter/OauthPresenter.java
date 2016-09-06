package com.jtech.imaging.presenter;

import android.app.Activity;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.OauthContract;
import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.model.ScopeModel;
import com.jtech.imaging.net.API;
import com.jtech.imaging.presenter.base.BasePresenter;
import com.jtech.imaging.util.OauthUtils;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 授权认证
 * Created by wuxubaiyang on 16/4/16.
 */
public class OauthPresenter extends BasePresenter<OauthContract.View> implements OauthContract.Presenter {

    public OauthPresenter(Activity activity, OauthContract.View view) {
        super(activity, view);
    }

    @Override
    public String getOauthUrl(String[] scopes) {
        return OauthUtils.getOauthUrl(scopes);
    }

    @Override
    public List<ScopeModel> getScopeList() {
        List<ScopeModel> scopeModels = new ArrayList<>();
        String[] scopes = getActivity().getResources().getStringArray(R.array.scopeList);
        for (int i = 0; i < scopes.length; i++) {
            ScopeModel scopeModel = new ScopeModel();
            String[] scope = scopes[i].split("\\|");
            scopeModel.setScopeDescribe(scope[0]);
            scopeModel.setCantChange("true".equals(scope[1]));
            scopeModel.setChecked("true".equals(scope[2]));
            scopeModel.setScopeValue(scope[3]);
            scopeModels.add(scopeModel);
        }
        return scopeModels;
    }

    @Override
    public void requestToken(String clientId, String clientSecret, String redirectUri, String code, String grantType) {
        API.oauthApi()
                .oauth(clientId, clientSecret, redirectUri, code, grantType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<OauthModel>() {
                    @Override
                    public void call(OauthModel oauthModel) {
                        getView().oauthSuccess(oauthModel);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        getView().oauthFail(throwable.getMessage());
                    }
                });
    }
}