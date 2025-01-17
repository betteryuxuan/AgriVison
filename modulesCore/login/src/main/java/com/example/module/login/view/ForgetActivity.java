package com.example.module.login.view;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.login.R;
import com.example.module.login.room.User;
import com.google.android.material.textfield.TextInputLayout;

@Route(path = "/login/ForgetActivity")
public class ForgetActivity extends AppCompatActivity {

    private TextInputLayout tilForgetEmail;
    private EditText etForgetEmail;
    private TextInputLayout tilCode;
    private EditText etCode;
    private Button btnForgetLoginaccount;
    private Button btnSendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tilForgetEmail.findViewById(R.id.til_forget_email);
        etForgetEmail.findViewById(R.id.et_forget_email);
        btnForgetLoginaccount.findViewById(R.id.btn_forget_loginaccount);
        btnSendCode.findViewById(R.id.btn_forget_sendcode);
        tilCode.findViewById(R.id.til_forget_verification_code);
        etCode.findViewById(R.id.et_forget_verification_code);

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vaildMail()) {
                    tilCode.setVisibility(View.VISIBLE);
                    ObjectAnimator.ofFloat(tilCode, "alpha", 0f, 1f).setDuration(1200).start();
                    btnSendCode.setVisibility(View.GONE);

                    // 发送验证码
//                    mPresenter.sendVerificationCode(etForgetEmail.getText().toString());
                }
            }
        });

        btnForgetLoginaccount.setOnClickListener(v -> {
            String email = etForgetEmail.getText().toString();
            String code = etCode.getText().toString();
            if (vaildEdit()) {
                // 从数据库验证邮箱是否已注册，回调
//                mPresenter.validateVerificationCode(verificationCode);

            }
        });
    }


    private boolean vaildEdit() {
        if (!vaildMail())
            return false;
        if (!validateCode())
            return false;
        return true;
    }

    private boolean vaildMail() {
        String email = etForgetEmail.getText().toString();
        if (email.isEmpty()) {
            tilForgetEmail.setError("邮箱不能为空");
            return false;
        } else if (!isValidEmail(email)) {
            tilForgetEmail.setError("邮箱格式不正确");
            return false;
        } else {
            tilForgetEmail.setError(null);
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return email.matches(emailPattern);
    }

    private boolean validateCode() {
        String verificationCode = etForgetEmail.getText().toString();

        if (verificationCode.isEmpty()) {
            tilForgetEmail.setError("验证码不能为空");
            return false;
        }
        tilForgetEmail.setError(null);

        if (verificationCode.length() != 6) {
            tilForgetEmail.setError("验证码长度不正确");
            return false;
        }
//    if (!mPresenter.validateVerificationCode(verificationCode)) {
//        tilForgetEmail.setError("验证码不正确");
//        return false;
//    }
        tilForgetEmail.setError(null);
        return true;
    }

    public void startMainActivity(User user) {
        ARouter.getInstance()
                .build("/main/MainActivity")
                .withSerializable("user", user)
                .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                .navigation();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }
}