package com.example.test;

public class MyUtilityModel {

    //define your class attributes here:
    private String fromCurrency;
    private String[] toCurrencies;
    private String favCity;



    //currencies
    //city


    public MyUtilityModel() {
        fromCurrency = "AUD";
        int arryCount = 4;
        toCurrencies = new String[(arryCount)];
        toCurrencies[0] = "AUD";
        toCurrencies[1] = "INR";
        toCurrencies[2] = "EUR";
        toCurrencies[3] = "USD";
        favCity = "Brisbane";


    }

    public String getFromCurrency() {
        return fromCurrency;
    }



    public String[] getToCurrencies() {
        return toCurrencies;
    }

    public String getFavCity() {
        return favCity;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }



    public void setToCurrencies(String[] toCurrencies) {
        this.toCurrencies = toCurrencies;
    }

    public void setFavCity(String favCity) {
        this.favCity = favCity;
    }
}