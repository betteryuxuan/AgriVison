package com.example.module.homepageview.presenter;

import com.example.module.homepageview.contract.IHomePageContract;

public class HomePagePresenter implements IHomePageContract.IHomePagePresenter {
    private IHomePageContract.IHomePageView mHomePageView;
    private IHomePageContract.IHomePageModel mHomePageModel;

    public HomePagePresenter(IHomePageContract.IHomePageView mHomePageView, IHomePageContract.IHomePageModel mHomePageModel) {
        this.mHomePageView = mHomePageView;
        this.mHomePageModel = mHomePageModel;
    }

}
