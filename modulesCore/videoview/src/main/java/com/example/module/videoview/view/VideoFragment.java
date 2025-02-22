package com.example.module.videoview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.module.videoview.R;
import com.example.module.videoview.contract.IVideoContract;
import com.example.module.videoview.model.classes.Video;
import com.example.module.videoview.view.adapter.VideoShowAdapter;

import java.util.List;

public class VideoFragment extends Fragment implements IVideoContract.IVideoView {

    private IVideoContract.IVideoPresenter mPresenter;

    private ViewPager2 viewPager2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.vp_video_show);

        initView();
        initListener();
    }

    @Override
    public void initView() {

        mPresenter.loadViewPagerData();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void setupViewPager(List<Video.Item> list) {
        if (isAdded()) {
            viewPager2.setAdapter(new VideoShowAdapter(this, list));
        }
    }


    @Override
    public void setPresenter(IVideoContract.IVideoPresenter presenter) {
        this.mPresenter = presenter;
    }
}
