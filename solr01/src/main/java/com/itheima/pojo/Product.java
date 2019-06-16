package com.itheima.pojo;

import org.apache.solr.client.solrj.beans.Field;

public class Product {
    @Field
    private String id;
    @Field
    private String title;
    @Field
    private String name;
    @Field
    private Long price;
    @Field
    private String content;

    public Product() {
    }

    public Product(String id, String title, String name, Long price, String content) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.price = price;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", content='" + content + '\'' +
                '}';
    }
}
