package com.example.lab8_and103.Model;

public class GHNItem {
    private String name, code;
    private int quantity, weight;
    private double price;

    public GHNItem() {
    }

    public GHNItem(String name, String code, int quantity, int weight, double price) {
        this.name = name;
        this.code = code;
        this.quantity = quantity;
        this.weight = weight;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
