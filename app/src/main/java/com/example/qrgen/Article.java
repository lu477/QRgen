package com.example.qrgen;

public class Article {

    String name, amount, color, price;

    Article(){

    }

    public Article(String name, String amount, String color, String price) {
        this.name = name;
        this.amount = amount;
        this.color = color;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor(){ return color; }

    public void setColor(String color){ this.color = color; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
