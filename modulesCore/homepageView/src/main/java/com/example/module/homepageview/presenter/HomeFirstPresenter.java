package com.example.module.homepageview.presenter;

import android.content.Context;

import com.example.module.homepageview.contract.IHomeFirstContract;
import com.example.module.libBase.bean.Crop;
import com.example.module.homepageview.model.classes.News;
import com.example.module.homepageview.model.classes.Proverb;

import java.io.IOException;
import java.util.List;

public class HomeFirstPresenter implements IHomeFirstContract.IHomeFirstPresenter {

    private IHomeFirstContract.IHomeFirstView homeFirstView;
    private IHomeFirstContract.IHomeFirstModel homeFirstModel;
    private Context mContext;

    public HomeFirstPresenter(IHomeFirstContract.IHomeFirstView homeFirstView, IHomeFirstContract.IHomeFirstModel homeFirstModel, Context mContext) {
        this.homeFirstView = homeFirstView;
        this.homeFirstModel = homeFirstModel;
        this.mContext = mContext;
    }

    @Override
    public void loadBannerDatas() {
        List<Integer> bannerDatas = homeFirstModel.getBannerDatas();
        homeFirstView.setupBanner(bannerDatas);
    }

    @Override
    public void loadCropRecyclerViewDatas() {
        homeFirstModel.getCropRecyclerViewDatas(new IHomeFirstContract.IHomeFirstModel.CropsCallback() {
            @Override
            public void onCropsLoaded(List<Crop.DataItem> data) {
                homeFirstView.setupCropRecyclerView(data);
            }

            @Override
            public void onError(IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void loadNewsRecyclerViewDatas() {
        homeFirstModel.getNewsRecyclerViewDatas(new IHomeFirstContract.IHomeFirstModel.NewsCallback() {
            @Override
            public void onNewsLoaded(List<News.Item> data) {
                if (homeFirstView != null) {
                    homeFirstView.setupNewsRecyclerView(data);
                }
            }
            @Override
            public void onError(IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void loadProverbViewPagerDatas() {
        homeFirstModel.getProverbViewPagerDatas(new IHomeFirstContract.IHomeFirstModel.ProverbCallback() {
            @Override
            public void onProverbsLoaded(List<Proverb.ProverbData> data) {
                if (homeFirstView != null) {
                    homeFirstView.setupProverbViewPager(data);
                }
            }
            @Override
            public void onError(IOException e) {
                e.printStackTrace();

            }
        });
    }
}

