package com.example.homepageview.model;

public class News {

    private String text;
    private int image;

    public News(String text, int image) {
        this.text = text;
        this.image = image;
    }

    public News() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "News{" +
                "text='" + text + '\'' +
                ", image=" + image +
                '}';
    }
}
