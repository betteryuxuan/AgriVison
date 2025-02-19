package com.example.module.login.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.libBase.TokenManager;
import com.example.module.login.ILoginContract;
import com.example.module.login.R;
import com.example.module.login.presenter.LoginPresenter;
import com.github.boybeak.skbglobal.SoftKeyboardGlobal;
import com.google.android.material.textfield.TextInputLayout;

import net.center.blurview.ShapeBlurView;


@Route(path = "/login/LoginActivity")
public class LoginActivity extends AppCompatActivity implements ILoginContract.View {
    private static final String TAG = "LoginActivityTAG";
    private ILoginContract.Presenter mPresenter;
    private Button btnLoginRegister;
    private Button btnSendCode;
    private View view;
    private TextView tvRegister;
    private TextView tvLoginNow;
    private TextView tvTop;
    private TextView tvTop1;
    private TextView tvTop2;
    private TextView tvBack;
    private TextView tvForgetName;
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
    private ShapeBlurView blurView;
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
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        QueryLoginStatus();

        btnLoginRegister = findViewById(R.id.btn_login_loginaccount);
        btnSendCode = findViewById(R.id.btn_send_code);
        tvRegister = findViewById(R.id.tv_register);
        tvLoginNow = findViewById(R.id.tv_login_now);
        tvTop = findViewById(R.id.tv_top);
        tvTop1 = findViewById(R.id.tv_login_1);
        tvTop2 = findViewById(R.id.tv_login_2);
        tvBack = findViewById(R.id.tv_login_back);
        tvForgetName = findViewById(R.id.tv_login_forgetname);
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
        blurView = findViewById(R.id.blur_login_view);

        mPresenter = new LoginPresenter(this, this);

        Typeface iconfont = Typeface.createFromAsset(getAssets(), "fonts/iconfont.ttf");
        tvBack.setTypeface(iconfont);

//        tilPassword.setPasswordVisibilityToggleEnabled(false);
//        tilTwicePassword.setPasswordVisibilityToggleEnabled(false);

        SoftKeyboardGlobal.INSTANCE.addSoftKeyboardCallback(new SoftKeyboardGlobal.SoftKeyboardCallback() {
            public void onOpen(int height) {
                Log.d(TAG, "onOpen: " + height);
                // 在键盘打开时，可以调整布局
                lastKeyboardHeight = height;
                adjustLayoutForKeyboard(true, height);
            }

            public void onClose() {
                Log.d(TAG, "onClose: ");
                // 在键盘关闭时恢复布局
                adjustLayoutForKeyboard(false, 0);
            }

            public void onHeightChanged(int height) {
                Log.d(TAG, "onHeightChanged: " + height);
                // 键盘高度变化时，动态调整布局
                adjustLayoutForKeyboard(true, height);
            }
        });

        tvLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        tvRegister.setOnClickListener(v -> {
            toRegister();
        });

