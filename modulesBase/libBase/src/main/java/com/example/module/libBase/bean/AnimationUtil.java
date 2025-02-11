package com.example.module.libBase.bean;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class AnimationUtil {
    // 缩放动画
    public static void setonlyAnimateView(View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f).setDuration(200);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f).setDuration(200);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.8f);

                animator1.start();
                animator2.start();
                animator3.start();

                animator1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ObjectAnimator reverseAnimator1 = ObjectAnimator.ofFloat(view, "scaleX", 0.95f, 1f).setDuration(200);
                        ObjectAnimator reverseAnimator2 = ObjectAnimator.ofFloat(view, "scaleY", 0.95f, 1f).setDuration(200);
                        ObjectAnimator reverseAnimator3 = ObjectAnimator.ofFloat(view, "alpha", 0.8f, 1f);

                        reverseAnimator1.start();
                        reverseAnimator2.start();
                        reverseAnimator3.start();
                    }
                });
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.99f).setDuration(200);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.99f).setDuration(200);
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.8f);

                animator1.start();
                animator2.start();
                animator3.start();

                animator1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ObjectAnimator reverseAnimator1 = ObjectAnimator.ofFloat(view, "scaleX", 0.99f, 1f).setDuration(200);
                        ObjectAnimator reverseAnimator2 = ObjectAnimator.ofFloat(view, "scaleY", 0.99f, 1f).setDuration(200);
                        ObjectAnimator reverseAnimator3 = ObjectAnimator.ofFloat(view, "alpha", 0.8f, 1f);

                        reverseAnimator1.start();
                        reverseAnimator2.start();
                        reverseAnimator3.start();
                    }
                });
                return true;
            }
        });
    }

    // 给有点击事件的view设置动画
    public static void setAnimateView(View view) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.95f).setDuration(200);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.95f).setDuration(200);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.8f);

        animator1.start();
        animator2.start();
        animator3.start();

        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ObjectAnimator reverseAnimator1 = ObjectAnimator.ofFloat(view, "scaleX", 0.95f, 1f).setDuration(200);
                ObjectAnimator reverseAnimator2 = ObjectAnimator.ofFloat(view, "scaleY", 0.95f, 1f).setDuration(200);
                ObjectAnimator reverseAnimator3 = ObjectAnimator.ofFloat(view, "alpha", 0.8f, 1f);

                reverseAnimator1.start();
                reverseAnimator2.start();
                reverseAnimator3.start();
            }
        });
    }

    // 震动动画
    public static void setShakeAnimateView(View view) {
        float shakeOffset = 5f;

        ValueAnimator shakeAnimator = ValueAnimator.ofFloat(0, shakeOffset, -shakeOffset, shakeOffset, 0);
        shakeAnimator.setDuration(100); // 动画持续时间
        shakeAnimator.setRepeatCount(3); // 重复次数
        shakeAnimator.setRepeatMode(ValueAnimator.REVERSE);

        shakeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setTranslationX(value);
            }
        });

        shakeAnimator.start();
    }

    public static void setLikeAnimate(View view) {
        // 放大并缩小，模拟跳动效果
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.3f, 1f);  // 水平方向放大并还原
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.3f, 1f);  // 垂直方向放大并还原

        // 设置动画持续时间和插值器
        scaleX.setDuration(300);  // 持续时间 300ms
        scaleY.setDuration(300);  // 持续时间 300ms
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());  // 插值器，模拟自然的放大和缩小效果
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());  // 插值器

        // 开始执行动画
        scaleX.start();
        scaleY.start();
    }
}
