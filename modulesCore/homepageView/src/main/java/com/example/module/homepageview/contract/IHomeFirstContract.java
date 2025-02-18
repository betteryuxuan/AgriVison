package com.example.module.homepageview.contract;

import com.example.module.homepageview.base.BaseView;
import com.example.module.homepageview.model.classes.Crop;
import com.example.module.homepageview.model.classes.News;
import com.example.module.homepageview.model.classes.Proverb;

import java.util.List;

public interface IHomeFirstContract {

    interface IHomeFirstView extends BaseView<IHomeFirstPresenter> {

        void initView();

        void initListener();

        void initAinm();

        void setupBanner(List<Integer> list);

        void setupCropRecyclerView(List<Crop> list);

        void setupNewsRecyclerView(List<News> list);

        void setupProverbViewPager(List<Proverb> list);
    }

    interface IHomeFirstPresenter {

        void loadBannerDatas();

        void loadCropRecyclerViewDatas();

        void loadNewsRecyclerViewDatas();

        void loadProverbViewPagerDatas();
    }

    interface IHomeFirstModel<T> {

        List<Integer> getBannerDatas();

        List<Crop> getCropRecyclerViewDatas();

        List<News> getNewsRecyclerViewDatas();

        List<Proverb> getProverbViewPagerDatas();
    }
}
