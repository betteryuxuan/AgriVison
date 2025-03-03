package com.example.module.homepageview.presenter;

import com.example.module.homepageview.contract.ICategoryContract;
import com.example.module.libBase.bean.Crop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryPresenter implements ICategoryContract.ICategoryPresenter {

    private ICategoryContract.ICategoryView mCategoryView;
    private ICategoryContract.ICategoryModel mCategoryModel;

    public CategoryPresenter(ICategoryContract.ICategoryView categoryFragment, ICategoryContract.ICategoryModel categoryModel) {
        mCategoryView = categoryFragment;
        mCategoryModel = categoryModel;
    }

    @Override
    public void loadCategoryDatas() {
        mCategoryModel.loadCategoryDatas(new ICategoryContract.ICategoryModel.CropsCallback() {
            @Override
            public void onCropsLoaded(Crop data) {
                List<Crop.CropDetail> foodCategoryData = new ArrayList<>();
                List<Crop.CropDetail> oilCategoryData = new ArrayList<>();
                List<Crop.CropDetail> vegetableCategoryData = new ArrayList<>();
                List<Crop.CropDetail> fruitCategoryData = new ArrayList<>();
                List<Crop.CropDetail> wildFruitCategoryData = new ArrayList<>();
                List<Crop.CropDetail> seedCategoryData = new ArrayList<>();
                List<Crop.CropDetail> medicinalCategoryData = new ArrayList<>();
                for (Crop.DataItem dataItem : data.getData()) {
                    switch (dataItem.getCategory()) {
                        case "粮食作物":
                            foodCategoryData = dataItem.getCropDetail();
                            break;
                        case "油料作物":
                            oilCategoryData = dataItem.getCropDetail();
                            break;
                        case "蔬菜作物":
                            vegetableCategoryData = dataItem.getCropDetail();
                            break;
                        case "果类":
                            fruitCategoryData = dataItem.getCropDetail();
                            break;
                        case "野生果类":
                            wildFruitCategoryData = dataItem.getCropDetail();
                            break;
                        case "饲料作物":
                            seedCategoryData = dataItem.getCropDetail();
                            break;
                        case "药用作物":
                            medicinalCategoryData = dataItem.getCropDetail();
                            break;
                        default:
                            break;
                    }
                }
                mCategoryView.setupFoodCatergoryRecyclerView(foodCategoryData);
                mCategoryView.setupOilCatergoryRecyclerView(oilCategoryData);
                mCategoryView.setupVegetableCatergoryRecyclerView(vegetableCategoryData);
                mCategoryView.setupFruitCatergoryRecyclerView(fruitCategoryData);
                mCategoryView.setupWildFruitCatergoryRecyclerView(wildFruitCategoryData);
                mCategoryView.setupSeedCatergoryRecyclerView(seedCategoryData);
                mCategoryView.setupMedicinalCatergoryRecyclerView(medicinalCategoryData);
            }

            @Override
            public void onError(IOException e) {

            }
        });
    }

}
