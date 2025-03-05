package com.example.module.login.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.libBase.bean.User;
import com.example.module.login.IForgetContract;
import com.example.module.login.R;
import com.example.module.login.databinding.ActivityForgetBinding;
import com.example.module.login.presenter.ForgetPresenter;
import com.github.boybeak.skbglobal.SoftKeyboardGlobal;

@Route(path = "/login/ForgetActivity")
public class ForgetActivity extends AppCompatActivity implements IForgetContract.View {
    private ActivityForgetBinding binding;
    private ForgetPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityForgetBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mPresenter = new ForgetPresenter(this, this);


        String lastEmail = getIntent().getStringExtra("email");
        if (lastEmail != null) {
            binding.etForgetEmail.setText(lastEmail);
        }

        binding.btnForgetSendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vaildMail()) {
                    binding.tilForgetCode.setVisibility(View.VISIBLE);
                    ObjectAnimator.ofFloat(binding.tilForgetCode, "alpha", 0f, 1f).setDuration(1200).start();
                    binding.btnForgetSendcode.setVisibility(View.GONE);

                    mPresenter.sendVerificationCode(binding.etForgetEmail.getText().toString());
                }
            }
        });

        binding.btnForgetLoginaccount.setOnClickListener(v -> {
            String email = binding.etForgetEmail.getText().toString();
            String code = binding.etForgetCode.getText().toString();
            String password = binding.etForgetPassword.getText().toString();
            if (vaildEdit()) {
                mPresenter.changePassword(email, password, code);
            }
        });

        SoftKeyboardGlobal.INSTANCE.addSoftKeyboardCallback(new SoftKeyboardGlobal.SoftKeyboardCallback() {
            public void onOpen(int height) {
                // 在键盘打开时，可以调整布局
                lastKeyboardHeight = height;
                adjustLayoutForKeyboard(true, height);
            }

            public void onClose() {
                // 在键盘关闭时恢复布局
                adjustLayoutForKeyboard(false, 0);
            }

            public void onHeightChanged(int height) {
                // 键盘高度变化时，动态调整布局
                adjustLayoutForKeyboard(true, height);
            }
        });

        setTextChangedListener();
    }


    private boolean vaildEdit() {
        if (!vaildMail() || !validateCode()
                || !validateTwicePassword()
                || !validateTwicePassword())
            return false;
        return true;
    }

    private boolean vaildMail() {
        String email = binding.etForgetEmail.getText().toString();
        if (email.isEmpty()) {
            binding.etForgetEmail.setError("邮箱不能为空");
            return false;
        } else if (!isValidEmail(email)) {
            binding.etForgetEmail.setError("邮箱格式不正确");
            return false;
        } else {
            binding.etForgetEmail.setError(null);
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return email.matches(emailPattern);
    }

    // 验证密码
    private boolean validatePassword() {
        String password = binding.etForgetPassword.getText().toString();
        if (password.isEmpty()) {
            binding.tilForgetPassword.setError("密码不能为空");
            return false;
        } else if (password.length() < 6) {
            binding.tilForgetPassword.setError("密码长度不能小于6");
            return false;
        } else if (password.length() > 20) {
            binding.tilForgetPassword.setError("密码长度不能大于20");
            return false;
        }
        binding.tilForgetPassword.setErrorEnabled(false);
        return true;
    }

    // 验证二次密码
    private boolean validateTwicePassword() {
        String password = binding.etForgetPassword.getText().toString();
        String twicePassword = binding.etForgetPasswordtwo.getText().toString();

        if (twicePassword.isEmpty()) {
            binding.tilForgetPasswordtwo.setError("密码不能为空");
            return false;
        } else if (twicePassword.length() < 6) {
            binding.tilForgetPasswordtwo.setError("密码长度不能小于6");
            return false;
        } else if (password.length() > 20) {
            binding.tilForgetPassword.setError("密码长度不能大于20");
            return false;
        } else if (!twicePassword.equals(password)) {
            binding.tilForgetPasswordtwo.setError("两次密码不一致");
            return false;
        }
        binding.tilForgetPasswordtwo.setErrorEnabled(false);
        return true;
    }

    private boolean validateCode() {
        String verificationCode = binding.etForgetCode.getText().toString();

        if (verificationCode.isEmpty()) {
            binding.etForgetCode.setError("验证码不能为空");
            return false;
        }
        binding.etForgetCode.setError(null);

        if (verificationCode.length() != 6) {
            binding.etForgetCode.setError("验证码长度不正确");
            return false;
        }

        binding.etForgetCode.setError(null);
        return true;
    }


    private int lastKeyboardHeight = 0;
    private ObjectAnimator currentAnimator;
    private static final float CHANGEPASSWORD_OFFSET = 0.4f;

    // 根据键盘是否打开和键盘高度调整布局位置
    private void adjustLayoutForKeyboard(boolean isKeyboardOpen, int keyboardHeight) {
        // 如果键盘打开，保存当前键盘高度
        if (isKeyboardOpen) {
            lastKeyboardHeight = keyboardHeight;
        }

        // 获取当前视图svLogin的垂直平移值
        final float currentTranslation = binding.svForget.getTranslationY();
        // 根据键盘状态计算目标平移值
        final float targetTranslation = calculateTargetTranslation(isKeyboardOpen, keyboardHeight);

        // 平移
        startTranslationAnimation(currentTranslation, targetTranslation);
    }

    // 根据键盘状态和高度计算视图的目标平移值
    private float calculateTargetTranslation(boolean isKeyboardOpen, int keyboardHeight) {
        return isKeyboardOpen ? -keyboardHeight * CHANGEPASSWORD_OFFSET : 0f;
    }

    // 平移动画
    private void startTranslationAnimation(float start, float end) {
        if (currentAnimator != null && currentAnimator.isRunning()) {
            currentAnimator.cancel();
        }

        currentAnimator = ObjectAnimator.ofFloat(binding.svForget, "translationY", start, end);
        currentAnimator.setDuration(300);
        currentAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        currentAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }
        });
        currentAnimator.start();
    }

    private void setTextChangedListener() {
        binding.etForgetEmail.addTextChangedListener(new TextWatcher() {
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
        binding.etForgetPassword.addTextChangedListener(new TextWatcher() {
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
        binding.etForgetPasswordtwo.addTextChangedListener(new TextWatcher() {
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
        binding.etForgetCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateCode();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void startMainActivity(User user) {
        ARouter.getInstance()
                .build("/main/MainActivity")
                .withSerializable("user", user)
                .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                .navigation();
        finish();
    }

    @Override
    public void showToast(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ForgetActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startMainActivity() {
        ARouter.getInstance()
                .build("/main/MainActivity")
                .withTransition(R.anim.slide_in_left, R.anim.slide_out_left)
                .navigation();
        finishAffinity();
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}