package com.example.homepageview.contract;

import com.example.homepageview.base.BaseView;

import java.util.List;

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
