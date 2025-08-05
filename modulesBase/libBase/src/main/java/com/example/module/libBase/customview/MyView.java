package com.example.module.libBase.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyView extends LinearLayout {
    private float startX, startY;

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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                getParent().requestDisallowInterceptTouchEvent(true); // 不让父布局拦截事件
                break;

            case MotionEvent.ACTION_MOVE:
                float endX = event.getX();
                float endY = event.getY();
                float diffX = Math.abs(endX - startX);
                float diffY = Math.abs(endY - startY);

                if (diffX > diffY) {
                    // 水平方向滑动，交给 ViewPager2 处理，不拦截
                    getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                } else {
                    // 垂直方向滑动，父布局处理，拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return true;
                }
        }
        return super.onInterceptTouchEvent(event);
    }
}
