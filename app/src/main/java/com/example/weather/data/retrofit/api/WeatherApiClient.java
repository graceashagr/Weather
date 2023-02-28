package com.example.weather.data.retrofit.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 *  Class maintains a single instance of the Retrofit object and provides the WeatherApiClient object
 */
public class WeatherApiClient {

    private static Retrofit retrofit;

    /***
     * Singleton object for retrofit
     * @return retrofit instance
     */
    public static Retrofit getInstance() {
        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return retrofit;
    }

    private WeatherApiClient() {
    }

    /****
     *
     *  method to return the API service
     * @return instance of API service
     */
    public static WeatherAPI getWeatherApiClient(){
        return getInstance().create(WeatherAPI.class);
    }
}
