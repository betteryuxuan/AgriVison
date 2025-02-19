package com.example.module.videoview.contract;

import com.example.module.videoview.base.BaseView;

public interface IVideoContract {

    interface IVideoView extends BaseView<IVideoPresenter> {

        void initView();

        void initListener();
    }

    interface IVideoPresenter {

    }

    interface IVideoModel {

    }
}
