package com.jtech.imaging.view.adapter;

import android.app.Activity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.jtech.adapter.RecyclerAdapter;
import com.jtech.imaging.R;
import com.jtech.imaging.model.ScopeModel;
import com.jtech.view.RecyclerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限列表适配器
 * Created by jianghan on 2016/8/30.
 */
public class ScopesAdapter extends RecyclerAdapter<ScopeModel> {

    public ScopesAdapter(Activity activity) {
        super(activity);
    }

    /**
     * 获取已选中的权限
     *
     * @return
     */
    public String[] getCheckedScope() {
        List<String> scopes = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            if (getItem(i).isChecked()) {
                scopes.add(getItem(i).getScopeValue());
            }
        }
        return scopes.toArray(new String[]{});
    }

    @Override
    public View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        return layoutInflater.inflate(R.layout.view_oauth_scope, viewGroup, false);
    }

    @Override
    public void convert(RecyclerHolder recyclerHolder, ScopeModel scopeModel, final int position) {
        //获取选择框对象
        AppCompatCheckBox appCompatCheckBox = recyclerHolder.getView(R.id.checkbox_scope);
        appCompatCheckBox.setText(scopeModel.getScopeDescribe());
        appCompatCheckBox.setEnabled(!scopeModel.isCantChange());
        appCompatCheckBox.setChecked(scopeModel.isChecked());
        appCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getItem(position).setChecked(isChecked);
            }
        });
    }
}