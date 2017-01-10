package com.jtech.imaging.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.mvp.contract.PhotoContract;
import com.jtech.imaging.mvp.presenter.PhotoPresenter;
import com.jtech.imaging.view.widget.LoadingView;
import com.jtechlib.Util.DeviceUtils;
import com.jtechlib.view.fragment.BaseFragment;

import butterknife.Bind;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片fragment
 * Created by jianghan on 2016/11/23.
 */

public class PhotoFragment extends BaseFragment implements PhotoContract.View {
    public static final String KEY_PHOTO_URI = "PHOTO_URI";

    @Bind(R.id.photoview)
    PhotoView photoView;
    @Bind(R.id.contentloading)
    LoadingView loadingView;

    private PhotoContract.Presenter presenter;
    private PhotoViewAttacher.OnPhotoTapListener onPhotoTapListener;

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_photo, viewGroup, false);
    }

    @Override
    protected void initVariables(Bundle bundle) {
        //获取图片的uri
        String originUrl = bundle.getString(KEY_PHOTO_URI);
        //实例化P类
        this.presenter = new PhotoPresenter(getActivity(), this, originUrl);
    }

    @Override
    protected void initViews(Bundle bundle) {
        loadingView.show();
        //设置tap事件
        photoView.setOnPhotoTapListener(onPhotoTapListener);
    }

    @Override
    protected void loadData() {
        //请求图片
        presenter.loadImage(DeviceUtils.getScreenWidth(getActivity()));
    }

    public static PhotoFragment newInstance(String uri) {
        Bundle args = new Bundle();
        args.putString(KEY_PHOTO_URI, uri);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void loadSuccess(Bitmap bitmap) {
        loadingView.hide();
        photoView.setImageBitmap(bitmap);
    }

    @Override
    public void looadFail(String error) {

    }

    /**
     * 设置图片的点击事件
     *
     * @param onPhotoTapListener
     */
    public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener onPhotoTapListener) {
        this.onPhotoTapListener = onPhotoTapListener;
    }

    /**
     * 获取photoView
     *
     * @return
     */
    public PhotoView getPhotoView() {
        return photoView;
    }
}