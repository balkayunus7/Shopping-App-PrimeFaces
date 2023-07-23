package com.example;

public class PersonaModel {

    private int id;
    private String name;
    private int price;
    private int quantity;
    private double rating;
    private String imagePath;
    private String description;

    public PersonaModel(int id, int price, String name, int quantity, double rating, String imagePath, String description) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
        this.rating = rating;
        this.imagePath = imagePath;
        this.description = description;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
