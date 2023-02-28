package com.example.weather.data.retrofit.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/***
 *
 * class to capture the details of hourly forecast
 *
 * API - <a href="https://pro.openweathermap.org/data/2.5/forecast/hourly?lat=">...</a>{lat}&lon={lon}&appid={API key}
 */

public class WeatherHourlyResponse {


    @SerializedName("weather")
    public ArrayList<Weather> weather = new ArrayList<>();
    @SerializedName("main")
    public Main main;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("dt")
    public float dt;
    @SerializedName("dt_txt")
    public String dt_txt;

}
