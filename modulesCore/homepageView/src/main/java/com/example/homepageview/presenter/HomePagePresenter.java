package com.example.homepageview.presenter;

import com.example.homepageview.contract.IHomePageContract;

public class HomePagePresenter implements IHomePageContract.IHomePagePresenter {
    private IHomePageContract.IHomePageView mHomePageView;
    private IHomePageContract.IHomePageModel mHomePageModel;

    public HomePagePresenter(IHomePageContract.IHomePageView mHomePageView, IHomePageContract.IHomePageModel mHomePageModel) {
        this.mHomePageView = mHomePageView;
        this.mHomePageModel = mHomePageModel;
    }

}
