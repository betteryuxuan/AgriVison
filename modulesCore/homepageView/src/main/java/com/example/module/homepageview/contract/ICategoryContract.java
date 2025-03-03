package com.example.module.homepageview.contract;

import com.example.module.homepageview.base.BaseView;
import com.example.module.libBase.bean.Crop;

import java.io.IOException;
import java.util.List;

public interface ICategoryContract {

    interface ICategoryView extends BaseView<ICategoryPresenter> {

        void initView();

        void initListener();

        void setupFoodCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupOilCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupVegetableCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupFruitCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupWildFruitCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupSeedCatergoryRecyclerView(List<Crop.CropDetail> list);

        void setupMedicinalCatergoryRecyclerView(List<Crop.CropDetail> list);
    }

    interface ICategoryPresenter {

        void loadCategoryDatas();

    }

    interface ICategoryModel {

        void loadCategoryDatas(CropsCallback callback);


        interface CropsCallback {
            void onCropsLoaded(Crop data);
            void onError(IOException e);
        }
    }

}
