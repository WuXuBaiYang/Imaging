package com.jtech.imaging.contract;

import com.jtech.imaging.contract.base.BaseContract;
import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.model.ScopeModel;

import java.util.List;

import retrofit2.Call;

/**
 * 授权认证协议
 * Created by wuxubaiyang on 16/4/16.
 */
public interface OauthContract {

    interface Presenter extends BaseContract.Presenter {
        String getOauthUrl(String[] scopes);

        List<ScopeModel> getScopeList();

        void requestToken(String clientId, String clientSecret, String redirectUri, String code, String grantType);
    }

    interface View extends BaseContract.View {

        void oauthSuccess(OauthModel oauthModel);

        void oauthFail(String error);
    }
}