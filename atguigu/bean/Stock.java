package com.atguigu.bean;

public class Stock {
    private int id;
    private String ticker;
    private String company_name;
    private String exchange;
    private String currency;
    private String country;
    private String address;
    private String sector;

    public Stock() {
    }

    public Stock(int id, String ticker, String company_name, String exchange, String currency, String country, String address, String sector) {
        this.id = id;
        this.ticker = ticker;
        this.company_name = company_name;
        this.exchange = exchange;
        this.currency = currency;
        this.country = country;
        this.address = address;
        this.sector = sector;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", company_name='" + company_name + '\'' +
                ", exchange='" + exchange + '\'' +
                ", currency='" + currency + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", sector='" + sector + '\'' +
                '}';
    }
}
