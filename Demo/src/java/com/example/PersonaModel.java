package com.example;

import java.util.Objects;

public class PersonaModel {

    private int id;
    private String name;
    private int price;
    private int quantity;
    private double rating;
    private String imagePath;
    private boolean duplicate;

    public PersonaModel(int id, int price, String name, int quantity, double rating, String imagePath) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
        this.rating = rating;
        this.imagePath = imagePath;
        this.duplicate = false;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonaModel that = (PersonaModel) o;

        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
