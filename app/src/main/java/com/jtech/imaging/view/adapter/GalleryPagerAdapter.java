package com.jtech.imaging.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.jtech.imaging.model.DownloadModel;
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
    private List<DownloadModel> downloadModels;

    public GalleryPagerAdapter(FragmentManager fm, List<DownloadModel> downloadModels) {
        super(fm);
        this.downloadModels = downloadModels;
        this.viewMap = new HashMap<>();
    }

    public void setOnOutsidePhotoTap(OnOutsidePhotoTap onOutsidePhotoTap) {
        this.onOutsidePhotoTap = onOutsidePhotoTap;
    }

    @Override
    public Fragment getItem(int position) {
        DownloadModel downloadModel = downloadModels.get(position);
        PhotoFragment photoFragment = PhotoFragment.newInstance(downloadModel.getPath(), downloadModel.getWidth(), downloadModel.getHeight());
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
        return downloadModels.size();
    }

    public DownloadModel getModel(int position) {
        return downloadModels.get(position);
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