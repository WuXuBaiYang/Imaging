package com.jtech.imaging.view.fragment.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.jtech.imaging.contract.base.BaseContract;
import com.jtech.imaging.view.activity.base.BaseActivity;
import com.jtech.imaging.view.widget.ToolbarChain;

import butterknife.ButterKnife;

/**
 * Fragment基类
 * Created by wuxubaiyang on 16/4/16.
 */
public abstract class BaseFragment<T extends BaseContract.Presenter> extends Fragment implements BaseContract.View {

    private T presenter;
    private View contentView;

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
        //使用fragment加载toolbar
        setHasOptionsMenu(true);
    }

    /**
     * 设置toolbar
     *
     * @param toolbar
     * @return
     */
    public ToolbarChain setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        return new ToolbarChain(getActivity(), toolbar);
    }

    /**
     * 设置toolbar为actionbar
     *
     * @param toolbar
     */
    public void setSupportActionBar(Toolbar toolbar) {
        ((BaseActivity) getActivity()).setSupportActionBar(toolbar);
    }

    /**
     * 开始刷新
     */
    public void startRefresh() {

    }

    /**
     * 获取根视图
     *
     * @return
     */
    public View getContentView() {
        return contentView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    @SuppressWarnings("unchecked")
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