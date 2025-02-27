package com.example.personalinfoview.view;

import android.os.Bundle;
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
import com.example.module.libBase.bean.Crop;
import com.example.personalinfoview.R;
import com.example.personalinfoview.adapter.CropAdapter;
import com.example.personalinfoview.databinding.ActivityMyFavoritesBinding;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/personalinfoview/MyFavoritesActivity")
public class MyFavoritesActivity extends AppCompatActivity {

    private ActivityMyFavoritesBinding binding;
    private List<Crop> favoritesCropsList;

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
        favoritesCropsList = new ArrayList<>();
        favoritesCropsList.add(new Crop("小麦", R.drawable.ic_wheat));
        favoritesCropsList.add(new Crop("玉米", R.drawable.ic_corn));
        favoritesCropsList.add(new Crop("水稻", R.drawable.ic_barley));
        favoritesCropsList.add(new Crop("小麦", R.drawable.ic_wheat));
        favoritesCropsList.add(new Crop("玉米", R.drawable.ic_corn));
        favoritesCropsList.add(new Crop("水稻", R.drawable.ic_barley));
        favoritesCropsList.add(new Crop("小麦", R.drawable.ic_wheat));
        favoritesCropsList.add(new Crop("玉米", R.drawable.ic_corn));
        favoritesCropsList.add(new Crop("水稻", R.drawable.ic_barley));
    }

    public void setupCropRecyclerView(List<Crop> list) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvFavoritesCrops.setAdapter(new CropAdapter(list, new CropAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .navigation();
            }
        }));
        binding.rvFavoritesCrops.setLayoutManager(linearLayoutManager);
    }
}