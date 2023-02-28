package com.example.weather.data.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 *
 * class to capture the daily weather forecast response
 *
 * API - api.openweathermap.org/data/2.5/forecast/daily?lat={lat}&lon={lon}&cnt={cnt}&appid={API key}
 */
public class WeatherDailyForecastResponse {

    @SerializedName("weather")
    public ArrayList<Weather> weather = new ArrayList<>();
    @SerializedName("temp")
    public Temp temp;
    @SerializedName("dt")
    public float dt;

}
