package com.example.homepageview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homepageview.R;
import com.example.homepageview.contract.ICategoryContract;
import com.example.homepageview.model.classes.Crop;
import com.example.homepageview.view.adapter.CropCategoryRecyclerViewAdapter;

import java.util.List;

public class CategoryFragment extends Fragment implements ICategoryContract.ICategoryView {

    private ICategoryContract.ICategoryPresenter presenter;
    private RecyclerView foodRecyclerView, oilRecyclerView, vegetableRecyclerView, fruitRecyclerView, wildFruitRecyclerView, seedRecyclerView, medicinalRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.categorypage_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodRecyclerView = view.findViewById(R.id.rv_category_food);
        oilRecyclerView = view.findViewById(R.id.rv_category_oil);
        vegetableRecyclerView = view.findViewById(R.id.rv_category_vegetable);
        fruitRecyclerView = view.findViewById(R.id.rv_category_fruit);
        wildFruitRecyclerView = view.findViewById(R.id.rv_category_wild_fruit);
        seedRecyclerView = view.findViewById(R.id.rv_category_seed);
        medicinalRecyclerView = view.findViewById(R.id.rv_category_medicinal);
        initView();
        initListener();
    }

    @Override
    public void initView() {
        presenter.loadFoodCategoryData();
        presenter.loadOilCategoryData();
        presenter.loadVegetableCategoryData();
        presenter.loadFruitCategoryData();
        presenter.loadWildFruitCategoryData();
        presenter.loadSeedCategoryData();
        presenter.loadMedicinalCategoryData();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void setupFoodCatrgoryRecyclerView(List<Crop> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        foodRecyclerView.setLayoutManager(linearLayoutManager);
        foodRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list));
    }

    @Override
    public void setupOilCatrgoryRecyclerView(List<Crop> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        oilRecyclerView.setLayoutManager(linearLayoutManager);
        oilRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list));
    }

    @Override
    public void setupVegetableCatrgoryRecyclerView(List<Crop> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        vegetableRecyclerView.setLayoutManager(linearLayoutManager);
        vegetableRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list));
    }

    @Override
    public void setupFruitCatrgoryRecyclerView(List<Crop> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fruitRecyclerView.setLayoutManager(linearLayoutManager);
        fruitRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list));
    }

    @Override
    public void setupWildFruitCatrgoryRecyclerView(List<Crop> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        wildFruitRecyclerView.setLayoutManager(linearLayoutManager);
        wildFruitRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list));
    }

    @Override
    public void setupSeedCatrgoryRecyclerView(List<Crop> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        seedRecyclerView.setLayoutManager(linearLayoutManager);
        seedRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list));
    }

    @Override
    public void setupMedicinalCatrgoryRecyclerView(List<Crop> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        medicinalRecyclerView.setLayoutManager(linearLayoutManager);
        medicinalRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list));
    }

    @Override
    public void setPresenter(ICategoryContract.ICategoryPresenter presenter) {
        this.presenter = presenter;
    }
}
