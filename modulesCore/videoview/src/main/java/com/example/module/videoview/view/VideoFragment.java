package com.example.module.videoview.view;

import androidx.fragment.app.Fragment;

import com.example.module.videoview.contract.IVideoContract;

public class VideoFragment extends Fragment implements IVideoContract.IVideoView {

    private IVideoContract.IVideoPresenter mPresenter;

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void setPresenter(IVideoContract.IVideoPresenter presenter) {
        this.mPresenter = presenter;
    }
}
