package com.jtech.imaging.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jtech.imaging.R;
import com.jtech.imaging.cache.OauthCache;
import com.jtech.imaging.common.Constants;
import com.jtech.imaging.contract.OauthContract;
import com.jtech.imaging.model.OauthModel;
import com.jtech.imaging.presenter.OauthPresenter;
import com.jtech.imaging.view.adapter.ScopesAdapter;
import com.jtech.imaging.view.widget.RxCompat;
import com.jtech.view.JRecyclerView;
import com.jtechlib.view.activity.BaseActivity;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 授权认证页面
 * Created by jianghan on 2016/9/20.
 */
public class OauthActivity extends BaseActivity implements OauthContract.View {

    @Bind(R.id.jrecyclerview)
    JRecyclerView jRecyclerView;
    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.contentloading)
    ContentLoadingProgressBar contentLoadingProgressBar;
    @Bind(R.id.fab)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.content)
    CoordinatorLayout content;

    private ScopesAdapter scopesAdapter;
    private OauthContract.Presenter presenter;

    @Override
    protected void initVariables(Bundle bundle) {
        //绑定VP
        presenter = new OauthPresenter(this);
    }

    @Override
    protected void initViews(Bundle bundle) {
        setContentView(R.layout.activity_oauth);
        //设置toolbar
        setupToolbar(toolbar)
                .setTitle(R.string.oauth_page_title)
                .setSubTitle(R.string.oauth_page_subtitle_scope);
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
        RxCompat.clickThrottleFirst(floatingActionButton, new FabClick());
    }

    @Override
    protected void loadData() {
        //设置权限列表数据
        scopesAdapter.setDatas(presenter.getScopeList(getActivity()));
    }

    @Override
    public void oauthSuccess(OauthModel oauthModel) {
        //插入数据
        OauthCache.get().setOauthModel(oauthModel);
        Snackbar.make(content, "授权成功"
                , Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                //跳转到主页
                ActivityOptionsCompat activityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                                floatingActionButton, getString(R.string.fab));
                ActivityCompat.startActivity(getActivity(), new Intent(getActivity(),
                        MainActivity.class), activityOptionsCompat.toBundle());
            }
        }).show();
    }

    @Override
    public void oauthFail(String error) {
        //还原状态
        new FabClick().call(null);
        Snackbar.make(content, error,
                Snackbar.LENGTH_SHORT).show();
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            boolean isVisible = View.VISIBLE == webView.getVisibility();
            //设置subtitle
            toolbar.setSubtitle(isVisible ? R.string.oauth_page_subtitle_scope : R.string.oauth_page_subtitle_login);
            //显示或隐藏webview
            webView.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
            //设置fab的图标
            floatingActionButton.setImageResource(isVisible ? R.drawable.ic_done_white_36dp : R.drawable.ic_reply_white_36dp);
            //加载url
            webView.stopLoading();
            contentLoadingProgressBar.setProgress(0);
            if (!isVisible) {
                //得到授权认证的url
                String oauthUrl = presenter.getOauthUrl(scopesAdapter.getCheckedScope());
                webView.loadUrl(oauthUrl);
            }
        }
    }

    /**
     * 进度监听
     */
    private class mWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            contentLoadingProgressBar.setProgress(newProgress >= 100 ? 0 : newProgress);
        }
    }

    /**
     * 处理浏览器url
     */
    private class mWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            if (url.startsWith(Constants.UNSPLASH_REDIRECT_URI)) {
                String code = url.replace(Constants.UNSPLASH_REDIRECT_URI + "?code=", "");
                presenter.requestToken(Constants.UNSPLASH_CLIENT_ID,
                        Constants.UNSPLASH_SECRET, Constants.UNSPLASH_REDIRECT_URI,
                        code, Constants.GRANT_TYPE);
            }
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, request);
        }
    }
}