package com.example.module.videoview.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.module.libBase.TokenManager;
import com.example.module.videoview.contract.IVideoContract;
import com.example.module.videoview.model.classes.Video;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class VideoModel implements IVideoContract.IVideoModel {

    private static final String VIDEO_URL = "http://101.200.122.3:8080/firstpage/video";

    private static final String TAG = "VideoModel";

    private Context mContext;

    public VideoModel(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getViewPagerData(VideoCallback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String token = TokenManager.getToken(mContext);
        Log.d(TAG, "Token为：" + token);

        Request.Builder builder = new Request.Builder();
        builder.url(VIDEO_URL);
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
                Video video = gson.fromJson(responseBody, Video.class);
                Log.d(TAG, "解析后的数据：" + video);

                // 确保回调在主线程中执行
                if (callback != null) {
                    new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(video.getData()));
                }
            }

        });
    }
}
