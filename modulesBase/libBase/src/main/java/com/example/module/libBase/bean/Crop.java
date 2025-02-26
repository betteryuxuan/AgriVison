package com.example.module.libBase.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Crop implements Parcelable {

    private String name;
    private int image;

    public Crop(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public Crop() {
    }

    protected Crop(Parcel in) {
        name = in.readString();
        image = in.readInt();
    }

    public static final Creator<Crop> CREATOR = new Creator<Crop>() {
        @Override
        public Crop createFromParcel(Parcel in) {
            return new Crop(in);
        }

        @Override
        public Crop[] newArray(int size) {
            return new Crop[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(image);
    }
}
