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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyInfoModel implements IMyInfoContract.Model {

    private MyInfoPresenter mPresenter;
    private Context mContext;

    private static final String USER_URL = "http://101.200.122.3:8080/user";
    private OkHttpClient client = new OkHttpClient();

    public MyInfoModel(MyInfoPresenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    @Override
    public User getMyInfo() {
        return new User();
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

}




