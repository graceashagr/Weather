package com.example.weather.data.retrofit.model;

import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    public float speed;
    @SerializedName("deg")
    public float deg;
}
