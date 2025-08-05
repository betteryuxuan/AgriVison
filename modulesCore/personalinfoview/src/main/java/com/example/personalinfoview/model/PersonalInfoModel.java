package com.example.personalinfoview.model;

import android.content.Context;
import android.util.Log;

import com.example.module.libBase.utils.SPUtils;
import com.example.module.libBase.utils.TokenManager;
import com.example.module.libBase.bean.User;
import com.example.personalinfoview.contract.IInfoContract;
import com.example.personalinfoview.presenter.PersonalInfoPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersonalInfoModel implements IInfoContract.Model {
    private static final String TAG = "PersonalInfoModelTAG";

    private PersonalInfoPresenter mPresenter;
    private Context mContext;

    private static final String USER_URL = "http://101.200.122.3:8080/user/info";
    private static final String AVATAR_URL = "http://101.200.122.3:8080/user/avatar";
    private OkHttpClient client = new OkHttpClient();

    public PersonalInfoModel(PersonalInfoPresenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    @Override
    public void getUserInfo() {
        String username = SPUtils.getString(mContext, SPUtils.USERNAME_KEY, null);
        String email = SPUtils.getString(mContext, SPUtils.EMAIL_KEY, null);
        String avatar = SPUtils.getString(mContext, SPUtils.AVATAR_KEY, null);

        // 帖子数要经常更新
//        int postNum = SPUtils.getInt(mContext, SPUtils.POSTNUM_KEY, 0);
//        if (username != null && email != null && avatar != null) {
//            // 本地已登录直接获
//            User user = new User();
//            user.setUserName(username);
//            user.setEmail(email);
//            user.setAvatar(avatar);
//            user.setPostnum(postNum);
//
//            mPresenter.getUser(user);
//            mPresenter.updateUserInfo(user);
//        } else
            if (username == null && email == null && avatar == null) {
            // 未登录
            mPresenter.getUser(null);
            mPresenter.updateUserInfo(null);
        } else {
            fetchUserName();
        }
    }

    @Override
    public String getUserAvatar() {
        return SPUtils.getString(mContext, SPUtils.AVATAR_KEY, null);
    }

    private void fetchUserName() {
        String token = TokenManager.getToken(mContext);
        Log.d(TAG, "token : " + token);

        Request.Builder builder = new Request.Builder();
        builder.url(USER_URL);
        if (token != null) {
            builder.addHeader("Authorization", "Bearer " + token);
        }
        Request request = builder.build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "fetchUserName onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                try {
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    if (jsonResponse.getInt("code") == 1) {
                        JSONObject data = jsonResponse.getJSONObject("data");

                        String username = data.getString("username");
                        String email = data.getString("email");
                        String avatar = data.getString("avatar");
                        int postNum = data.getInt("post_num");
                        if (avatar.equals("")) {
                            avatar = null;
                            Log.d(TAG, "avatar is null");
                        }
                        User user = new User();
                        user.setUserName(username);
                        user.setEmail(email);
                        user.setAvatar(avatar);
                        user.setPostnum(postNum);

                        SPUtils.putString(mContext, SPUtils.USERNAME_KEY, username);
                        SPUtils.putString(mContext, SPUtils.EMAIL_KEY, email);
                        SPUtils.putString(mContext, SPUtils.AVATAR_KEY, avatar);
//                        SPUtils.putInt(mContext, SPUtils.POSTNUM_KEY, postNum);

                        Log.d(TAG, responseBody);

                        mPresenter.getUser(user);
                        mPresenter.updateUserInfo(user);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}




