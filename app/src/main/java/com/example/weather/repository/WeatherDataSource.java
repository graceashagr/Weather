package com.example.weather.repository;

import static com.example.weather.data.retrofit.api.Constants.APP_ID;
import static com.example.weather.data.retrofit.api.Constants.METRIC;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.weather.data.retrofit.api.WeatherAPI;
import com.example.weather.data.retrofit.model.WeatherDailyForecastResponse;
import com.example.weather.data.retrofit.model.WeatherHourlyResponse;
import com.example.weather.data.retrofit.model.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 *  Class acts as the dataSource for the Weather details.
 *  It performs the network request through the API call and populates the weatherResponseDetails LiveData
 */
public class WeatherDataSource {

    private final WeatherAPI weatherAPI;
    private CompositeDisposable compositeDisposable;

    private final MutableLiveData<WeatherResponse> weatherResponseDetails= new MutableLiveData<>();

    private final List<MutableLiveData<WeatherHourlyResponse>> weatherHourlyResponseDetails = new ArrayList<>();

    public List<MutableLiveData<WeatherHourlyResponse>> getWeatherHourlyResponseDetails() {
        return weatherHourlyResponseDetails;
    }

    public List<MutableLiveData<WeatherDailyForecastResponse>> getWeatherDailyResponseDetails() {
        return weatherDailyResponseDetails;
    }

    private final List<MutableLiveData<WeatherDailyForecastResponse>> weatherDailyResponseDetails = new ArrayList<>();

    public WeatherDataSource(WeatherAPI weatherAPI , CompositeDisposable compositeDisposable){
        this.weatherAPI = weatherAPI;
        this.compositeDisposable = compositeDisposable;
    }

    public MutableLiveData<WeatherResponse> getWeatherResponseDetails() {
        return weatherResponseDetails;
    }

    /**
     * Method that calls the network request for current weather using Retrofit API service
     * populates the response details to the Livedata and Handles error conditions
     * @param input - input city name
     */
    public void getWeatherResponse(String input){

        try{
            Observable<WeatherResponse> weatherResponseObservable = weatherAPI.getCurrentWeather(input, APP_ID, METRIC);
            weatherResponseObservable.subscribeOn(Schedulers.io())
                    .onErrorReturnItem(new WeatherResponse())
                    .subscribe(new Observer<WeatherResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(WeatherResponse weatherResponse) {
                            weatherResponseDetails.postValue(weatherResponse);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("WeatherResponse" , e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }catch(Exception e){
            Log.d("WeatherResponse" , e.getMessage());
        }

    }

    /**
     * Method not implemented  - API key is not free
     * Method signature can be modified to take
     * {city name}
     * {city name},{state code},{country code}
     * {city name},{country code}
     *
     * Method that calls the network request for hourly weather using Retrofit API service
     * populates the response details to the Livedata and Handles error conditions
     * @param latitude - latitude of the desired location
     * @param longitude - longitude of the desired location
     */
    public void getWeatherHourlyResponse(String latitude , String longitude){

    }

    /**
     * Method not implemented  - API key is not free
     * Method signature can be modified to take
     * {city name}
     * {city name},{state code},{country code}
     * {city name},{country code}
     *
     * Method that calls the network request for daily weather using Retrofit API service
     * populates the response details to the Livedata and Handles error conditions
     * @param latitude - latitude of the desired location
     * @param longitude - longitude of the desired location
     * @param count - number of days
     */
    public void getWeatherDailyResponse(String latitude , String longitude, String count){

    }

}
