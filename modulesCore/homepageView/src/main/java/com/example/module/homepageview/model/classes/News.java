package com.example.module.homepageview.model.classes;

import java.util.List;

public class News {

    private int code; // 返回码
    private String msg; // 返回消息
    private List<Item> data; // 返回的数据列表

    public static class Item {
        private String title; // 新闻标题
        private String content; // 新闻内容
        private String image; // 新闻图片

        private boolean isCollected = false;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isCollected() {
            return isCollected;
        }

        public void setCollected(boolean collected) {
            isCollected = collected;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", image='" + image + '\'' +
                    ", isCollected=" + isCollected +
                    '}';
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Item> getData() {
        return data;
    }

    public void setData(List<Item> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "News{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
