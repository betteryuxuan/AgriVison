package com.example.module.homepageview.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.homepageview.R;
import com.example.module.homepageview.contract.ICategoryContract;
import com.example.module.homepageview.view.adapter.CropCategoryRecyclerViewAdapter;
import com.example.module.libBase.bean.Crop;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements ICategoryContract.ICategoryView {

    private ICategoryContract.ICategoryPresenter presenter;
    private RecyclerView foodRecyclerView, oilRecyclerView, vegetableRecyclerView, fruitRecyclerView, wildFruitRecyclerView, seedRecyclerView, medicinalRecyclerView;
    private ImageView foodButton, oilButton, vegetableButton, fruitButton, wildFruitButton, seedButton, medicinalButton;
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

        foodButton = view.findViewById(R.id.iv_category_food);
        oilButton = view.findViewById(R.id.iv_category_oil);
        vegetableButton = view.findViewById(R.id.iv_category_vegetable);
        fruitButton = view.findViewById(R.id.iv_category_fruit);
        wildFruitButton = view.findViewById(R.id.iv_category_wild_fruit);
        seedButton = view.findViewById(R.id.iv_category_seed);
        medicinalButton = view.findViewById(R.id.iv_category_medicinal);
        initView();
        initListener();
    }

    @Override
    public void initView() {

        presenter.loadCategoryDatas();

    }

    @Override
    public void initListener() {

    }

    @Override
    public void setupFoodCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        foodRecyclerView.setLayoutManager(linearLayoutManager);
        foodRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));

        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "粮食作物");
                startActivity(intent);
            }
        });
    }

    @Override
    public void setupOilCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        oilRecyclerView.setLayoutManager(linearLayoutManager);
        oilRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));

        oilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "油料作物");
                startActivity(intent);
            }
        });
    }

    @Override
    public void setupVegetableCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        vegetableRecyclerView.setLayoutManager(linearLayoutManager);
        vegetableRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));

        vegetableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "蔬菜作物");
                startActivity(intent);
            }
        });
    }

    @Override
    public void setupFruitCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        fruitRecyclerView.setLayoutManager(linearLayoutManager);
        fruitRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));

        fruitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "水果作物");
                startActivity(intent);
            }
        });
    }

    @Override
    public void setupWildFruitCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        wildFruitRecyclerView.setLayoutManager(linearLayoutManager);
        wildFruitRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));

        wildFruitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "野果作物");
                startActivity(intent);
            }
        });
    }

    @Override
    public void setupSeedCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        seedRecyclerView.setLayoutManager(linearLayoutManager);
        seedRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));

        seedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "种子作物");
                startActivity(intent);
            }
        });
    }

    @Override
    public void setupMedicinalCatergoryRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        medicinalRecyclerView.setLayoutManager(linearLayoutManager);
        medicinalRecyclerView.setAdapter(new CropCategoryRecyclerViewAdapter(list, new CropCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }, getContext()));

        medicinalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CategoryDetailsActivity.class);
                intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
                intent.putExtra("title", "药草作物");
                startActivity(intent);
            }
        });
    }

    @Override
    public void setPresenter(ICategoryContract.ICategoryPresenter presenter) {
        this.presenter = presenter;
    }
}
