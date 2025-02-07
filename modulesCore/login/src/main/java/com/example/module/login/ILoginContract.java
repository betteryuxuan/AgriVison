package com.example.module.login;

public interface ILoginContract {
    interface View {
        void showToast(String msg);

        void startMainActivity();
    }

    interface Presenter {
        void sendVerificationCode(String destinationEmail);

        void onVerificationCodeSentSuccess();

        void onVerificationCodeSentFailure();

        void login(String email, String password);

        void register(String email, String password, String username, String code);

    }

    interface Model {
        void sendVerificationCode(String destinationEmail);

        void login(String email, String password, Callback callback);

        void register(String email, String password, String username, String code, Callback callback);

        void saveLoginState(String email, String password, String token);

        interface Callback {
            void onSuccess(String token);

            void onFailure();
        }
    }

}
