package com.example.personalinfoview.bean;

import android.widget.ImageView;

public class MenuItem {
    private int imageResId = 0;
    private String title = " ";

    public MenuItem() {
    }

    public MenuItem(int imageResId, String title) {
        this.imageResId = imageResId;
        this.title = title;
    }

    /**
     * 获取
     *
     * @return imageResId
     */
    public int getImageResId() {
        return imageResId;
    }

    /**
     * 设置
     *
     * @param imageResId
     */
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    /**
     * 获取
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return "MenuItem{imageResId = " + imageResId + ", title = " + title + "}";
    }
}