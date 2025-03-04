package com.example.personalinfoview.model;

import android.content.Context;
import android.util.Log;

import com.example.module.libBase.SPUtils;
import com.example.module.libBase.TokenManager;
import com.example.module.libBase.bean.User;
import com.example.personalinfoview.contract.IMyInfoContract;
import com.example.personalinfoview.presenter.MyInfoPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyInfoModel implements IMyInfoContract.Model {
    private static final String TAG = "MyInfoModelTAG";

    private MyInfoPresenter mPresenter;
    private Context mContext;

    private static final String USER_URL = "http://101.200.122.3:8080/user/info";
    private static final String AVATAR_URL = "http://101.200.122.3:8080/user/avatar";
    private OkHttpClient client = new OkHttpClient();

    public MyInfoModel(MyInfoPresenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }


    @Override
    public void modifyInfo(String username, String email) {
        String token = TokenManager.getToken(mContext);

        JSONObject object = new JSONObject();
        try {
            object.put("username", username);
            object.put("email", email);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
        Request.Builder builder = new Request.Builder();
        builder.url(USER_URL).put(body);
        if (token != null) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MyInfoActivityTAG", "onFailure: " + e.getMessage());
                mPresenter.onModifyInfoFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("MyInfoActivityTAG", "onResponse: " + response.body().string());
                mPresenter.onModifyInfoSuccess(username);
                SPUtils.putString(mContext, SPUtils.USERNAME_KEY, username);
            }
        });
    }

    @Override
    public void saveUserAvatar(String avatarUri) {
        SPUtils.putString(mContext, SPUtils.AVATAR_KEY, avatarUri);
    }

    @Override
    public String getUserAvatar() {
        return SPUtils.getString(mContext, SPUtils.AVATAR_KEY, null);
    }

    @Override
    public void modifyUserAvatar(String avatarPath) {
        Log.d(TAG, "modifyUserAvatar: " + avatarPath);
        String token = TokenManager.getToken(mContext);
        // 通过表单上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        File imageFile;
        if (avatarPath != null) {
            imageFile = new File(avatarPath);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
            requestBody.addFormDataPart("file", imageFile.getName(), fileBody);
        } else {
            requestBody.addFormDataPart("file", "");
        }

        Request request = new Request.Builder()
                .url(AVATAR_URL)
                .addHeader("Authorization", "Bearer " + token)
                .post(requestBody.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        String code = json.getString("code");
                        String msg = json.getString("msg");
                        if (code.equals("0")) {
                            Log.d(TAG, "上传失败" + msg);
                        } else {
                            Log.d(TAG, "上传成功: " + msg);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                } else {
                    Log.e(TAG, "上传异常: " + response.code());
                }
            }
        });
    }
}




