package com.jtech.imaging.presenter;

import android.app.Activity;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.MainContract;
import com.jtech.imaging.contract.OauthContract;
import com.jtech.imaging.model.ScopeModel;
import com.jtech.imaging.presenter.base.BasePresenter;
import com.jtech.imaging.util.OauthUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 授权认证
 * Created by wuxubaiyang on 16/4/16.
 */
public class OauthPresenter extends BasePresenter<MainContract.View> implements OauthContract.Presenter {

    public OauthPresenter(Activity activity, MainContract.View view) {
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
}