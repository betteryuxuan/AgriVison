package com.example.module.videoview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.videoview.R;
import com.example.module.videoview.model.VideoModel;
import com.example.module.videoview.presenter.VideoPresenter;

@Route(path = "/videoview/VideoFragment")
public class BaseVideoFragment extends Fragment {

    private VideoFragment mVideoFragment;
    private VideoPresenter mVideoPresenter;
    private VideoModel mVideoModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mVideoFragment = new VideoFragment();
        mVideoModel = new VideoModel(getContext());
        mVideoPresenter = new VideoPresenter(mVideoFragment, mVideoModel);
        mVideoFragment.setPresenter(mVideoPresenter);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_base, mVideoFragment)
                .commit();
    }


}
