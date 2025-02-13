package com.example.module.login.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.module.login.IForgetContract;
import com.example.module.login.model.ForgetModel;
import com.example.module.login.view.ForgetActivity;

public class ForgetPresenter implements IForgetContract.Presenter {
    private IForgetContract.Model mModel;
    private IForgetContract.View mView;
    private Context mContext;

    public ForgetPresenter(ForgetActivity forgetActivity, Context context) {
        mView = forgetActivity;
        mContext = context;
        mModel = new ForgetModel(this, context);
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
        mModel.login(email, password, new IForgetContract.Model.Callback() {
            @Override
            public void onSuccess(String token) {
                SharedPreferences sp = mContext.getSharedPreferences("loggedInState", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("isLoggedIn", true);
                edit.putString("userToken", token);
                edit.apply();

                mView.showToast("登录成功");
                mView.startMainActivity();
            }

            @Override
            public void onFailure() {
                mView.showToast("账户未注册或密码错误");
            }
        });
    }

    @Override
    public void changePassword(String email, String password, String code) {
        mModel.changePassword(email, password, code, new IForgetContract.Model.Callback() {
            @Override
            public void onSuccess(String token) {
                login(email, password);
            }

            @Override
            public void onFailure() {
                mView.showToast("验证码不正确");
            }
        });
    }


}
