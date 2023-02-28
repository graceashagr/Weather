package com.example.weather.data.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Temp {

    @SerializedName("day")
    public float day;
    @SerializedName("min")
    public float temp_min;
    @SerializedName("max")
    public float temp_max;
}
