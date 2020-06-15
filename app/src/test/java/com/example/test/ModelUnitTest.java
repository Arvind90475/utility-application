package com.example.test;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ModelUnitTest {
    private MyUtilityModel model = new MyUtilityModel();

    @Test
    public void getFromCurrency_isCorrect(){
        assertEquals("AUD", model.getFromCurrency());
    }

    @Test
    public void getToCurrency_isCorrect(){
        String[] currencies ={"AUD","INR","EUR","USD"};
        for (int i= 0; i< 4; i++){
        assertEquals(currencies[i],model.getToCurrencies()[i]);

        }

    }

    @Test
    public void getFavCity_isCorrect(){
        assertEquals("Brisbane",model.getFavCity());
    }


    @Test
    public void setFromCurrency_isCorrect(){
        model.setFromCurrency("USD");
        assertEquals("USD", model.getFromCurrency());
    }

    @Test
    public void setToCurrency_isCorrect(){
        String[] currencies ={"AUD","INR","EUR","USD"};
        model.setToCurrencies(currencies);
        for (int i= 0; i< 4; i++){
            assertEquals(currencies[0],model.getToCurrencies()[0]);

        }

    }

    @Test
    public void setFavCity_isCorrect(){

        String[] cities = {"Brisbane","florida","Melbourne","Mumbai"};

        for (String city : cities) {
            model.setFavCity(city);
            assertEquals(city, model.getFavCity());
        }

    }



}