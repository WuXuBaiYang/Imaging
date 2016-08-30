package com.jtech.imaging.view.fragment.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.jtech.imaging.contract.base.BaseContract;
import com.jtech.imaging.net.call.JCall;
import com.jtech.imaging.net.RequestManager;
import com.jtech.imaging.util.ActivityUtils;
import com.jtech.imaging.util.ToastUtils;

import butterknife.ButterKnife;

/**
 * Fragment基类
 * Created by wuxubaiyang on 16/4/16.
 */
public abstract class BaseFragment<T extends BaseContract.Presenter> extends Fragment implements BaseContract.View, View.OnClickListener {

    public static final String TAG = BaseFragment.class.getSimpleName();

    private T presenter;
    private View contentView;
    private RequestManager requestManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建视图
        contentView = createView(inflater, container);
        if (null != contentView) {
            //绑定注解框架
            ButterKnife.bind(this, contentView);
            //调用入口方法
            init(getArguments());
        }
        //返回视图
        return contentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 显示toast
     *
     * @param message
     */
    public void showToast(String message) {
        ToastUtils.showShort(getActivity(), message);
    }

    /**
     * 开始刷新
     */
    public void startRefresh() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 获取根视图
     *
     * @return
     */
    public View getContentView() {
        return contentView;
    }

    /**
     * 根据id获取视图
     *
     * @param id
     * @return
     */
    public <T extends View> T findViewById(@IdRes int id) {
        return (T) contentView.findViewById(id);
    }

    /**
     * 获取请求管理对象
     *
     * @return
     */
    public RequestManager getRequestManager() {
        if (null == requestManager) {
            //实例化请求管理对象
            requestManager = new RequestManager();
        }
        return requestManager;
    }

    /**
     * 添加一个请求
     *
     * @param call
     * @return
     */
    public RequestManager addCall(JCall call) {
        return getRequestManager().addCall(call);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != requestManager) {
            requestManager.clearAllCall();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != requestManager) {
            requestManager.clearAllCall();
        }
    }

    @Override
    public void setPresenter(BaseContract.Presenter presenter) {
        this.presenter = (T) presenter;
    }

    /**
     * 获取P类实现
     *
     * @return
     */
    public T getPresenter() {
        return presenter;
    }

    /**
     * 无bundle的方法
     *
     * @param classType
     * @param <R>
     * @return
     */
    public static <R extends BaseFragment> R newInstance(Class<R> classType) {
        return newInstance(classType, null);
    }

    /**
     * 实例化方法
     *
     * @param classType
     * @param args
     * @return
     */
    public static <R extends BaseFragment> R newInstance(Class<R> classType, Bundle args) {
        return ActivityUtils.newFragmentInstance(classType, args);
    }

    /**
     * 检查权限
     *
     * @param permissions
     * @param permissionsResultAction
     */
    public void checkPermissions(String[] permissions, PermissionsResultAction permissionsResultAction) {
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(getActivity(), permissions, permissionsResultAction);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //权限申请结果接收
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    /**
     * 后退方法
     */
    public void keyBack() {
        getActivity().finish();
    }

    /**
     * 创建视图
     *
     * @param inflater
     * @param container
     * @return
     */
    public abstract View createView(LayoutInflater inflater, ViewGroup container);

    /**
     * 入口方法
     *
     * @param bundle
     */
    public abstract void init(Bundle bundle);
}