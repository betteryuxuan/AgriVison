package com.example.module.homepageview.view;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.module.homepageview.R;


public class NewsActivity extends AppCompatActivity {

    private ImageView back;
    private static final String TAG = "NewsActivity";

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

        WebView webView = findViewById(R.id.wv_news_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        // 启用缩放支持
        webView.getSettings().setSupportZoom(true);  // 支持缩放
        webView.getSettings().setBuiltInZoomControls(true);  // 内置缩放控制
        webView.getSettings().setDisplayZoomControls(false);  // 不显示缩放按钮

        // 启用适应屏幕的功能
        webView.getSettings().setLoadWithOverviewMode(true); // 加载时自适应
        webView.getSettings().setUseWideViewPort(true); // 设置网页的视口大小
        webView.setWebViewClient(new WebViewClient());

        String htmlContent = getIntent().getStringExtra("htmlContent");

        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
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
    }
}

