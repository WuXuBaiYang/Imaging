package com.jtech.imaging.contract;

import com.jtech.imaging.contract.base.BaseContract;
import com.jtech.imaging.model.ScopeModel;

import java.util.List;

/**
 * 授权认证协议
 * Created by wuxubaiyang on 16/4/16.
 */
public interface OauthContract {

    interface Presenter extends BaseContract.Presenter {
        String getOauthUrl(String[] scopes);

        List<ScopeModel> getScopeList();
    }

    interface View extends BaseContract.View {
    }
}