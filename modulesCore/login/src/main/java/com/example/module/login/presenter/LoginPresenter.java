package com.example.module.login.presenter;

import android.content.Context;
import android.util.Log;

import com.example.module.login.ILoginContract;
import com.example.module.login.model.LoginModel;
import com.example.module.login.view.LoginActivity;

public class LoginPresenter implements ILoginContract.Presenter {
    private static final String TAG = "LoginPresenterTAG";

    private ILoginContract.Model mModel;
    private ILoginContract.View mView;
    private Context mContext;

    public LoginPresenter(LoginActivity loginActivity, Context context) {
        mView = loginActivity;
        mContext = context;
        mModel = new LoginModel(this, context);
    }

    @Override
    public void sendVerificationCode(String destinationEmail) {
        mModel.sendVerificationCode(destinationEmail);
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
    public void login(String email, String password) {
        mModel.login(email, password, new ILoginContract.Model.LoginCallback() {
            @Override
            public void onSuccess(String token) {
                Log.d(TAG, "登录成功:" + token);
                mView.showToast("登录成功");
                mView.startMainActivity();
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "登录失败");
                mView.showToast("账户未注册或密码错误");
            }
        });
    }


    @Override
    public void register(String email, String password, String username, String verificationCode) {
        mModel.register(email, password, username, verificationCode,
                new ILoginContract.Model.RegisterCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "注册成功");
//                        mView.showToast("注册成功");
                        login(email, password);
                    }

                    @Override
                    public void onFailure() {
                        Log.d(TAG, "注册失败");
                        mView.showToast("此邮箱已注册");
                    }
                });
    }
}
