package com.example.homepageview.custom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.homepageview.R;

public class ProverbFragment extends Fragment {

    private static final String ARG_DATA = "data";

    public static ProverbFragment newInstance(String data) {
        ProverbFragment fragment = new ProverbFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_proverb, null);
        // 获取传递的数据
        String data = getArguments() != null ? getArguments().getString(ARG_DATA) : "";
        // 根据数据更新界面
        TextView textView = view.findViewById(R.id.tv_homepage_proverb);
        textView.setText(data);  // 设置不同的文本内容
        return view;
    }
}
