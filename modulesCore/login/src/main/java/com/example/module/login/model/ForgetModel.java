package com.example.module.login.model;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.module.login.IForgetContract;
import com.example.module.login.ILoginContract;

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

public class ForgetModel implements IForgetContract.Model {
    private static final String TAG = "ForgetModelTAG";

    private IForgetContract.Presenter mPresenter;
    private Context mContext;

    private static final String LOGIN_URL = "http://101.200.122.3:8080/login";
    private static final String EMAIL_URL = "http://101.200.122.3:8080/email";
    private static final String CHANGEPASSWORD_URL = "http://101.200.122.3:8080/changePassword";

    private static final OkHttpClient client = new OkHttpClient();

    public ForgetModel(IForgetContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    public void sendVerificationCode(String destinationEmail) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", destinationEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody
                .create(MediaType.parse("application/json; charset=utf-8"), json.toString());

        Request request = new Request.Builder()
                .url(EMAIL_URL)
                .post(requestBody)
                .build();
        Log.d(TAG, "发送验证码1" + destinationEmail);
        Log.d(TAG, "发送验证码2" + json.toString());

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mPresenter.onVerificationCodeSentFailure();
                Log.d(TAG, "发送验证码失败" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mPresenter.onVerificationCodeSentSuccess();
                try {
                    Log.d(TAG, "发送验证码" + response.body().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    @Override
    public void changePassword(String email, String password, String code, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", password);
            json.put("code", code);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        RequestBody requestBody = RequestBody
                .create(MediaType.parse(("application/json; charset=utf-8")), json.toString());

        Request request = new Request.Builder()
                .url(CHANGEPASSWORD_URL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onSuccess("");
            }
        });

    }

    @Override
    public void login(String email, String password, Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody
                .create(MediaType.parse("application/json; charset=utf-8"), json.toString());

        Request request = new Request.Builder()
                .url(LOGIN_URL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        if (jsonResponse.getInt("code") == 1) {
                            String token = jsonResponse.getString("data");
                            saveLoginState(email, password, token);
                            callback.onSuccess(token);
                        } else {
                            callback.onFailure();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure();
                    }
                } else {
                    callback.onFailure();
                }
            }
        });
    }


    @Override
    public void saveLoginState(String email, String password, String token) {
        SharedPreferences sp = mContext.getSharedPreferences("loggedInState", MODE_PRIVATE);
        sp.edit()
                .putBoolean("isLoggedIn", true)
                .putString("userToken", token)
                .putString("userEmail", email)
                .putString("userPassword", password)
                .apply();
    }


}
