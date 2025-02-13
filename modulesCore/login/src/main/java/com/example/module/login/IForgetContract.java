package com.example.module.login;

public interface IForgetContract {
    interface View {
        void showToast(String msg);

        void startMainActivity();

    }

    interface Presenter {
        void sendVerificationCode(String destinationEmail);

        void onVerificationCodeSentSuccess();

        void onVerificationCodeSentFailure();

        void login(String email, String password);

        void changePassword(String email, String password, String code);
    }

    interface Model {
        void sendVerificationCode(String destinationEmail);

        void login(String email, String password, IForgetContract.Model.Callback callback);

        void changePassword(String email, String password, String code, IForgetContract.Model.Callback callback);

        void saveLoginState(String email, String token);

        interface Callback {
            void onSuccess(String token);

            void onFailure();
        }
    }
}
