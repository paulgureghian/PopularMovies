package com.example.android.popularmoviesdemo;

public class Product {

    private int id;
    private String name;
    private String description;

    public Product() {
        super();
    }

    public Product(int id, String description, String name) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId() {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription() {
        this.description = description;
    }

}
