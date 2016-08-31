package com.jtech.imaging.realm;

import com.jtech.imaging.model.OauthModel;

/**
 * 授权认证相关的数据库操作
 * Created by jianghan on 2016/8/31.
 */
public class OauthRealm extends BaseRealm {
    private static OauthRealm INSTANCE;
    private OauthModel oauthModel;

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
        insertModel(OauthModel.class, oauthModel);
    }

    /**
     * 移除授权认证对象
     */
    public void removeOauthModel() {
        this.oauthModel = null;
        deleteModel(OauthModel.class);
    }

    /**
     * 获取授权认证对象
     *
     * @return
     */
    public OauthModel getOauthModel() {
        if (null == oauthModel) {
            oauthModel = queryModel(OauthModel.class);
        }
        return oauthModel;
    }
}