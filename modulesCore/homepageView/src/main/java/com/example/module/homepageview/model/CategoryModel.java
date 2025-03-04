package com.example.module.homepageview.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.module.homepageview.contract.ICategoryContract;
import com.example.module.libBase.TokenManager;
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

public class CategoryModel implements ICategoryContract.ICategoryModel {

    private static final String TAG = "CategoryModel";
    private static final String CROP_URL = "http://101.200.122.3:8080/firstpage/crop";

    private List<Crop.DataItem> foodCrops = new ArrayList<>();
    private List<Crop.DataItem> OilCrops = new ArrayList<>();
    private List<Crop.DataItem> vegetableCrops = new ArrayList<>();
    private List<Crop.DataItem> fruitCrops = new ArrayList<>();
    private List<Crop.DataItem> wildFruitCrops = new ArrayList<>();
    private List<Crop.DataItem> seedCrops = new ArrayList<>();
    private List<Crop.DataItem> medicinalCrops = new ArrayList<>();

    private Context mContext;

    public CategoryModel(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void loadCategoryDatas(CropsCallback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String token = TokenManager.getToken(mContext);
        Log.d(TAG, "Token为：" + token);

        Request.Builder builder = new Request.Builder();
        builder.url(CROP_URL);
        if (token != null) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "请求失败", e);
                if (callback != null) {
                    callback.onError(e);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "请求失败，状态码：" + response.code());
                    if (callback != null) {
                        callback.onError(new IOException("请求失败，状态码：" + response.code()));
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
                Crop crop = gson.fromJson(responseBody, Crop.class);
                Log.d(TAG, "解析后的数据：" + crop);

                // 确保回调在主线程中执行
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onCropsLoaded(crop));
                }
            }

        });
    }

}
