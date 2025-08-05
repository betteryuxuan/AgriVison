package com.example.module.homepageview.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.homepageview.R;
import com.example.module.homepageview.model.classes.CropBack;
import com.example.module.homepageview.model.classes.Keyword;
import com.example.module.homepageview.view.adapter.SearchRecyclerViewAdapter;
import com.example.module.libBase.utils.TokenManager;
import com.example.module.libBase.bean.Crop;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

interface SearchCallback {
    void onCropsLoaded(List<Keyword.Item> keywordList);
    void onError(Exception e);
}

interface SearchForCropCallback {
    void onCropsLoaded(Crop.CropDetail cropDetail);
    void onError(Exception e);
}

@Route(path = "/HomePageView/SearchActivity")
public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";

    private static final String SEARCH_URL = "http://101.200.122.3:8080/search";
    private static final String SEARCH_CROP_URL = "http://101.200.122.3:8080/search/";

    private EditText etSearch;
    private ImageButton btnBack;
    private RecyclerView searchRecyclerView;
    private TextView tvSearch, text;
    private SearchRecyclerViewAdapter adapter;
    private List<Keyword.Item> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.search_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etSearch = findViewById(R.id.et_search_text);
        btnBack = findViewById(R.id.ib_search_back);
        searchRecyclerView = findViewById(R.id.search_recycler_view);
        tvSearch = findViewById(R.id.tv_find_button);
        text = findViewById(R.id.tv_search_textview);

        // 强制获取焦点并显示键盘
        etSearch.requestFocus();
        etSearch.postDelayed(() -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 200);


        searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchRecyclerViewAdapter(list, new SearchRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Keyword.Item keyword) {
                SearchForCrop(new SearchForCropCallback() {
                    @Override
                    public void onCropsLoaded(Crop.CropDetail cropDetail) {
                        ARouter.getInstance()
                                .build("/HomePageView/CropDetailsActivity")
                                .withParcelable("cropDetail", cropDetail)
                                .navigation();
                    }
                    @Override
                    public void onError(Exception e) {

                    }
                }, keyword.getId());
            }
        });
        searchRecyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = etSearch.getText().toString();
                if (string.isEmpty() || string.equals("")) {
                    adapter.updateData(list);
                    text.setVisibility(View.VISIBLE);
                } else {
                    // 搜索
                    Search(new SearchCallback() {
                        @Override
                        public void onCropsLoaded(List<Keyword.Item> keywordList) {
                            if (keywordList != null) {
                                adapter.updateData(keywordList);
                                text.setVisibility(View.GONE);
                            } else {
                                adapter.updateData(list);
                                text.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "搜索失败：" + e.getMessage());
                        }
                    }, string);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = etSearch.getText().toString();
                if (string.isEmpty() || string.equals("")) {
                    Toast.makeText(SearchActivity.this, "搜索内容为空", Toast.LENGTH_SHORT).show();;
                } else {
                    // 搜索
                    Search(new SearchCallback() {
                        @Override
                        public void onCropsLoaded(List<Keyword.Item> keywordList) {
                            if (keywordList != null) {
                                adapter.updateData(keywordList);
                                text.setVisibility(View.GONE);
                                Toast.makeText(SearchActivity.this, "列表更新成功", Toast.LENGTH_SHORT).show();
                            } else {
                                adapter.updateData(list);
                                text.setVisibility(View.VISIBLE);
                                Toast.makeText(SearchActivity.this, "搜不到呢", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "搜索失败：" + e.getMessage());
                        }
                    }, string);
                }
            }
        });
    }

    private void Search(SearchCallback callback, String keyword) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String token = TokenManager.getToken(this);
        Log.d(TAG, "Token为：" + token);
        String searchUrl = SEARCH_URL + "?keyword=" + keyword;
        Request.Builder builder = new Request.Builder();
        builder.url(searchUrl);
        if (token != null) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "请求失败", e);
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onError(e));
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "请求失败，状态码：" + response.code());
                    if (callback != null) {
                        new Handler(Looper.getMainLooper()).post(() -> callback.onError(new IOException("请求失败，状态码：" + response.code())));
                    }
                    return;
                }

                ResponseBody body = response.body();
                if (body == null) {
                    Log.e(TAG, "响应体为空");
                    if (callback != null) {
                        callback.onError(new IOException("响应体为空"));
                    }
                    return;
                }

                String responseBody = body.string();
                Log.d(TAG, "解析前的数据：" + responseBody);

                Gson gson = new Gson();
                Keyword keyword = gson.fromJson(responseBody, Keyword.class);
                Log.d(TAG, "解析后的数据：" + keyword);

                // 确保回调在主线程中执行
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onCropsLoaded(keyword.getData()));
                }
            }
        });
    }

    private void SearchForCrop(SearchForCropCallback callback, int id) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String token = TokenManager.getToken(this);
        Log.d(TAG, "Token为：" + token);
        String searchUrl = SEARCH_CROP_URL + id;
        Request.Builder builder = new Request.Builder();
        builder.url(searchUrl);
        if (token != null) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "请求失败", e);
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onError(e));
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "请求失败，状态码：" + response.code());
                    if (callback != null) {
                        new Handler(Looper.getMainLooper()).post(() -> callback.onError(new IOException("请求失败，状态码：" + response.code())));
                    }
                    return;
                }

                ResponseBody body = response.body();
                if (body == null) {
                    Log.e(TAG, "响应体为空");
                    if (callback != null) {
                        callback.onError(new IOException("响应体为空"));
                    }
                    return;
                }

                String responseBody = body.string();
                Log.d(TAG, "解析前的数据：" + responseBody);

                Gson gson = new Gson();
                CropBack cropBack = gson.fromJson(responseBody, CropBack.class);
                Log.d(TAG, "解析后的数据：" + cropBack);

                // 确保回调在主线程中执行
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onCropsLoaded(cropBack.getData()));
                }
            }
        });
    }


}

