package com.example.homepageview.presenter;

import com.example.homepageview.contract.IHomeFirstContract;
import com.example.homepageview.model.Corn;
import com.example.homepageview.model.HomeFirstModel;
import com.example.homepageview.model.News;
import com.example.homepageview.view.HomeFirstFragment;

import java.util.List;

public class HomeFirstPresenter implements IHomeFirstContract.IHomeFirstPresenter {

    private HomeFirstFragment homeFirstFragment;
    private HomeFirstModel homeFirstModel;

    public HomeFirstPresenter(HomeFirstFragment homeFirstFragment, HomeFirstModel homeFirstModel) {
        this.homeFirstFragment = homeFirstFragment;
        this.homeFirstModel = homeFirstModel;
    }

    @Override
    public void loadBannerDatas() {
        List<Integer> bannerDatas = homeFirstModel.getBannerDatas();
        homeFirstFragment.setupBanner(bannerDatas);
    }

    @Override
    public void loadCornRecyclerViewDatas() {
        List<Corn> corns = homeFirstModel.getCornRecyclerViewDatas();
        homeFirstFragment.setupCornRecyclerView(corns);
    }

    @Override
    public void loadNewsRecyclerViewDatas() {
        List<News> newsList = homeFirstModel.getNewsRecyclerViewDatas();
        homeFirstFragment.setupNewsRecyclerView(newsList);
    }
}
