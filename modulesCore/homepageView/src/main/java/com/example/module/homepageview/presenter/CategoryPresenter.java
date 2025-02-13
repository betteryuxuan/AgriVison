package com.example.module.homepageview.presenter;

import com.example.module.homepageview.contract.ICategoryContract;
import com.example.module.homepageview.model.classes.Crop;

import java.util.List;

public class CategoryPresenter implements ICategoryContract.ICategoryPresenter {

    private ICategoryContract.ICategoryView mCategoryView;
    private ICategoryContract.ICategoryModel mCategoryModel;

    public CategoryPresenter(ICategoryContract.ICategoryView categoryFragment, ICategoryContract.ICategoryModel categoryModel) {
        mCategoryView = categoryFragment;
        mCategoryModel = categoryModel;
    }

    @Override
    public void loadFoodCategoryData() {
        List<Crop> foodCategoryData = mCategoryModel.getFoodCategoryData();
        mCategoryView.setupFoodCatrgoryRecyclerView(foodCategoryData);
    }

    @Override
    public void loadOilCategoryData() {
        List<Crop> oilCategoryData = mCategoryModel.getOilCategoryData();
        mCategoryView.setupOilCatrgoryRecyclerView(oilCategoryData);
    }

    @Override
    public void loadVegetableCategoryData() {
        List<Crop> vegetableCategoryData = mCategoryModel.getVegetableCategoryData();
        mCategoryView.setupVegetableCatrgoryRecyclerView(vegetableCategoryData);
    }

    @Override
    public void loadFruitCategoryData() {
        List<Crop> fruitCategoryData = mCategoryModel.getFruitCategoryData();
        mCategoryView.setupFruitCatrgoryRecyclerView(fruitCategoryData);
    }

    @Override
    public void loadWildFruitCategoryData() {
        List<Crop> wildFruitCategoryData = mCategoryModel.getWildFruitCategoryData();
        mCategoryView.setupWildFruitCatrgoryRecyclerView(wildFruitCategoryData);
    }

    @Override
    public void loadSeedCategoryData() {
        List<Crop> seedCategoryData = mCategoryModel.getSeedCategoryData();
        mCategoryView.setupSeedCatrgoryRecyclerView(seedCategoryData);
    }

    @Override
    public void loadMedicinalCategoryData() {
        List<Crop> medicinalCategoryData = mCategoryModel.getMedicinalCategoryData();
        mCategoryView.setupMedicinalCatrgoryRecyclerView(medicinalCategoryData);
    }
}
