package com.itheima.pojo;

public class Product {
    private String pid;
    private String title;
    private String price;
    private String brand;
    private String name;
    private String url;

    public Product() {
    }

    public Product(String pid, String title, String price, String brand, String name, String url) {
        this.pid = pid;
        this.title = title;
        this.price = price;
        this.brand = brand;
        this.name = name;
        this.url = url;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid='" + pid + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
