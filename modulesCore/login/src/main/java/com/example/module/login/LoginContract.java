package com.example.module.login;

import com.example.module.login.room.User;

public interface LoginContract {
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

        void register(String email, String password, String username);

    }

    interface Model {
        int sendVerificationCode(String destinationEmail, String verificationCode);

        void login(String email, String password, Callback callback);

        void register(String email, String password, String username, Callback callback);

        interface Callback {
            void onSuccess(User user);

            void onFailure();
        }
    }

}
