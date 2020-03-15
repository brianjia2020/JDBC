package com.atguigu.bean;

import java.sql.Date;

public class Stock_price {
    private int id;
    private int stock_id;
    private Date stock_date;
    private float open_price;
    private float close_price;
    private float volume;
    private float dividened;
    private float split;

    public Stock_price() {
    }

    public Stock_price(int id, int stock_id, Date stock_date, float open_price, float close_price, float volume, float dividened, float split) {
        this.id = id;
        this.stock_id = stock_id;
        this.stock_date = stock_date;
        this.open_price = open_price;
        this.close_price = close_price;
        this.volume = volume;
        this.dividened = dividened;
        this.split = split;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }

    public Date getStock_date() {
        return stock_date;
    }

    public void setStock_date(Date stock_date) {
        this.stock_date = stock_date;
    }

    public float getOpen_price() {
        return open_price;
    }

    public void setOpen_price(float open_price) {
        this.open_price = open_price;
    }

    public float getClose_price() {
        return close_price;
    }

    public void setClose_price(float close_price) {
        this.close_price = close_price;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public float getDividened() {
        return dividened;
    }

    public void setDividened(float dividened) {
        this.dividened = dividened;
    }

    public float getSplit() {
        return split;
    }

    public void setSplit(float split) {
        this.split = split;
    }

    @Override
    public String toString() {
        return "Stock_price{" +
                "id=" + id +
                ", stock_id=" + stock_id +
                ", stock_date=" + stock_date +
                ", open_price=" + open_price +
                ", close_price=" + close_price +
                ", volume=" + volume +
                ", dividened=" + dividened +
                ", split=" + split +
                '}';
    }
}
