package com.example.module.homepageview.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.module.homepageview.R;
import com.example.module.homepageview.contract.IHomeFirstContract;
import com.example.module.homepageview.model.classes.Crop;
import com.example.module.homepageview.model.classes.News;
import com.example.module.homepageview.model.classes.Proverb;
import com.example.module.homepageview.presenter.HomePagePresenter;
import com.example.module.libBase.TokenManager;
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

public class HomeFirstModel implements IHomeFirstContract.IHomeFirstModel<HomePagePresenter> {

    private static final String CROP_URL = "http://101.200.122.3:8080/firstpage/crop";
    private static final String PROVERB_URL = "http://101.200.122.3:8080/firstpage/proverb";
    private static final String NEWS_URL = "http://101.200.122.3:8080/firstpage/news";

    private Context mContext;
    private static final String TAG = "HomeFirstModel";

    public HomeFirstModel(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public List<Integer> getBannerDatas() {

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.example_pic1);
        list.add(R.drawable.example_pic2);
        list.add(R.drawable.example_pic3);

        return list;
    }

//    @Override
//    public void getCropRecyclerViewDatas(CropsCallback callback) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        String token = TokenManager.getToken(mContext);
//        Log.d(TAG, "Token为：" + token);
//
//        Request.Builder builder = new Request.Builder();
//        builder.url(CROP_URL);
//        if (token != null) {
//            builder.addHeader("Authorization", "Bearer " + token);
//        }
//        Request request = builder.build();
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.e(TAG, "请求失败", e);
//                if (callback != null) {
//                    callback.onError(e);
//                }
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    Log.e(TAG, "请求失败，状态码：" + response.code());
//                    if (callback != null) {
//                        callback.onError(new IOException("请求失败，状态码：" + response.code()));
//                    }
//                    return;
//                }
//
//                ResponseBody body = response.body();
//                if (body == null) {
//                    Log.e(TAG, "响应体为空");
//                    if (callback != null) {
//                        callback.onError(new IOException("响应体为空"));
//                    }
//                    return;
//                }
//
//                String responseBody = body.string();
//                Log.d(TAG, "解析前的数据crops：" + responseBody);
//
//                Gson gson = new Gson();
//                List<Crop> crops = gson.fromJson(responseBody, Crop.class);
//                Log.d(TAG, "解析后的数据：" + crops);
//
//                // 确保回调在主线程中执行
//                if (callback != null) {
//                    new Handler(Looper.getMainLooper()).post(() -> callback.onCropsLoaded(crop));
//                }
//            }
//
//        });
//    }

    @Override
    public List<Crop> getCropRecyclerViewDatas() {

        List<Crop> crops = new ArrayList<>();
        crops.add(new Crop("小麦", R.drawable.ic_wheat));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("水稻", R.drawable.ic_barley));
        crops.add(new Crop("小麦", R.drawable.ic_wheat));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("水稻", R.drawable.ic_barley));
        crops.add(new Crop("小麦", R.drawable.ic_wheat));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("水稻", R.drawable.ic_barley));
        crops.add(new Crop("小麦", R.drawable.ic_wheat));
        crops.add(new Crop("玉米", R.drawable.ic_corn));
        crops.add(new Crop("水稻", R.drawable.ic_barley));
        return crops;
    }

    @Override
    public void getNewsRecyclerViewDatas(NewsCallback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String token = TokenManager.getToken(mContext);
        Log.d(TAG, "Token为：" + token);

        Request.Builder builder = new Request.Builder();
        builder.url(NEWS_URL);
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
                News news = gson.fromJson(responseBody, News.class);
                Log.d(TAG, "解析后的数据：" + news);

                // 确保回调在主线程中执行
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onNewsLoaded(news.getData()));
                }
            }

        });
    }


    @Override
    public void getProverbViewPagerDatas(ProverbCallback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String token = TokenManager.getToken(mContext);
        Log.d(TAG, "Token为：" + token);

        Request.Builder builder = new Request.Builder();
        builder.url(PROVERB_URL);
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
                Proverb proverb = gson.fromJson(responseBody, Proverb.class);
                Log.d(TAG, "解析后的数据：" + proverb);

                // 确保回调在主线程中执行
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onProverbsLoaded(proverb.getData()));
                }
            }

        });
    }
}
