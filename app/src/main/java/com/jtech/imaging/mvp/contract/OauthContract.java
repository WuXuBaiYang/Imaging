package com.jtech.imaging.mvp.contract;

import android.app.Activity;

import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.model.ScopeModel;
import com.jtechlib.contract.BaseContract;

import java.util.List;

/**
 * 授权认证协议
 * Created by wuxubaiyang on 16/4/16.
 */
public interface OauthContract {

    interface Presenter extends BaseContract.Presenter {
        String getOauthUrl(String[] scopes);

        List<ScopeModel> getScopeList(Activity activity);

        void requestToken(String clientId, String clientSecret, String redirectUri, String code, String grantType);
    }

    interface View extends BaseContract.View {

        void oauthSuccess(OauthModel oauthModel);

        void oauthFail(String error);
    }
}