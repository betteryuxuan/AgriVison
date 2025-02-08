package com.example.homepageview.model.classes;

public class Crop {

    private String name;
    private int image;

    public Crop(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Crop() {
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
        return "Crop{" +
                "name='" + name + '\'' +
                ", image=" + image +
                '}';
    }
}
