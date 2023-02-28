package com.example.weather.data.retrofit.api;


/**
 *  Class defines all the constants used in the data/api/* classes
 */

public class Constants {
    // permission code passed for requesting Location Permission from the USer
    public static int USER_PERMISSION_CODE = 1;
    // base url for the network request for weather details
    public static String BASE_URL = "https://api.openweathermap.org/";
    // API key for network request
    public static String APP_ID = "89c87eb61d7c3148805a113bfa0c6221";
    // metric for the weather details - celsius
    public static String METRIC = "metric";
    // base url for loading weather icon
    public static String ICON_URL = "https://openweathermap.org/img/wn/";
    // base url for launching the web page for the city
    public static String Web_URL = "https://openweathermap.org/city/";
}
