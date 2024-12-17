package com.example.homepageview.contract;

import com.example.homepageview.base.BaseView;
import com.example.homepageview.model.Corn;
import com.example.homepageview.model.News;

import java.util.List;

public interface IHomeFirstContract {

    interface IHomeFirstView extends BaseView<IHomeFirstPresenter> {
        void initView();
        void initListener();
        void initAinm();

        void setupBanner(List<Integer> list);
        void setupCornRecyclerView(List<Corn> list);
        void setupNewsRecyclerView(List<News> list);
    }

    interface IHomeFirstPresenter {
        void loadBannerDatas();
        void loadCornRecyclerViewDatas();
        void loadNewsRecyclerViewDatas();
    }

    interface IHomeFirstModel<T> {
        List<Integer> getBannerDatas();
        List<Corn> getCornRecyclerViewDatas();
        List<News> getNewsRecyclerViewDatas();
    }
}
