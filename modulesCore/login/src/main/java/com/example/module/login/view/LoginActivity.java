package com.example.module.login.view;

import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.login.LoginContract;
import com.example.module.login.R;
import com.example.module.login.presenter.LoginPresenter;
import com.example.module.login.room.User;
import com.google.android.material.textfield.TextInputLayout;


@Route(path = "/login/LoginActivity")
public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private static final String TAG = "LoginActivityTAG";
    private ScrollView sv;
    private LoginContract.Presenter mPresenter;
    private ConstraintLayout cl_main;
    private Button btnLoginRegister;
    private Button btnSendCode;
    private View view;
    private TextView tvRegister;
    private TextView tvLoginNow;
    private TextView tvTop;
    private TextView tvtop1;
    private TextView tvtop2;
    private TextView tvBack;
    private TextInputLayout tilUseremail;
    private TextInputLayout tilPassword;
    private TextInputLayout tilTwicePassword;
    private TextInputLayout tilUsername;
    private TextInputLayout tilVerificationCode;
    private EditText etUseremail;
    private EditText etPassword;
    private EditText etTwicePassword;
    private EditText etUsername;
    private EditText etVerificationCode;


    private ScrollView svLogin;
    private boolean isRegistering = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sv = findViewById(R.id.sv_login);
        cl_main = findViewById(R.id.main);
        btnLoginRegister = findViewById(R.id.btn_login_loginaccount);
        btnSendCode = findViewById(R.id.btn_send_code);
        tvRegister = findViewById(R.id.tv_register);
        tvLoginNow = findViewById(R.id.tv_login_now);
        tvTop = findViewById(R.id.tv_top);
        tvtop1 = findViewById(R.id.tv_login_1);
        tvtop2 = findViewById(R.id.tv_login_2);
        tvBack = findViewById(R.id.tv_login_back);
        tilUseremail = findViewById(R.id.til_login_email);
        tilPassword = findViewById(R.id.til_login_password);
        tilUsername = findViewById(R.id.til_register_username);
        tilVerificationCode = findViewById(R.id.til_register_verification_code);
        tilTwicePassword = findViewById(R.id.til_register_password);
        svLogin = findViewById(R.id.sv_login);
        view = findViewById(R.id.view_login);
        etUseremail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);
        etTwicePassword = findViewById(R.id.et_register_password);
        etUsername = findViewById(R.id.et_register_username);
        etVerificationCode = findViewById(R.id.et_register_verification_code);

        mPresenter = new LoginPresenter(this, this);

        Typeface iconfont = Typeface.createFromAsset(getAssets(), "fonts/iconfont.ttf");
        tvBack.setTypeface(iconfont);


        tvLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity(null);
            }
        });

        tvRegister.setOnClickListener(v -> {
            toRegister();
        });

        btnLoginRegister.setOnClickListener(v -> {
            String email = etUseremail.getText().toString();
            String password = etPassword.getText().toString();
            if (vaildEdit()) {
                // 从数据库验证邮箱是否已注册
                if (!isRegistering) {
                    // 登录
                    mPresenter.login(email, password);
                } else {
                    // 注册
                    String username = etUsername.getText().toString();
                    String verificationCode = etVerificationCode.getText().toString();
                    String twicePassword = etTwicePassword.getText().toString();
                    mPresenter.validateVerificationCode(verificationCode);
                    if (validateRegistrationFields()) {
                        mPresenter.register(email, password, username);
                        showToast("注册成功");
                    }

                    Log.d("LoginActivityTAG", "注册：email: " + email + " password: " + password + " username: " + username + " verificationCode: " + verificationCode + " twicePassword: " + twicePassword);
                }
            }
        });

        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vaildMail()) {
                    tilVerificationCode.setVisibility(View.VISIBLE);
                    ObjectAnimator.ofFloat(tilVerificationCode, "alpha", 0f, 1f).setDuration(1200).start();
                    btnSendCode.setVisibility(View.GONE);

                    mPresenter.sendVerificationCode(etUseremail.getText().toString());
                }
            }
        });


        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLogin();
            }
        });

        setTextChangedListener();

    }

    private void toRegister() {
        isRegistering = true;

        tvTop.setText("注册");
        btnLoginRegister.setText("注册");
        btnSendCode.setVisibility(View.VISIBLE);
        tilTwicePassword.setVisibility(View.VISIBLE);
        tilUsername.setVisibility(View.VISIBLE);
        tvBack.setVisibility(View.VISIBLE);

        tvRegister.setVisibility(View.INVISIBLE);
        view.setVisibility(View.INVISIBLE);
        ObjectAnimator.ofFloat(tvTop, "translationX", 0f, 600f).setDuration(1200).start();
        ObjectAnimator.ofFloat(svLogin, "translationY", 0f, -600f).setDuration(800).start();
        ObjectAnimator.ofFloat(tvBack, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(btnLoginRegister, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(btnSendCode, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvRegister, "alpha", 1f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvLoginNow, "alpha", 1f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvtop1, "alpha", 1f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvtop2, "alpha", 1f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tilUsername, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tilTwicePassword, "alpha", 0f, 1f).setDuration(1200).start();
    }

    private void toLogin() {
        isRegistering = false;
        tvTop.setText("登录");
        btnLoginRegister.setText("登录");
        tvRegister.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(tvTop, "translationX", 600f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(svLogin, "translationY", -600f, 0f).setDuration(800).start();
        ObjectAnimator.ofFloat(btnLoginRegister, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvLoginNow, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvtop1, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvtop2, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvRegister, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tilUsername, "alpha", 1f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tilVerificationCode, "alpha", 1f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tilTwicePassword, "alpha", 1f, 0f).setDuration(1200).start();

        tvBack.setVisibility(View.GONE);
        tilTwicePassword.setVisibility(View.GONE);
        tilUsername.setVisibility(View.GONE);
        tilVerificationCode.setVisibility(View.GONE);
        btnSendCode.setVisibility(View.GONE);


    }

    public void startMainActivity(User user) {
        ARouter.getInstance()
                .build("/main/MainActivity")
                .withSerializable("user", user)
                .navigation();
    }


    private void setTextChangedListener() {
        etUseremail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vaildMail();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etTwicePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateTwicePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateUsername();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etVerificationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateVerificationCode();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }




    private boolean vaildEdit() {
        if (!vaildMail())
            return false;
        if (!validatePassword())
            return false;
        if (isRegistering && !validateRegistrationFields())
            return false;
        return true;
    }

    private boolean vaildMail() {
        String email = etUseremail.getText().toString();
        if (email.isEmpty()) {
            tilUseremail.setError("邮箱不能为空");
            return false;
        } else if (!isValidEmail(email)) {
            tilUseremail.setError("邮箱格式不正确");
            return false;
        } else {
            tilUseremail.setError(null);
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return email.matches(emailPattern);
    }

    // 验证密码
    private boolean validatePassword() {
        String password = etPassword.getText().toString();
        if (password.isEmpty()) {
            tilPassword.setError("密码不能为空");
            return false;
        }
        tilPassword.setError(null);

        if (password.length() < 6) {
            tilPassword.setError("密码长度不能小于6");
            return false;
        }
        tilPassword.setError(null);
        return true;
    }

    // 验证注册
    private boolean validateRegistrationFields() {
        if (!validateTwicePassword())
            return false;
        if (!validateUsername())
            return false;
        if (!validateVerificationCode())
            return false;
        return true;
    }

    // 验证二次密码
    private boolean validateTwicePassword() {
        String password = etPassword.getText().toString();
        String twicePassword = etTwicePassword.getText().toString();

        if (twicePassword.isEmpty()) {
            tilTwicePassword.setError("密码不能为空");
            return false;
        }
        tilTwicePassword.setError(null);

        if (twicePassword.length() < 6) {
            tilTwicePassword.setError("密码长度不能小于6");
            return false;
        }
        tilTwicePassword.setError(null);

        if (!twicePassword.equals(password)) {
            tilTwicePassword.setError("两次密码不一致");
            return false;
        }
        tilTwicePassword.setError(null);
        return true;
    }

    // 验证用户名
    private boolean validateUsername() {
        String username = etUsername.getText().toString();

        if (username.isEmpty()) {
            tilUsername.setError("用户名不能为空");
            return false;
        }
        tilUsername.setError(null);
        return true;
    }

    // 验证验证码
    private boolean validateVerificationCode() {
        String verificationCode = etVerificationCode.getText().toString();

        if (verificationCode.isEmpty()) {
            tilVerificationCode.setError("验证码不能为空");
            return false;
        }
        tilVerificationCode.setError(null);

        if (verificationCode.length() != 6) {
            tilVerificationCode.setError("验证码长度不正确");
            return false;
        }
        if (!mPresenter.validateVerificationCode(verificationCode)) {
            tilVerificationCode.setError("验证码不正确");
            return false;
        }
        tilVerificationCode.setError(null);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (isRegistering) {
            toLogin();
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }


    @Override
    public void showToast(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}