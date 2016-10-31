package com.jtech.imaging.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jtech.adapter.RecyclerAdapter;
import com.jtech.view.RecyclerHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试用适配器
 * Created by jianghan on 2016/10/31.
 */

public class TestAdapter extends RecyclerAdapter<String> {
    public TestAdapter(Context context) {
        super(context);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            datas.add("当前为第" + i);
        }
        setDatas(datas);
    }

    @Override
    protected View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        TextView textView = new TextView(getContext());
        textView.setId(android.R.id.text1);
        textView.setPadding(30, 30, 30, 30);
        return textView;
    }

    @Override
    protected void convert(RecyclerHolder recyclerHolder, String s, int i) {
        recyclerHolder.setText(android.R.id.text1, s);
    }
}