        btnLoginRegister.setOnClickListener(v -> {
            String email = etUseremail.getText().toString();
            String password = etPassword.getText().toString();
            if (vaildEdit()) {
                if (!isRegistering) {
                    mPresenter.login(email, password);
                } else {
                    // 注册
                    String username = etUsername.getText().toString();
                    String verificationCode = etVerificationCode.getText().toString();
                    String twicePassword = etTwicePassword.getText().toString();
                    String code = etVerificationCode.getText().toString();
                    if (validateRegistrationFields()) {
                        mPresenter.register(email, password, username, code);
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

        tvForgetName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                        .build("/login/ForgetActivity")
                        .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                        .navigation(LoginActivity.this);
            }
        });
        setTextChangedListener();
    }

    private void QueryLoginStatus() {
        if (TokenManager.getLoginStatus(this)) {
            startMainActivity();
        }
    }


    private int lastKeyboardHeight = 0;
    private final Float svOffsetAmount = -500f;
    private ObjectAnimator currentAnimator;
    // 登录状态偏移比例
    private static final float LOGIN_OFFSET = 0.5f;
    //注册状态偏移比例
    private static final float REGISTERING_OFFSET = 0.4f;

    // 根据键盘是否打开和键盘高度调整布局位置
    private void adjustLayoutForKeyboard(boolean isKeyboardOpen, int keyboardHeight) {
        // 如果键盘打开，保存当前键盘高度
        if (isKeyboardOpen) {
            lastKeyboardHeight = keyboardHeight;
        }

        // 获取当前视图svLogin的垂直平移值
        final float currentTranslation = svLogin.getTranslationY();
        // 根据键盘状态计算目标平移值
        final float targetTranslation = calculateTargetTranslation(isKeyboardOpen, keyboardHeight);

        // 平移
        startTranslationAnimation(currentTranslation, targetTranslation);
    }

    // 根据键盘状态和高度计算视图的目标平移值
    private float calculateTargetTranslation(boolean isKeyboardOpen, int keyboardHeight) {
        // 登录状态
        if (!isRegistering) {
            // 键盘打开时，向上平移0.5键盘高度
            // 键盘关闭时，平移0
            return isKeyboardOpen ? -keyboardHeight * LOGIN_OFFSET : 0f;
        } else {
            // 键盘打开时，平移 sv已经向上的高度+0.4键盘高度
            // 键盘关闭时，平移值回到 sv已经向上的高度
            return isKeyboardOpen ? svOffsetAmount - (keyboardHeight * REGISTERING_OFFSET) : svOffsetAmount;
        }
    }

    // 平移动画
    private void startTranslationAnimation(float start, float end) {
        // 如果有动画正在运行，则取消当前动画，避免动画叠加冲突
        if (currentAnimator != null && currentAnimator.isRunning()) {
            currentAnimator.cancel();
        }

        currentAnimator = ObjectAnimator.ofFloat(svLogin, "translationY", start, end);
        currentAnimator.setDuration(300);
        // 动画插值器：开始和结束时较慢，中间加速
        currentAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        currentAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }
        });
        currentAnimator.start();
    }


    private void toRegister() {
        isRegistering = true;

        tvTop.setText("注册");
        btnLoginRegister.setText("注册");
        btnSendCode.setVisibility(View.VISIBLE);
        tilTwicePassword.setVisibility(View.VISIBLE);
        tilUsername.setVisibility(View.VISIBLE);
        tvBack.setVisibility(View.VISIBLE);

        tvForgetName.setVisibility(View.GONE);
        tvRegister.setVisibility(View.GONE);
        view.setVisibility(View.GONE);

        ObjectAnimator.ofFloat(tvTop, "translationX", 0f, 600f).setDuration(1000).start();
        ObjectAnimator.ofFloat(svLogin, "translationY", 0f, svOffsetAmount).setDuration(1000).start();
        ObjectAnimator.ofFloat(tvBack, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(btnLoginRegister, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(btnSendCode, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvRegister, "alpha", 1f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvLoginNow, "alpha", 1f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvTop1, "alpha", 1f, 0f).setDuration(1000).start();
        ObjectAnimator.ofFloat(tvTop2, "alpha", 1f, 0f).setDuration(1000).start();
        ObjectAnimator.ofFloat(tilUsername, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tilTwicePassword, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(blurView, "alpha", 1f, 0f).setDuration(1200).start();

//        svLogin.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            // 记录键盘高度值
//            private float lastKeyboardHeight;
//            private boolean isKeyboardVisible = false;
//
//            @Override
//            public void onGlobalLayout() {
//                Rect r = new Rect();
//                svLogin.getWindowVisibleDisplayFrame(r);
//                int screenHeight = svLogin.getRootView().getHeight();
//                int keypadHeight = screenHeight - r.bottom;
//
//                boolean keyboardNowVisible = keypadHeight > screenHeight * 0.15;
//
//                if (keyboardNowVisible && !isKeyboardVisible) {
//                    isKeyboardVisible = true;
//                    ObjectAnimator.ofFloat(svLogin, "translationY", svOffsetAmount, -r.bottom / 2f)
//                            .setDuration(300)
//                            .start();
//                    lastKeyboardHeight = -r.bottom / 2f;
//                } else if (!keyboardNowVisible && isKeyboardVisible) {
//                    isKeyboardVisible = false;
//                    ObjectAnimator.ofFloat(svLogin, "translationY", lastKeyboardHeight, svOffsetAmount)
//                            .setDuration(300)
//                            .start();
//                }
//            }
//        });
    }

    private void toLogin() {
        isRegistering = false;
        tvTop.setText("登录");
        btnLoginRegister.setText("登录");

        tvRegister.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        tvForgetName.setVisibility(View.VISIBLE);

        tvBack.setVisibility(View.GONE);
        tilTwicePassword.setVisibility(View.GONE);
        tilUsername.setVisibility(View.GONE);
        tilVerificationCode.setVisibility(View.GONE);
        btnSendCode.setVisibility(View.GONE);

        ObjectAnimator.ofFloat(tvTop, "translationX", 600f, 0f).setDuration(1000).start();
        ObjectAnimator.ofFloat(svLogin, "translationY", svOffsetAmount, 0f).setDuration(1000).start();
        ObjectAnimator.ofFloat(btnLoginRegister, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvLoginNow, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tvTop1, "alpha", 0f, 1f).setDuration(1000).start();
        ObjectAnimator.ofFloat(tvTop2, "alpha", 0f, 1f).setDuration(1000).start();
        ObjectAnimator.ofFloat(tvRegister, "alpha", 0f, 1f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tilUsername, "alpha", 1f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tilVerificationCode, "alpha", 1f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(tilTwicePassword, "alpha", 1f, 0f).setDuration(1200).start();
        ObjectAnimator.ofFloat(blurView, "alpha", 0f, 1f).setDuration(1200).start();
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
            etUseremail.setError("邮箱不能为空");
            return false;
        } else if (!isValidEmail(email)) {
            etUseremail.setError("邮箱格式不正确");
            return false;
        }
        etUseremail.setError(null);
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

        if (password.length() < 6) {
            tilPassword.setError("密码长度不能小于6");
            return false;
        }
        tilPassword.setErrorEnabled(false);
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
        if (twicePassword.length() < 6) {
            tilTwicePassword.setError("密码长度不能小于6");
            return false;
        }
        if (!twicePassword.equals(password)) {
            tilTwicePassword.setError("两次密码不一致");
            return false;
        }
        tilTwicePassword.setErrorEnabled(false);
        return true;
    }

    // 验证用户名
    private boolean validateUsername() {
        String username = etUsername.getText().toString();

        if (username.isEmpty()) {
            etUsername.setError("用户名不能为空");
            return false;
        }else if (username.length() > 10) {
            etUsername.setError("用户名长度不能大于10");
            return false;
        }
        etUsername.setError(null);
        return true;
    }

    // 验证验证码
    private boolean validateVerificationCode() {
        String verificationCode = etVerificationCode.getText().toString();

        if (verificationCode.isEmpty()) {
            etVerificationCode.setError("验证码不能为空");
            return false;
        }
        if (verificationCode.length() != 6) {
            etVerificationCode.setError("验证码长度不正确");
            return false;
        }
        etVerificationCode.setError(null);
        return true;
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

    @Override
    public void onBackPressed() {
        if (isRegistering) {
            toLogin();
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }

    public void startMainActivity() {
        ARouter.getInstance()
                .build("/main/MainActivity")
                .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                .navigation();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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