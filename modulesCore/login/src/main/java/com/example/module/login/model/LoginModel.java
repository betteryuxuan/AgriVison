package com.example.module.login.model;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.module.libBase.SPUtils;
import com.example.module.libBase.TokenManager;
import com.example.module.login.ILoginContract;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginModel implements ILoginContract.Model {
    private final String TAG = "LoginModelTAG";

    private ILoginContract.Presenter mPresenter;
    private Context mContext;

    private static final String LOGIN_URL = "http://101.200.122.3:8080/login";
    private static final String SIGNUP_URL = "http://101.200.122.3:8080/signup";
    private static final String EMAIL_URL = "http://101.200.122.3:8080/email";

    private static final OkHttpClient client = new OkHttpClient();

    public LoginModel(ILoginContract.Presenter presenter, Context context) {
        mPresenter = presenter;
        mContext = context;
    }

    public void sendVerificationCode(final String destinationEmail) {
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

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mPresenter.onVerificationCodeSentFailure();
                Log.d(TAG, "发送验证码失败" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mPresenter.onVerificationCodeSentSuccess();

            }
        });

    }

    @Override
    public void login(String email, String password, LoginCallback callback) {
        SPUtils.clear(mContext);

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
                Log.d(TAG, "登录失败" + e.getMessage());
                callback.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    Log.d(TAG, "登录成功" + responseBody);
                    try {
                        JSONObject jsonResponse = new JSONObject(responseBody);
                        if (jsonResponse.getInt("code") == 1) {
                            String token = jsonResponse.getString("data");
                            saveLoginState(email, token);
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
    public void register(String email, String password, String username, String code, RegisterCallback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", password);
            json.put("code", code);
            json.put("username", username);

        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody
                .create(MediaType.parse("application/json; charset=utf-8"), json.toString());

        Request request = new Request.Builder()
                .url(SIGNUP_URL)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                try {
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    if (jsonResponse.getInt("code") == 0) {
                        callback.onFailure();
                    } else {
                        SPUtils.putString(mContext, SPUtils.EMAIL_KEY, email);
                        callback.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onFailure();
                }
            }
        });

    }

    @Override
    public void saveLoginState(String email, String token) {
        SPUtils.putString(mContext, SPUtils.EMAIL_KEY, email);
        TokenManager.saveToken(mContext, token);
    }


}
