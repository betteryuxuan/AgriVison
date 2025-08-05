package com.example.communityfragment;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;

public class SoftKeyBoardListener {
    private View rootView;
    private int rootViewVisibleHeight;
    private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;

    public SoftKeyBoardListener(Activity activity) {
        // 获取根视图
        rootView = activity.getWindow().getDecorView();
        // 监听视图树中全局布局发生改变
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            int visibleHeight = r.height();
            if (rootViewVisibleHeight == 0) {
                rootViewVisibleHeight = visibleHeight;
                return;
            }
            if (rootViewVisibleHeight - visibleHeight > 200) {
                if (onSoftKeyBoardChangeListener != null) {
                    onSoftKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                }
                rootViewVisibleHeight = visibleHeight;
                return;
            }
            if (visibleHeight - rootViewVisibleHeight > 200) {
                if (onSoftKeyBoardChangeListener != null) {
                    onSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                }
                rootViewVisibleHeight = visibleHeight;
            }
        });
    }

    public void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener listener) {
        this.onSoftKeyBoardChangeListener = listener;
    }

    public interface OnSoftKeyBoardChangeListener {
        void keyBoardShow(int height);
        void keyBoardHide(int height);
    }
}
