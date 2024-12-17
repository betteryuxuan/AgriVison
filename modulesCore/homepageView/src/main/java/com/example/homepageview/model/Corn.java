package com.example.homepageview.model;

public class Corn {

    private String name;
    private int image;

    public Corn(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Corn() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Corn{" +
                "name='" + name + '\'' +
                ", image=" + image +
                '}';
    }
}
