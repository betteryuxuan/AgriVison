package com.example.communityfragment.model;

import android.content.Context;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.example.communityfragment.contract.IPublishContract;
import com.example.communityfragment.presenter.PublishPresenter;
import com.example.communityfragment.utils.FileUtils;
import com.example.communityfragment.utils.URLUtils;
import com.example.module.libBase.utils.TokenManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PublishModel implements IPublishContract.Model {
    public static final String TAG = "PublishFunctionTAG";

    private PublishPresenter presenter;
    private Context context;
    Dispatcher dispatcher;
    private OkHttpClient client;

    public PublishModel(PublishPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
        dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(3);
        dispatcher.setMaxRequestsPerHost(3);
        client = new OkHttpClient().newBuilder()
                .dispatcher(dispatcher)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public void publish(String content, List<String> imagePaths, int communityId, IPublishContract.PublishCallback callback) {
        Log.d(TAG, "发布帖子: " + content + " " + imagePaths + " " + communityId);
        String token = TokenManager.getToken(context);

        uploadImages(imagePaths, token, new UploadCallback() {
            @Override
            public void onComplete(List<String> uploadedImageUrls) {
                JSONObject json = new JSONObject();
                try {
                    json.put("content", content);
                    json.put("image", new JSONArray(uploadedImageUrls).toString());
                    json.put("community_id", communityId);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                Log.d(TAG, "发布帖子请求体: " + json.toString());

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json.toString());
                Request request = new Request.Builder()
                        .url(URLUtils.PUBLISH_POST_URL)
                        .post(requestBody)
                        .addHeader("Authorization", "Bearer " + token)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "发帖失败: " + e.getMessage());
                        callback.onFailure();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseBody = response.body().string();
                        Log.d(TAG, "发帖: " + responseBody);
                        if (response.isSuccessful()) {
                            callback.onSuccess();
                        } else {
                            callback.onFailure();
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                callback.onFailure();
            }
        });
    }


    private void uploadImages(List<String> imagePaths, String token, UploadCallback callback) {
        if (imagePaths == null || imagePaths.isEmpty()) {
            callback.onComplete(new ArrayList<>());
            return;
        }

        List<String> uploadedImageUrls = new ArrayList<>(Collections.nCopies(imagePaths.size(), null));
        AtomicInteger uploadCount = new AtomicInteger(0);
        int totalImages = imagePaths.size();

        for (int i = 0; i < totalImages; i++) {
            String path = imagePaths.get(i);
            int index = i;
            uploadImageWithRetry(path, token, uploadedImageUrls, uploadCount, totalImages, callback, 0, index);
        }

    }

    private void uploadImageWithRetry(String path, String token, List<String> uploadedImageUrls,
                                      AtomicInteger uploadCount, int totalImages, UploadCallback callback, int retryCount, int index) {
        Log.d(TAG, "上传图片路径: " + path + "，重试次数: " + retryCount);

        File imageFile = FileUtils.ifHeicToJpg(new File(path));
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        String mimeType = getMimeType(imageFile);
        RequestBody fileBody = RequestBody.create(MediaType.parse(mimeType), imageFile);
        requestBodyBuilder.addFormDataPart("file", imageFile.getName(), fileBody);

        Request request = new Request.Builder()
                .url(URLUtils.UPLOAD_URL)
                .addHeader("Authorization", "Bearer " + token)
                .post(requestBodyBuilder.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (retryCount < 3) {
                    Log.d(TAG, "上传图片失败，重试第 " + (retryCount + 1) + " 次: " + e.getMessage());
                    uploadImageWithRetry(path, token, uploadedImageUrls, uploadCount, totalImages, callback, retryCount + 1, index);
                } else {
                    Log.e(TAG, "上传图片失败，已达到最大重试次数: " + e.getMessage());
                    callback.onError(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d(TAG, "图片上传返回: " + responseBody);
                    try {
                        JSONObject json = new JSONObject(responseBody);
                        String imgUrl = json.getString("data");
                        synchronized (uploadedImageUrls) {
                            uploadedImageUrls.set(index, imgUrl);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onError(e);
                        return;
                    }
                } else {
                    if (retryCount < 3) {
                        Log.d(TAG, "上传图片响应失败，重试第 " + (retryCount + 1) + " 次: 响应码 " + response.code());
                        uploadImageWithRetry(path, token, uploadedImageUrls, uploadCount, totalImages, callback, retryCount + 1, index);
                        return;
                    } else {
                        callback.onError(new Exception("图片上传失败，响应码：" + response.code()));
                        return;
                    }
                }

                if (uploadCount.incrementAndGet() == totalImages) {
                    callback.onComplete(uploadedImageUrls);
                }
            }
        });
    }

    private String getMimeType(File file) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(file.getAbsolutePath());
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        }
        return type != null ? type : "*/*";
    }

    interface UploadCallback {
        void onComplete(List<String> uploadedImageUrls);

        void onError(Exception e);
    }
}
