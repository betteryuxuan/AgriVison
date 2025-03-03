package com.example.module.homepageview.view;


import android.os.Bundle;
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
import com.example.module.libBase.bean.Crop;
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
    private TextView name, description, introduction;

    @Autowired
    Crop.DataItem dataItem;

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
        name = findViewById(R.id.tv_cropdetails_name);
        introduction= findViewById(R.id.tv_cropdetails_text);
        description= findViewById(R.id.tv_cropdetails_introduce);

        ARouter.getInstance().inject(this);

        List<String> list = new ArrayList<>();
        list.add(dataItem.getCropDetail().get(0).getImage1());
        list.add(dataItem.getCropDetail().get(0).getImage2());


        name.setText(dataItem.getCropDetail().get(0).getName());

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

    }
}
