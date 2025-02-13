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

        void login(String email, String password, LoginCallback callback);

        void register(String email, String password, String username, String code, RegisterCallback callback);

        void saveLoginState(String email, String token);

        public interface LoginCallback {
            void onSuccess(String token);
            void onFailure();
        }
        public interface RegisterCallback {
            void onSuccess();
            void onFailure();
        }

    }

}
