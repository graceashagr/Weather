package com.example.weather.data.retrofit.api;


import com.example.weather.data.retrofit.model.WeatherDailyForecastResponse;
import com.example.weather.data.retrofit.model.WeatherHourlyResponse;
import com.example.weather.data.retrofit.model.WeatherResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *  Weather API class defines apis for network request
 */

public interface WeatherAPI {

    /** Method to get the Current Weather for the openweathermap.org
     *
      * @param cityName - input city name
     * @param app_id - api key
     * @param unit_metrics - metrics
     * @return Observable<WeatherResponse> - Weather response as a observable
     */
    @GET("data/2.5/weather?")
    Observable<WeatherResponse> getCurrentWeather(@Query("q") String cityName,
                                                  @Query("APPID") String app_id,
                                                  @Query("units") String unit_metrics);

    /** Method to get the Daily Weather forecast from the openweathermap.org
     *  * Method signature can be modified to take
     *      * {city name}
     *      * {city name},{state code},{country code}
     *      * {city name},{country code}
     *
     * @param latitude - latitude of the location
     * @param longitude - longitude of the location
     * @param count - count of days
     * @param app_id - api key
     * @param unit_metrics - metrics
     * @return List<Observable<WeatherDailyForecastResponse>> - WeatherDailyForecastResponse as a List of observable
     */

    @GET("data/2.5/forecast/daily?")
    List<Observable<WeatherDailyForecastResponse>> getDailyWeather(@Query("lat") String latitude,
                                                                   @Query("lon") String longitude,
                                                                   @Query("cnt") String count ,
                                                                   @Query("APPID") String app_id,
                                                                   @Query("units") String unit_metrics);

    /** Method to get the Hourly Weather forecast from the openweathermap.org
     *  * Method signature can be modified to take
     *      * {city name}
     *      * {city name},{state code},{country code}
     *      * {city name},{country code}
     *
     * @param latitude - latitude of the location
     * @param longitude - longitude of the location
     * @param app_id - api key
     * @param unit_metrics - metrics
     * @return List<Observable<WeatherHourlyResponse>> - WeatherHourlyResponse as a List of observable
     */

    @GET("data/2.5/forecast/hourly?")
    List<Observable<WeatherHourlyResponse>> getHourlyWeather(@Query("lat") String latitude,
                                                             @Query("lon") String longitude,
                                                             @Query("APPID") String app_id,
                                                             @Query("units") String unit_metrics);
}