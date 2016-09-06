package com.jtech.imaging.view.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jakewharton.rxbinding.view.RxView;
import com.jtech.imaging.R;
import com.jtech.imaging.common.Constants;
import com.jtech.imaging.contract.OauthContract;
import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.realm.OauthRealm;
import com.jtech.imaging.view.adapter.ScopesAdapter;
import com.jtech.imaging.view.fragment.base.BaseFragment;
import com.jtech.view.JRecyclerView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 授权认证fragment
 * Created by wuxubaiyang on 16/4/16.
 */
public class OauthFragment extends BaseFragment<OauthContract.Presenter> implements OauthContract.View {

    @Bind(R.id.jrecyclerview_oauth)
    JRecyclerView jRecyclerView;
    @Bind(R.id.webview_oauth)
    WebView webView;
    @Bind(R.id.contentloading_oauth)
    ContentLoadingProgressBar contentLoadingProgressBar;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;

    private ScopesAdapter scopesAdapter;

    /**
     * 创建视图的方法
     *
     * @param inflater
     * @param container
     * @return
     */
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_oauth, container, false);
    }

    public static OauthFragment newInstance() {
        Bundle args = new Bundle();
        OauthFragment fragment = new OauthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 入口方法
     *
     * @param bundle
     */
    @Override
    public void init(Bundle bundle) {
        //设置列表
        jRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        jRecyclerView.setItemAnimator(new DefaultItemAnimator());
        scopesAdapter = new ScopesAdapter(getActivity());
        jRecyclerView.setAdapter(scopesAdapter);
        //设置浏览器
        webView.setWebViewClient(new mWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new mWebChromeClient());
        //设置fab点击事件
        RxView.clicks(floatingActionButton)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new FabClick());
        //隐藏progressbar
        contentLoadingProgressBar.hide();
        //设置数据
        scopesAdapter.setDatas(getPresenter().getScopeList(getActivity()));
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            //隐藏授权列表，显示浏览器
            webView.setVisibility(View.VISIBLE);
            //得到授权认证的url
            String oauthUrl = getPresenter().getOauthUrl(scopesAdapter.getCheckedScope());
            webView.loadUrl(oauthUrl);
        }
    }

    /**
     * 进度监听
     */
    private class mWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            contentLoadingProgressBar.setProgress(newProgress);
            if (newProgress >= 100) {
                contentLoadingProgressBar.hide();
            } else {
                contentLoadingProgressBar.show();
            }
        }
    }

    /**
     * 处理浏览器url
     */
    private class mWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(Constants.UNSPLASH_REDIRECT_URI)) {
                String code = url.replace(Constants.UNSPLASH_REDIRECT_URI + "?code=", "");
                getPresenter().requestToken(Constants.UNSPLASH_CLIENT_ID,
                        Constants.UNSPLASH_SECRET, Constants.UNSPLASH_REDIRECT_URI,
                        code, Constants.GRANT_TYPE);
            }
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void oauthSuccess(OauthModel oauthModel) {
        Snackbar.make(getContentView(), "授权成功"
                , Snackbar.LENGTH_SHORT).show();
        //插入数据
        OauthRealm.getInstance().setOauthModel(oauthModel);
        //跳转到主页
        getPresenter().jumpToMainPage(getActivity().getSupportFragmentManager()
                , floatingActionButton
                , getString(R.string.fab));
    }

    @Override
    public void oauthFail(String error) {
        Snackbar.make(jRecyclerView, error,
                Snackbar.LENGTH_SHORT).show();
        //还原状态
        webView.setVisibility(View.GONE);
    }
}