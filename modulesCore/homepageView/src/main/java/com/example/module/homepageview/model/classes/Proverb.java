package com.example.module.homepageview.model.classes;

import java.util.List;

public class Proverb {
    private int code;
    private String msg;
    private List<ProverbData> data;

    // Getter 和 Setter 方法
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

    public List<ProverbData> getData() {
        return data;
    }

    public void setData(List<ProverbData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Proverb{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static class ProverbData {
        private String sentence;
        private String annotation;

        // Getter 和 Setter 方法
        public String getSentence() {
            return sentence;
        }

        public void setSentence(String sentence) {
            this.sentence = sentence;
        }

        public String getAnnotation() {
            return annotation;
        }

        public void setAnnotation(String annotation) {
            this.annotation = annotation;
        }

        @Override
        public String toString() {
            return "ProverbData{" +
                    "sentence='" + sentence + '\'' +
                    ", annotation='" + annotation + '\'' +
                    '}';
        }
    }
}
