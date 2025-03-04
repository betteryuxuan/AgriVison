package com.example.module.homepageview.contract;

import com.example.module.homepageview.base.BaseView;
import com.example.module.libBase.bean.Crop;
import com.example.module.homepageview.model.classes.News;
import com.example.module.homepageview.model.classes.Proverb;

import java.io.IOException;
import java.util.List;

public interface IHomeFirstContract {

    interface IHomeFirstView extends BaseView<IHomeFirstPresenter> {

        void initView();

        void initListener();

        void initAinm();

        void setupBanner(List<Integer> list);

        void setupCropRecyclerView(List<Crop.DataItem> list);

        void setupNewsRecyclerView(List<News.Item> list);

        void setupProverbViewPager(List<Proverb.ProverbData> list);
    }

    interface IHomeFirstPresenter {

        void loadBannerDatas();

        void loadCropRecyclerViewDatas();

        void loadNewsRecyclerViewDatas();

        void loadProverbViewPagerDatas();
    }

    interface IHomeFirstModel<T> {

        List<Integer> getBannerDatas();

        void getCropRecyclerViewDatas(CropsCallback callback);

        void getNewsRecyclerViewDatas(NewsCallback callback);

        void getProverbViewPagerDatas(ProverbCallback callback);

        interface CropsCallback {
            void onCropsLoaded(List<Crop.DataItem> data);
            void onError(IOException e);
        }

        interface NewsCallback {
            void onNewsLoaded(List<News.Item> data);
            void onError(IOException e);
        }

        interface ProverbCallback {
            void onProverbsLoaded(List<Proverb.ProverbData> data);
            void onError(IOException e);
        }
    }

}
