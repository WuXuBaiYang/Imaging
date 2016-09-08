package com.jtech.imaging.view.fragment;

import android.animation.Animator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
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
import com.jtech.imaging.view.widget.StatusBarCompat;
import com.jtech.view.JRecyclerView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import rx.functions.Action1;

/**
 * 授权认证fragment
 * Created by wuxubaiyang on 16/4/16.
 */
public class OauthFragment extends BaseFragment<OauthContract.Presenter> implements OauthContract.View {

    private static final long ANIMATION_DURATION = 130;

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
        //设置状态栏占位
        StatusBarCompat.compat(getActivity());
        //设置toolbar
        setupToolbar(toolbar)
                .setNavigationIcon(R.drawable.ic_verified_user_white_24dp)
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
        RxView.clicks(floatingActionButton)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new FabClick());
        //设置数据
        scopesAdapter.setDatas(getPresenter().getScopeList(getActivity()));
    }

    /**
     * fab的点击事件
     */
    private class FabClick implements Action1<Void> {
        @Override
        public void call(Void aVoid) {
            //缩小fab
            scaleFab(floatingActionButton, 1.0f, 0f, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
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
                        String oauthUrl = getPresenter().getOauthUrl(scopesAdapter.getCheckedScope());
                        webView.loadUrl(oauthUrl);
                    }
                    //放大fab
                    scaleFab(floatingActionButton, 0f, 1.0f, null);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
    }

    /**
     * 缩放fab
     *
     * @param floatingActionButton
     * @param start
     * @param end
     * @param animatorListener
     */
    private void scaleFab(FloatingActionButton floatingActionButton, float start, float end, Animator.AnimatorListener animatorListener) {
        Animator animator = ViewAnimationUtils
                .createCircularReveal(floatingActionButton, floatingActionButton.getWidth() / 2, floatingActionButton.getHeight() / 2, start, end)
                .setDuration(ANIMATION_DURATION);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        if (null != animatorListener) {
            animator.addListener(animatorListener);
        }
        animator.start();
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
        //插入数据
        OauthRealm.getInstance().setOauthModel(oauthModel);
        Snackbar.make(getContentView(), "授权成功"
                , Snackbar.LENGTH_SHORT).setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                //跳转到主页
                getPresenter().jumpToMainPage(getActivity().getSupportFragmentManager()
                        , floatingActionButton
                        , getString(R.string.fab));
            }
        }).show();
    }

    @Override
    public void oauthFail(String error) {
        //还原状态
        new FabClick().call(null);
        Snackbar.make(getContentView(), error,
                Snackbar.LENGTH_SHORT).show();
    }
}