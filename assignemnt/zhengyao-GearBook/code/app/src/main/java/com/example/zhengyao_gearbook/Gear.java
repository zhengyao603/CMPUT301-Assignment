package com.example.zhengyao_gearbook;

import java.math.BigDecimal;

public class Gear {
    // defined attributes
    private String date;
    private String maker;
    private String description;
    private String price;
    private String comment;
    private static BigDecimal count = new BigDecimal("0.00");

    // public generator of Gear object
    public Gear(String date, String maker, String description, String price, String comment) {
        this.date = date;
        this.maker = maker;
        this.description = description;
        this.price = price;
        this.comment = comment;
        this.count = this.count.add(new BigDecimal(price));
    }

    public void subtract() {
        this.count = this.count.subtract(new BigDecimal(this.price));
    }

    // getter functions
    public String getDate() {
        return date;
    }
    public String getMaker() {
        return maker;
    }
    public String getDescription() {
        return description;
    }
    public String getPrice() {
        return price;
    }
    public String getComment() {
        return comment;
    }
    public static BigDecimal getCount() { return count; }

    // setter functions
    public void setDate(String date) {
        this.date = date;
    }
    public void setMaker(String maker) {
        this.maker = maker;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(String price) {
        this.count = this.count.subtract(new BigDecimal(this.price));
        this.price = price;
        this.count = this.count.add(new BigDecimal(price));
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
