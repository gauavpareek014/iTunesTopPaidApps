package com.uncc.inclass07test;

/**
 * Created by gaurav on 10/23/2017.
 */

public class AppData {

    long _id;
    String appName,appThumb,appPrice,dollarImage,thumb_url;
    public AppData(){

    }

    public AppData(long _id,String appName, String appThumb, String appPrice, String thumb_url,String dollarImage) {
        this._id = _id;
        this.appName = appName;
        this.appThumb = appThumb;
        this.appPrice = appPrice;
        this.thumb_url = thumb_url;
        this.dollarImage = dollarImage;
    }

    @Override
    public String toString() {
        return "AppData{" +
                "_id=" + _id +
                ", appName='" + appName + '\'' +
                ", appThumb='" + appThumb + '\'' +
                ", appPrice='" + appPrice + '\'' +
                ", dollarImage='" + dollarImage + '\'' +
                ", thumb_url='" + thumb_url + '\'' +
                '}';
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppThumb() {
        return appThumb;
    }

    public void setAppThumb(String appThumb) {
        this.appThumb = appThumb;
    }

    public String getAppPrice() {
        return appPrice;
    }

    public void setAppPrice(String appPrice) {
        this.appPrice = appPrice;
    }

    public String getDollarImage() {
        return dollarImage;
    }

    public void setDollarImage(String dollarImage) {
        this.dollarImage = dollarImage;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }
}

