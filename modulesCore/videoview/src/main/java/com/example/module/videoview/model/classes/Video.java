package com.example.module.videoview.model.classes;

import java.util.List;

public class Video {

    private int code; // 返回码
    private String msg; // 返回消息
    private List<Item> data; // 返回的数据列表

    public class Item {

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "url='" + url + '\'' +
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
        return "Video{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
