package com.example.module.shoppingview.model.classes;

public class Commodity {

    private String commodityName;

    private String commodityPrice;

    private int commodityImage;

    private String url;

    public Commodity(String commodityName, String commodityPrice, int commodityImage, String url) {
        this.commodityName = commodityName;
        this.commodityPrice = commodityPrice;
        this.commodityImage = commodityImage;
        this.url = url;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public int getCommodityImage() {
        return commodityImage;
    }

    public void setCommodityImage(int commodityImage) {
        this.commodityImage = commodityImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Commodity{" +
                "commodityName='" + commodityName + '\'' +
                ", commodityPrice='" + commodityPrice + '\'' +
                ", commodityImage=" + commodityImage +
                ", url='" + url + '\'' +
                '}';
    }
}
