package com.uncc.inclass07test;

/**
 * Created by gaurav on 10/23/2017.
 */

public class Filter {
    String name,thumb_url,thumb_url_large;

    Double price;
    long id;

    public Filter()
    {

    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", thumb_url='" + thumb_url + '\'' +
                ", id=" + id +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public Filter(String name, Double price, String thumb_url,String thumb_url_large) {
        this.name = name;
        this.price = price;
        this.thumb_url = thumb_url;
        this.thumb_url_large = thumb_url_large;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getThumb_url() {
        return thumb_url;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public String getThumb_url_large() {
        return thumb_url_large;
    }

    public void setThumb_url_large(String thumb_url_large) {
        this.thumb_url_large = thumb_url_large;
    }
}
