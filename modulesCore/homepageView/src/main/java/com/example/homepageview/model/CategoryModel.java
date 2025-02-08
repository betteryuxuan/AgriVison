package com.example.homepageview.model;

import com.example.homepageview.R;
import com.example.homepageview.contract.ICategoryContract;
import com.example.homepageview.model.classes.Crop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryModel implements ICategoryContract.ICategoryModel {
    @Override
    public List<Crop> getFoodCategoryData() {
        List<Crop> crops = new ArrayList<>();
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        return crops;
    }

    @Override
    public List<Crop> getOilCategoryData() {
        List<Crop> crops = new ArrayList<>();
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        return crops;
    }

    @Override
    public List<Crop> getVegetableCategoryData() {
        List<Crop> crops = new ArrayList<>();
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        return crops;
    }

    @Override
    public List<Crop> getFruitCategoryData() {
        List<Crop> crops = new ArrayList<>();
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        return crops;
    }

    @Override
    public List<Crop> getWildFruitCategoryData() {
        List<Crop> crops = new ArrayList<>();
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        return crops;
    }

    @Override
    public List<Crop> getSeedCategoryData() {
        List<Crop> crops = new ArrayList<>();
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        return crops;
    }

    @Override
    public List<Crop> getMedicinalCategoryData() {
        List<Crop> crops = new ArrayList<>();
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        return crops;
    }
}
