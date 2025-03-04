package com.example.personalinfoview.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.libBase.SPUtils;
import com.example.module.libBase.bean.Crop;
import com.example.personalinfoview.adapter.CropAdapter;
import com.example.personalinfoview.databinding.ActivityMyFavoritesBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Route(path = "/personalinfoview/MyFavoritesActivity")
public class MyFavoritesActivity extends AppCompatActivity {
    private static final String TAG = "MyFavoritesActivityTAG";

    private ActivityMyFavoritesBinding binding;
    private List<Crop.CropDetail> favoritesCropsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMyFavoritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initData();

        setupCropRecyclerView(favoritesCropsList);
        binding.imgFavoritesBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        String jsonList = SPUtils.getString(MyFavoritesActivity.this, SPUtils.CROP_DETAIL_LIST_KEY, "");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Crop.CropDetail>>(){}.getType();
        favoritesCropsList = gson.fromJson(jsonList, listType);

        if (favoritesCropsList == null) {
            favoritesCropsList = new ArrayList<>();
        }

        if (favoritesCropsList.isEmpty()) {
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.rvFavoritesCrops.setVisibility(View.GONE);
        } else {
            binding.tvEmpty.setVisibility(View.GONE);
            binding.rvFavoritesCrops.setVisibility(View.VISIBLE);
        }
    }


    public void setupCropRecyclerView(List<Crop.CropDetail> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvFavoritesCrops.setAdapter(new CropAdapter(list, new CropAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        }));
        binding.rvFavoritesCrops.setLayoutManager(linearLayoutManager);
    }


    @Override
    protected void onStart() {
        super.onStart();
        initData();
        setupCropRecyclerView(favoritesCropsList);
    }
}