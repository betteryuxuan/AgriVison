package com.example.module.homepageview.contract;

import com.example.module.homepageview.base.BaseView;
import com.example.module.libBase.bean.Crop;

import java.util.List;

public interface ICategoryContract {

    interface ICategoryView extends BaseView<ICategoryPresenter> {

        void initView();

        void initListener();

        void setupFoodCatrgoryRecyclerView(List<Crop> list);

        void setupOilCatrgoryRecyclerView(List<Crop> list);

        void setupVegetableCatrgoryRecyclerView(List<Crop> list);

        void setupFruitCatrgoryRecyclerView(List<Crop> list);

        void setupWildFruitCatrgoryRecyclerView(List<Crop> list);

        void setupSeedCatrgoryRecyclerView(List<Crop> list);

        void setupMedicinalCatrgoryRecyclerView(List<Crop> list);
    }

    interface ICategoryPresenter {

        void loadFoodCategoryData();

        void loadOilCategoryData();

        void loadVegetableCategoryData();

        void loadFruitCategoryData();

        void loadWildFruitCategoryData();

        void loadSeedCategoryData();

        void loadMedicinalCategoryData();
    }

    interface ICategoryModel {

        List<Crop> getFoodCategoryData();

        List<Crop> getOilCategoryData();

        List<Crop> getVegetableCategoryData();

        List<Crop> getFruitCategoryData();

        List<Crop> getWildFruitCategoryData();

        List<Crop> getSeedCategoryData();

        List<Crop> getMedicinalCategoryData();
    }

}
