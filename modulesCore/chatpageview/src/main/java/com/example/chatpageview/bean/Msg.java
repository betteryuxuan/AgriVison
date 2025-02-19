package com.example.chatpageview.bean;

public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SENT = 1;
    public static final int TYPE_THINKING = 2;


    private String content;
    private int type;
    private String time;

    public Msg(String content, String time, int type) {
        this.content = content;
        this.time = time;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
    public String getTime() {
        return time;
    }
}
