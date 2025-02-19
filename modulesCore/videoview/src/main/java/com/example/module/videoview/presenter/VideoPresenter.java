package com.example.module.videoview.presenter;

import com.example.module.videoview.contract.IVideoContract;

public class VideoPresenter implements IVideoContract.IVideoPresenter {

    private IVideoContract.IVideoView mVideoView;
    private IVideoContract.IVideoModel mVideoModel;

    public VideoPresenter(IVideoContract.IVideoView mVideoView, IVideoContract.IVideoModel mVideoModel) {
        this.mVideoView = mVideoView;
        this.mVideoModel = mVideoModel;
    }
}
