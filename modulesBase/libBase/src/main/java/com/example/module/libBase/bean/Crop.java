package com.example.module.libBase.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Crop implements Parcelable {

    private int code;
    private String msg;
    private List<DataItem> data;

    public Crop() {
    }

    protected Crop(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        data = in.createTypedArrayList(DataItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
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

    @Override
    public String toString() {
        return "CropData{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
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

    public List<DataItem> getData() {
        return data;
    }

    public void setData(List<DataItem> data) {
        this.data = data;
    }

    public static class DataItem implements Parcelable {
        private String category;
        private List<CropDetail> crop_detail;

        public DataItem() {
        }

        protected DataItem(Parcel in) {
            category = in.readString();
            crop_detail = in.createTypedArrayList(CropDetail.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(category);
            dest.writeTypedList(crop_detail);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DataItem> CREATOR = new Creator<DataItem>() {
            @Override
            public DataItem createFromParcel(Parcel in) {
                return new DataItem(in);
            }

            @Override
            public DataItem[] newArray(int size) {
                return new DataItem[size];
            }
        };

        @Override
        public String toString() {
            return "DataItem{" +
                    "category='" + category + '\'' +
                    ", cropDetail=" + crop_detail +
                    '}';
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public List<CropDetail> getCropDetail() {
            return crop_detail;
        }

        public void setCropDetail(List<CropDetail> cropDetail) {
            this.crop_detail = cropDetail;
        }
    }

    public static class CropDetail implements Parcelable {

        private String name;
        private String icon;
        private String spell;
        private String description;
        private String introduction;
        private String image1;
        private String image2;
        private Boolean isCollected = false;

        public CropDetail() {
        }

        protected CropDetail(Parcel in) {
            name = in.readString();
            icon = in.readString();
            spell = in.readString();
            description = in.readString();
            introduction = in.readString();
            image1 = in.readString();
            image2 = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeString(icon);
            dest.writeString(spell);
            dest.writeString(description);
            dest.writeString(introduction);
            dest.writeString(image1);
            dest.writeString(image2);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<CropDetail> CREATOR = new Creator<CropDetail>() {
            @Override
            public CropDetail createFromParcel(Parcel in) {
                return new CropDetail(in);
            }

            @Override
            public CropDetail[] newArray(int size) {
                return new CropDetail[size];
            }
        };

        @Override
        public String toString() {
            return "CropDetail{" +
                    "name='" + name + '\'' +
                    ", icon='" + icon + '\'' +
                    ", spell='" + spell + '\'' +
                    ", description='" + description + '\'' +
                    ", introduction='" + introduction + '\'' +
                    ", image1='" + image1 + '\'' +
                    ", image2='" + image2 + '\'' +
                    '}';
        }

        public Boolean getCollected() {
            return isCollected;
        }

        public void setCollected(Boolean collected) {
            isCollected = collected;
        }

        public String getSpell() {
            return spell;
        }

        public void setSpell(String spell) {
            this.spell = spell;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getImage1() {
            return image1;
        }

        public void setImage1(String image1) {
            this.image1 = image1;
        }

        public String getImage2() {
            return image2;
        }

        public void setImage2(String image2) {
            this.image2 = image2;
        }
    }
}
