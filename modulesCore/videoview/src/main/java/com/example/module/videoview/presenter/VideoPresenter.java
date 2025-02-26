package com.example.module.videoview.presenter;

import com.example.module.videoview.contract.IVideoContract;
import com.example.module.videoview.model.classes.Video;

import java.io.IOException;
import java.util.List;

public class VideoPresenter implements IVideoContract.IVideoPresenter {

    private IVideoContract.IVideoView mVideoView;
    private IVideoContract.IVideoModel mVideoModel;

    public VideoPresenter(IVideoContract.IVideoView mVideoView, IVideoContract.IVideoModel mVideoModel) {
        this.mVideoView = mVideoView;
        this.mVideoModel = mVideoModel;
    }

    @Override
    public void loadViewPagerData() {
        mVideoModel.getViewPagerData(new IVideoContract.IVideoModel.VideoCallback() {
            @Override
            public void onSuccess(List<Video.Item> list) {
                if (mVideoView != null) {
                    mVideoView.setupViewPager(list);
                }
            }

            @Override
            public void onError(IOException e) {
                e.printStackTrace();
            }

        });
    }
}
