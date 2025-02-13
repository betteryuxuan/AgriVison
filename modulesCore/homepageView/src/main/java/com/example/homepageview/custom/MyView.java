package com.example.homepageview.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyView extends LinearLayout {
    private float startX, startY, endY, endX;
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean shouldIntercept = false; // 默认不拦截

//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                // 记录手指按下时的坐标
//                startX = event.getX();
//                startY = event.getY();
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                // 计算手指滑动的距离
//                endX = event.getX();
//                endY = event.getY();
//                float diffX = endX - startX;
//                float diffY = endY - startY;
//
//                // 判断滑动方向，横向滑动让子视图处理，竖向滑动让父视图处理
//                if (Math.abs(diffX) > Math.abs(diffY)) {
//                    // 横向滑动，子视图处理
//                    shouldIntercept = true; // 子视图拦截
//                } else {
//                    // 竖向滑动，父视图处理
//                    shouldIntercept = false; // 父视图拦截
//                }
//                break;
//        }

        // 告诉父视图是否需要拦截
        getParent().requestDisallowInterceptTouchEvent(!shouldIntercept);

        // 返回是否拦截事件：横向滑动拦截事件，竖向滑动不拦截
        return shouldIntercept;
    }




}
