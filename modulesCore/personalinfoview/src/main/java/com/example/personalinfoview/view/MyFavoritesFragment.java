package com.example.personalinfoview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.libBase.utils.SPUtils;
import com.example.module.libBase.bean.Crop;
import com.example.personalinfoview.adapter.CropAdapter;
import com.example.personalinfoview.databinding.FragmentMyFavoritesBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Route(path = "/personalinfoview/MyFavoritesFragment")
public class MyFavoritesFragment extends Fragment {
    private static final String TAG = "MyFavoritesFragmentTAG";
    private FragmentMyFavoritesBinding binding;
    private List<Crop.CropDetail> favoritesCropsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMyFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        setupCropRecyclerView(favoritesCropsList);
    }

    private void initData() {
        String jsonList = SPUtils.getString(requireContext(), SPUtils.CROP_DETAIL_LIST_KEY, "");

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Crop.CropDetail>>() {}.getType();
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

    private void setupCropRecyclerView(List<Crop.CropDetail> list) {
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2);
        CropAdapter adapter = new CropAdapter(list, new CropAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Crop.CropDetail crop) {
                ARouter.getInstance()
                        .build("/HomePageView/CropDetailsActivity")
                        .withParcelable("cropDetail", crop)
                        .navigation();
            }
        });
        binding.rvFavoritesCrops.setLayoutManager(linearLayoutManager);
        binding.rvFavoritesCrops.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        setupCropRecyclerView(favoritesCropsList);
    }
}
