package com.example.module.videoview.contract;

import com.example.module.videoview.base.BaseView;
import com.example.module.videoview.model.classes.Video;

import java.io.IOException;
import java.util.List;

public interface IVideoContract {

    interface IVideoView extends BaseView<IVideoPresenter> {

        void initView();

        void initListener();

        void setupViewPager(List<Video.Item> list);
    }

    interface IVideoPresenter {

        void loadViewPagerData();
    }

    interface IVideoModel {

        void getViewPagerData(VideoCallback callback);

        interface VideoCallback {
            void onSuccess(List<Video.Item> list);
            void onError(IOException e);
        }
    }
}
