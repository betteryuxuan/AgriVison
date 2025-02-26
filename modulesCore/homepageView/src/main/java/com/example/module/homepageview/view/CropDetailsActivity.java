package com.example.module.homepageview.view;


import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.module.homepageview.R;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/HomePageView/CropDetailsActivity")
public class CropDetailsActivity extends AppCompatActivity {

    private ImageView back;
    private Banner banner;

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
    }

    private void initView() {
        back = findViewById(R.id.iv_cropdetails_back);
        banner = findViewById(R.id.banner_crop_details);


        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.wheat1);
        list.add(R.drawable.wheat2);
        banner.setAdapter(new BannerImageAdapter<Integer>(list) {
                    @Override
                    public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                        holder.imageView.setImageResource(data);
                    }
                }).setIndicator(new CircleIndicator(this))
                .addBannerLifecycleObserver(this)
                .setIndicatorSelectedColor(getResources().getColor(R.color.white))
                .setIndicatorNormalColor(getResources().getColor(R.color.white_shallow))
                .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                .setLoopTime(3000);
    }
}
