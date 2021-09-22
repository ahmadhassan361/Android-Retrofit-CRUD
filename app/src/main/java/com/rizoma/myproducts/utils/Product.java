package com.rizoma.myproducts.utils;

public class Product {
    private int id;
    private String name;
    private String desc;
    private int price;
    private  String image;
    private boolean inStock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public Product() {
    }

    public Product(int id, String name, String desc, int price, String image, boolean inStock) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.image = image;
        this.inStock = inStock;
    }
}
