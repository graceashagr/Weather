package com.example.weather.data.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/***
 * Class to receive the current weather response data
 *
 * API - <a href="https://api.openweathermap.org/data/2.5/weather?q=">...</a>{city name}&appid={API key}
 *       <a href="https://api.openweathermap.org/data/2.5/weather?q=">...</a>{city name},{country code}&appid={API key}
 *       <a href="https://api.openweathermap.org/data/2.5/weather?q=">...</a>{city name},{state code},{country code}&appid={API key}
 */
public class WeatherResponse {

    @SerializedName("coord")
    public Coord coord;
    @SerializedName("sys")
    public Sys sys;
    @SerializedName("weather")
    public ArrayList<Weather> weather = new ArrayList<Weather>();
    @SerializedName("main")
    public Main main;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("rain")
    public Rain rain;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("dt")
    public float dt;
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public float cod;

    public boolean isEmpty(){
        if(this==null){
            return true;
        }
        return this.name == null;
    }


}

