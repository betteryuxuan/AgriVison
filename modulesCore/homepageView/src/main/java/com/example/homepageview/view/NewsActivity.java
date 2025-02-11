package com.example.homepageview.view;

import android.content.Intent;
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

import com.example.homepageview.R;

public class NewsActivity extends AppCompatActivity {

    private ImageView back;
    private ImageView image;
    private TextView text, updateTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.news_main), (v, insets) -> {
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
        back = findViewById(R.id.iv_news_back);
        image = findViewById(R.id.iv_news_image);
        text = findViewById(R.id.tv_news_text);
        updateTime = findViewById(R.id.tv_news_time);

        Intent intent = getIntent();
        if (intent != null) {
            String text1 = intent.getStringExtra("text");
            int image1 = intent.getIntExtra("image", 0);
            image.setImageResource(image1);
            text.setText(text1);
            updateTime.setText("更新时间：2周前");
        }
    }
}
