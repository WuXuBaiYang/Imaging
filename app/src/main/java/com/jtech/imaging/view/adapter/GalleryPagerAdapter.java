package com.jtech.imaging.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.view.fragment.PhotoFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 画廊的viewpager的适配器
 * Created by jianghan on 2016/11/21.
 */

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    private OnOutsidePhotoTap onOutsidePhotoTap;
    private Map<Integer, PhotoFragment> viewMap;
    private List<String> imageUris;

    public GalleryPagerAdapter(FragmentManager fm, List<String> imageUris) {
        super(fm);
        this.imageUris = imageUris;
        this.viewMap = new HashMap<>();
    }

    public void setOnOutsidePhotoTap(OnOutsidePhotoTap onOutsidePhotoTap) {
        this.onOutsidePhotoTap = onOutsidePhotoTap;
    }

    @Override
    public Fragment getItem(int position) {
        PhotoFragment photoFragment = PhotoFragment.newInstance(imageUris.get(position));
        photoFragment.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {

            }

            @Override
            public void onOutsidePhotoTap() {
                if (null != onOutsidePhotoTap) {
                    onOutsidePhotoTap.outsideTap();
                }
            }
        });
        viewMap.put(position, photoFragment);
        return photoFragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        viewMap.remove(position);
    }

    @Override
    public int getCount() {
        return imageUris.size();
    }

    public View getView(int position) {
        return viewMap.get(position).getPhotoView();
    }

    /**
     * 图片界外点击事件
     */
    public interface OnOutsidePhotoTap {
        void outsideTap();
    }
}