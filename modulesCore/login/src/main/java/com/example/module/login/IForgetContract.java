package com.example.module.login;

import com.example.module.login.room.User;

public interface IForgetContract {
    interface View {
        void showToast(String msg);
        void startMainActivity(User user);

    }
    interface Presenter {
        void sendVerificationCode(String destinationEmail);
        void onVerificationCodeSentSuccess();
        void onVerificationCodeSentFailure();
        boolean validateVerificationCode(String verificationCode);
        void login(String email, String password);
    }
    interface Model {
        void sendVerificationCode(String destinationEmail);
        void login(String email, String password);
    }
}
