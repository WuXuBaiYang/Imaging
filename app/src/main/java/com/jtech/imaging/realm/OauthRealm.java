package com.jtech.imaging.realm;

import com.jtech.imaging.JApplication;
import com.jtech.imaging.model.OauthModel;
import com.jtechlib.realm.BaseRealm;

import io.realm.Realm;

/**
 * 授权认证相关的数据库操作
 * Created by jianghan on 2016/8/31.
 */
public class OauthRealm extends BaseRealm {
    private static OauthRealm INSTANCE;
    private OauthModel oauthModel;
    private Realm realm;

    public OauthRealm() {
        realm = JApplication.getInstance().getRealm();
    }

    public static OauthRealm getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new OauthRealm();
        }
        return INSTANCE;
    }

    /**
     * 是否存在授权信息
     *
     * @return
     */
    public static boolean hasOauthModel() {
        return null != OauthRealm.getInstance().getOauthModel();
    }

    /**
     * 插入授权认证的对象
     *
     * @param oauthModel
     */
    public void setOauthModel(OauthModel oauthModel) {
        this.oauthModel = oauthModel;
        insertModel(OauthModel.class, realm, oauthModel);
    }

    /**
     * 移除授权认证对象
     */
    public void removeOauthModel() {
        this.oauthModel = null;
        deleteModel(OauthModel.class, realm);
    }

    /**
     * 获取授权认证对象
     *
     * @return
     */
    public OauthModel getOauthModel() {
        if (null == oauthModel) {
            oauthModel = (OauthModel) queryModel(OauthModel.class, realm);
        }
        return oauthModel;
    }
}