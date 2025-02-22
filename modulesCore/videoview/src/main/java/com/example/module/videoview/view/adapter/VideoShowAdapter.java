package com.example.module.videoview.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.module.videoview.model.classes.Video;
import com.example.module.videoview.view.VideoShowFragment;

import java.util.List;

public class VideoShowAdapter extends FragmentStateAdapter {

    List<Video.Item> urlList;

    public VideoShowAdapter(@NonNull FragmentActivity fragmentActivity, List<Video.Item> urlList) {
        super(fragmentActivity);
        this.urlList = urlList;
    }

    public VideoShowAdapter(@NonNull Fragment fragment, List<Video.Item> urlList) {
        super(fragment);
        this.urlList = urlList;
    }

    public VideoShowAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<Video.Item> urlList) {
        super(fragmentManager, lifecycle);
        this.urlList = urlList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new VideoShowFragment(urlList.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return urlList == null ? 0 : urlList.size();
    }
}
