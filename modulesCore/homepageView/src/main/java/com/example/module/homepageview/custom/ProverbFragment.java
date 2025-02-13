package com.example.module.homepageview.custom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.module.homepageview.R;


public class ProverbFragment extends Fragment {

    private static final String ARG_Title = "title";
    private static final String ARG_Meaning = "meaning";

    public static ProverbFragment newInstance(String title, String meaning) {
        ProverbFragment fragment = new ProverbFragment();
        Bundle args = new Bundle();
        args.putString(ARG_Title, title);
        args.putString(ARG_Meaning, meaning);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_proverb, null);
        // 获取传递的数据
        String title = getArguments() != null ? getArguments().getString(ARG_Title) : "";
        String meaning = getArguments() != null ? getArguments().getString(ARG_Meaning) : "";
        // 根据数据更新界面
        TextView textView = view.findViewById(R.id.tv_homepage_proverb);
        textView.setText(title);  // 设置不同的文本内容
        ConstraintLayout constraintLayout = view.findViewById(R.id.cl_proverb_layout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetFragment fragment = BottomSheetFragment.newInstance(title, meaning);
                fragment.show(getActivity().getSupportFragmentManager(), "BottomSheetDialog");

            }
        });
        return view;
    }
}
