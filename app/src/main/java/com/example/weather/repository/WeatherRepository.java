package com.example.weather.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.weather.data.retrofit.api.WeatherAPI;
import com.example.weather.data.retrofit.model.WeatherDailyForecastResponse;
import com.example.weather.data.retrofit.model.WeatherHourlyResponse;
import com.example.weather.data.retrofit.model.WeatherResponse;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * class that handles the network request from ViewModel
 */
public class WeatherRepository {

    private final WeatherAPI weatherAPI;

    public WeatherRepository(WeatherAPI weatherAPI){
        this.weatherAPI = weatherAPI;
    }

    /***
     * gets the Current Weather details from the WeatherDataSource
     *
     * @param compositeDisposable - disposable
     * @param input - input city
     * @return - MutableLiveData<WeatherResponse>
     */

    public MutableLiveData<WeatherResponse> getWeatherDetails(CompositeDisposable compositeDisposable, String input){
        WeatherDataSource weatherDataSource = new WeatherDataSource(weatherAPI, compositeDisposable);
        weatherDataSource.getWeatherResponse(input);
        return weatherDataSource.getWeatherResponseDetails();
    }

    /***
     * gets the Hourly Weather details from the WeatherDataSource // not implemented at WeatherDataSource. check WeatherDataSource for details
     *
     * @param compositeDisposable - disposable
     * @param latitude - input city latitude
     * @param longitude - input city longitude
     * @return - List<MutableLiveData<WeatherResponse>>
     */
    public List<MutableLiveData<WeatherHourlyResponse>> getHourlyWeatherDetails(CompositeDisposable compositeDisposable, String latitude, String longitude){
        WeatherDataSource weatherDataSource = new WeatherDataSource(weatherAPI, compositeDisposable);
        weatherDataSource.getWeatherHourlyResponse(latitude, longitude);
        return weatherDataSource.getWeatherHourlyResponseDetails();
    }

    /***
     * gets the Daily Weather details from the WeatherDataSource // not implemented at WeatherDataSource. check WeatherDataSource for details
     *
     * @param compositeDisposable - disposable
     * @param latitude - input city latitude
     * @param longitude - input city longitude
     * @param count - number of days
     * @return - List<MutableLiveData<WeatherResponse>>
     */
    public List<MutableLiveData<WeatherDailyForecastResponse>> getDailyWeatherDetails(CompositeDisposable compositeDisposable, String latitude, String longitude, String count){
        WeatherDataSource weatherDataSource = new WeatherDataSource(weatherAPI, compositeDisposable);
        weatherDataSource.getWeatherDailyResponse(latitude, longitude, count);
        return weatherDataSource.getWeatherDailyResponseDetails();
    }
}
