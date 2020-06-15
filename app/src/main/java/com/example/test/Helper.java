package com.example.test;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

class Helper {

    static private final String RATE_API_URL_CORE
            = "https://api.exchangerate-api.com/v4/latest/";

    static  private final String TEMPERATURE_API_URL
            = "https://weather.ls.hereapi.com/weather/1.0/report.json?apiKey=biNxu6zu-YMfJz_eG0TbS58-Bm5UU7Ua0k1E_Rj6kFM&product=observation&name=";



    // return current date and time
    static String getCurrentTime(){

        Date currentDateTime = Calendar.getInstance().getTime();
        DateFormat formatter = DateFormat.getDateTimeInstance();
        return formatter.format(currentDateTime);
    }

    //get info from api
    private static String getInfo(String api_url){

        try {

            URL url = new URL(api_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            final StringBuilder builder = new StringBuilder();
            try {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) break;
                    builder.append(line);
                }
                reader.close();
                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //return rates based on the array toCurrencies
    static double [] getRates(String fromCurrency, String [] toCurrencies){
        double[] rates = new double[toCurrencies.length];
        Arrays.fill(rates, -1.0);
        String rateApiUrl = RATE_API_URL_CORE + fromCurrency;
        String rateInfoStr = getInfo(rateApiUrl);
        if(rateInfoStr != null){
            try {
                JSONObject jsonObject = new JSONObject(rateInfoStr);
                JSONObject rateObj = jsonObject.getJSONObject("rates");
                for(int j = 0; j < rates.length; j++){
                    try {
                        rates[j] = rateObj.getDouble(toCurrencies[j]);
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
        return rates;
    }
    // return weatherInfo based on the favCity
    static String[] getTemp(String favCity){
        String[] temp = new String[3];
        String temperatureApiUrl = TEMPERATURE_API_URL+ favCity;
        String temperatureInfoStr = Helper.getInfo(temperatureApiUrl);
            if(temperatureInfoStr != null){
                try {
                    final JSONObject jsonObject = new JSONObject(temperatureInfoStr);
                    JSONObject observationsObj = jsonObject.getJSONObject("observations");
                    JSONArray locationArr = observationsObj.getJSONArray("location");
                    JSONObject locationObj = locationArr.getJSONObject(0);

                    JSONArray  observationArr = locationObj.getJSONArray("observation");
                    JSONObject observationObj = observationArr.getJSONObject(0);

                    //getting temperature
                    temp[0] = observationObj.getString("temperature");


                    // getting description
                    temp[1] = observationObj.getString("skyDescription");

                    //getting city
                    temp[2] = locationObj.getString("city");

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        return temp;
    }

}