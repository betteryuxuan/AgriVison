package com.example.module.homepageview.presenter;

import com.example.module.homepageview.contract.IHomeFirstContract;
import com.example.module.homepageview.model.classes.Crop;
import com.example.module.homepageview.model.classes.News;
import com.example.module.homepageview.model.classes.Proverb;

import java.util.List;

public class HomeFirstPresenter implements IHomeFirstContract.IHomeFirstPresenter {

    private IHomeFirstContract.IHomeFirstView homeFirstView;
    private IHomeFirstContract.IHomeFirstModel homeFirstModel;

    public HomeFirstPresenter(IHomeFirstContract.IHomeFirstView homeFirstView, IHomeFirstContract.IHomeFirstModel homeFirstModel) {
        this.homeFirstView = homeFirstView;
        this.homeFirstModel = homeFirstModel;
    }

    @Override
    public void loadBannerDatas() {
        List<Integer> bannerDatas = homeFirstModel.getBannerDatas();
        homeFirstView.setupBanner(bannerDatas);
    }

    @Override
    public void loadCropRecyclerViewDatas() {
        List<Crop> crops = homeFirstModel.getCropRecyclerViewDatas();
        homeFirstView.setupCropRecyclerView(crops);
    }

    @Override
    public void loadNewsRecyclerViewDatas() {
        List<News> newsList = homeFirstModel.getNewsRecyclerViewDatas();
        homeFirstView.setupNewsRecyclerView(newsList);
    }

    @Override
    public void loadProverbViewPagerDatas() {
        List<Proverb> proverbViewPagerDatas = homeFirstModel.getProverbViewPagerDatas();
        homeFirstView.setupProverbViewPager(proverbViewPagerDatas);
    }
}

