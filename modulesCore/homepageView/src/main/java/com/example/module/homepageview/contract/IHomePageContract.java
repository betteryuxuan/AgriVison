package com.example.module.homepageview.contract;

import com.example.module.homepageview.base.BaseView;

public interface IHomePageContract {
    interface IHomePageView extends BaseView<IHomePageContract.IHomePagePresenter> {
        void initView();
        void initListener();
    }

    interface IHomePagePresenter {
    }

    interface IHomePageModel<T> {
    }
}
