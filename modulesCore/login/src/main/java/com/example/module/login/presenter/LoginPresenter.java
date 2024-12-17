package com.example.module.login.presenter;

import android.content.Context;
import android.util.Log;

import com.example.module.login.LoginContract;
import com.example.module.login.model.LoginModel;
import com.example.module.login.room.User;
import com.example.module.login.view.LoginActivity;

import java.util.Random;

public class LoginPresenter implements LoginContract.Presenter {
    private static final String TAG = "LoginPresenterTAG";

    private LoginContract.Model mModel;
    private LoginContract.View mView;
    private Context mContext;
    private int curVerificationCode;

    public LoginPresenter(LoginActivity loginActivity, Context context) {
        mView = loginActivity;
        mContext = context;
        mModel = new LoginModel(this, context);
    }

    @Override
    public void sendVerificationCode(String destinationEmail) {
        Random random = new Random();
        curVerificationCode = 100000 + random.nextInt(900000);
        if (mModel.sendVerificationCode(destinationEmail, String.valueOf(curVerificationCode)) == 0) {
            mView.showToast("一分钟内只能发送一次");
        }
    }

    @Override
    public void onVerificationCodeSentSuccess() {
        mView.showToast("验证码已发送，请注意查收");
    }

    @Override
    public void onVerificationCodeSentFailure() {
        mView.showToast("验证码发送失败");
    }


    @Override
    public boolean validateVerificationCode(String verificationCode) {
        return Integer.parseInt(verificationCode) == curVerificationCode;
    }

    @Override
    public void login(String email, String password) {
        mModel.login(email, password, new LoginContract.Model.Callback() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "登录成功");
                mView.showToast("登录成功");
                mView.startMainActivity(user);
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "登录失败");
                mView.showToast("账户未注册或密码错误");
            }
        });
    }

    @Override
    public void register(String email, String password, String username) {
        mModel.register(email, password, username, new LoginContract.Model.Callback() {
            @Override
            public void onSuccess(User user) {
                Log.d(TAG, "注册成功");
                mView.showToast("注册成功");
                mView.startMainActivity(user);
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "注册失败");
                mView.showToast("该邮箱已注册");
            }
        });
    }
}
