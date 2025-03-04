package com.example.module.homepageview.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.example.module.homepageview.R;
import com.example.module.libBase.SPUtils;
import com.example.module.libBase.bean.Crop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Route(path = "/HomePageView/CropDetailsActivity")
public class CropDetailsActivity extends AppCompatActivity {

    private static final String TAG = "CropDetailsActivity";

    private SharedPreferences sharedPreferences;
    private ImageView back;
    private Banner banner;
    private TextView name, spell, description, introduction;
    private ImageView collect;
    private boolean isCollected;

    @Autowired
    Crop.DataItem dataItem;

    @Autowired
    Crop.CropDetail cropDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.crop_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        initView();
        initListenter();
    }

    private void initListenter() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollected) {
                    String jsonList = SPUtils.getString(CropDetailsActivity.this, SPUtils.CROP_DETAIL_LIST_KEY, "");
                    // 使用 Gson 将 JSON 字符串转换回 List
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Crop.CropDetail>>() {
                    }.getType();
                    List<Crop.CropDetail> cropDetailList = gson.fromJson(jsonList, listType);
                    Log.d(TAG, "cropDetailList1: " + cropDetailList);
                    if (cropDetailList == null) {
                        cropDetailList = new ArrayList<>();
                    }
                    // 删除所有同名的 cropDetail
                    for (int i = cropDetailList.size() - 1; i >= 0; i--) {
                        if (cropDetailList.get(i).getName().equals(cropDetail.getName())) {
                            cropDetailList.remove(i);
                        }
                    }
                    String jsonListAfterDelete = gson.toJson(cropDetailList);
                    SPUtils.putString(CropDetailsActivity.this, SPUtils.CROP_DETAIL_LIST_KEY,  jsonListAfterDelete);
                    collect.setImageResource(R.drawable.ic_collect);
                    isCollected = false;
                } else {
                    if (cropDetail != null) {
                        String jsonList = SPUtils.getString(CropDetailsActivity.this, SPUtils.CROP_DETAIL_LIST_KEY, "");
                        // 使用 Gson 将 JSON 字符串转换回 List
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Crop.CropDetail>>() {
                        }.getType();
                        List<Crop.CropDetail> cropDetailList = gson.fromJson(jsonList, listType);
                        Log.d(TAG, "cropDetailList2: " + cropDetailList);
                        if (cropDetailList == null) {
                            cropDetailList = new ArrayList<>();
                        }
                        cropDetailList.add(cropDetail);
                        String jsonListAfterAdd = gson.toJson(cropDetailList);
                        SPUtils.putString(CropDetailsActivity.this, SPUtils.CROP_DETAIL_LIST_KEY, jsonListAfterAdd);
                        collect.setImageResource(R.drawable.ic_collected);
                        isCollected = true;
                    }
                }
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.iv_cropdetails_back);
        banner = findViewById(R.id.banner_crop_details);
        name = findViewById(R.id.tv_cropdetails_name);
        spell = findViewById(R.id.tv_cropdetails_name_spell);
        introduction = findViewById(R.id.tv_cropdetails_text);
        description = findViewById(R.id.tv_cropdetails_introduce);
        collect = findViewById(R.id.iv_cropdetails_collect);

        ARouter.getInstance().inject(this);


        if (dataItem != null) {
            name.setText(dataItem.getCropDetail().get(0).getName());
            List<String> list = new ArrayList<>();
            list.add(dataItem.getCropDetail().get(0).getImage1());
            list.add(dataItem.getCropDetail().get(0).getImage2());
            banner.setAdapter(new BannerImageAdapter<String>(list) {
                        @Override
                        public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                            Glide.with(holder.itemView)
                                    .load(data)  // 这里的 data 就是 URL
                                    .into(holder.imageView);
                        }
                    }).setIndicator(new CircleIndicator(this))
                    .addBannerLifecycleObserver(this)
                    .setIndicatorSelectedColor(getResources().getColor(R.color.white))
                    .setIndicatorNormalColor(getResources().getColor(R.color.white_shallow))
                    .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                    .setLoopTime(3000);
            description.setText(dataItem.getCropDetail().get(0).getDescription());
            introduction.setText(dataItem.getCropDetail().get(0).getIntroduction());
            spell.setText(dataItem.getCropDetail().get(0).getSpell());
            collect.setVisibility(View.GONE);
        } else if (cropDetail != null) {
            String jsonList = SPUtils.getString( CropDetailsActivity.this, SPUtils.CROP_DETAIL_LIST_KEY, "");

            // 使用 Gson 将 JSON 字符串转换回 List<Crop.CropDetail>
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Crop.CropDetail>>() {
            }.getType();
            List<Crop.CropDetail> cropDetailList = gson.fromJson(jsonList, listType);
            if (cropDetailList == null) {
                cropDetailList = new ArrayList<>();
            }
            Log.d(TAG, "cropDetailList3: " + cropDetailList);
            if (cropDetailList != null) {
                for (Crop.CropDetail cropDetailItem : cropDetailList) {
                    if (cropDetailItem.getName().equals(cropDetail.getName())) {
                        isCollected = true;
                        collect.setImageResource(R.drawable.ic_collected);
                        break;
                    }
                }
            }

            name.setText(cropDetail.getName());
            List<String> list = new ArrayList<>();
            list.add(cropDetail.getImage1());
            list.add(cropDetail.getImage2());
            banner.setAdapter(new BannerImageAdapter<String>(list) {
                        @Override
                        public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                            Glide.with(holder.itemView)
                                    .load(data)  // 这里的 data 就是 URL
                                    .into(holder.imageView);
                        }
                    }).setIndicator(new CircleIndicator(this))
                    .addBannerLifecycleObserver(this)
                    .setIndicatorSelectedColor(getResources().getColor(R.color.white))
                    .setIndicatorNormalColor(getResources().getColor(R.color.white_shallow))
                    .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                    .setLoopTime(3000);
            description.setText(cropDetail.getDescription());
            introduction.setText(cropDetail.getIntroduction());
            spell.setText(cropDetail.getSpell());
        }
    }
}
