package com.example.agrivison;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.Random;

@Route(path = "/app/LoadActivity")
public class LoadActivity extends AppCompatActivity {

    private ConstraintLayout cl_main;
    private ImageView iv_main;
    private ObjectAnimator animator;
    private long lastClickTime = 0;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300;

    // 用于标识是否已经跳转，避免重复跳转
    private boolean isNavigated = false;
    private Handler handler = new Handler();
    private Runnable delayedRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isNavigated) {
                isNavigated = true;
                ARouter.getInstance().build("/login/LoginActivity")
                        .navigation(getApplicationContext());
                finish();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_load);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.loading_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cl_main = findViewById(R.id.cl_main);
        iv_main = findViewById(R.id.iv_main);

        int[] backgroundImages = new int[]{
                R.drawable.load_bg_1,
                R.drawable.load_bg_2,
        };
        Random random = new Random();

        int randomImageId = backgroundImages[random.nextInt(backgroundImages.length)];

        cl_main.setBackgroundResource(randomImageId);


        // 创建平移动画
        ObjectAnimator translateY = ObjectAnimator.ofFloat(cl_main, "translationX", 800f, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translateY);
        animatorSet.setDuration(2000);
        animatorSet.start();

        // 设置初始透明度为 0
        iv_main.setAlpha(0f);
        // 创建透明度动画
        animator = ObjectAnimator.ofFloat(iv_main, "alpha", 0f, 1f);
        animator.setDuration(2000);
        animator.start();

        // 延时 2300 毫秒跳转到登录界面
        handler.postDelayed(delayedRunnable, 2300);

//        cl_main.setOnClickListener(v -> {
//            long currentTime = System.currentTimeMillis();
//            if (currentTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
//                // 触发双击事件
//                onDoubleClick();
//            }
//            lastClickTime = currentTime;
//        });
    }

    private void onDoubleClick() {
        // 取消延时任务，避免重复跳转
        handler.removeCallbacks(delayedRunnable);
        // 取消动画
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        // 确保只执行一次跳转
        if (!isNavigated) {
            isNavigated = true;
            ARouter.getInstance().build("/login/LoginActivity")
                    .navigation(getApplicationContext());
            finish();
        }
    }
}