package com.jtech.imaging.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.R;
import com.jtech.imaging.contract.PhotoContract;
import com.jtech.imaging.presenter.PhotoPresenter;
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

    public static final String URI_KEY = "uriKey";
    public static final String WIDTH_KEY = "width";
    public static final String HEIGHT_KEY = "height";

    @Bind(R.id.photoview)
    PhotoView photoView;

    private PhotoContract.Presenter presenter;
    private int width, height;
    private String uri;
    private PhotoViewAttacher.OnPhotoTapListener onPhotoTapListener;

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.fragment_photo, viewGroup, false);
    }

    public static PhotoFragment newInstance(String uri, int width, int height) {
        Bundle args = new Bundle();
        args.putString(URI_KEY, uri);
        args.putInt(WIDTH_KEY, width);
        args.putInt(HEIGHT_KEY, height);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariables(Bundle bundle) {
        //获取图片的uri
        this.uri = bundle.getString(URI_KEY);
        //获取图片宽度
        this.width = bundle.getInt(WIDTH_KEY);
        //获取图片高度
        this.height = bundle.getInt(HEIGHT_KEY);
        //实例化P类
        this.presenter = new PhotoPresenter(getActivity(), this);
    }

    @Override
    protected void initViews(Bundle bundle) {
        //设置tap事件
        photoView.setOnPhotoTapListener(onPhotoTapListener);
    }

    @Override
    protected void loadData() {
        presenter.getImage(uri, width, height, DeviceUtils.getScreenWidth(getActivity()));
    }

    @Override
    public void setPhoto(Bitmap bitmap) {
        if (null != bitmap) {
            photoView.setImageBitmap(bitmap);
        }
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