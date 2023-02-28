package com.example.weather.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weather.data.retrofit.api.WeatherAPI;
import com.example.weather.data.retrofit.api.WeatherApiClient;
import com.example.weather.data.retrofit.model.WeatherDailyForecastResponse;
import com.example.weather.data.retrofit.model.WeatherHourlyResponse;
import com.example.weather.data.retrofit.model.WeatherResponse;
import com.example.weather.repository.DataStoreRepository;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.utils.WeatherUtils;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 *  View Model class to fetch the data and provide to the UI controller
 *
 */
public class WeatherViewModel extends ViewModel {

    private WeatherRepository weatherRepository;
    private CompositeDisposable compositeDisposable;
    public MutableLiveData<WeatherResponse> weatherDetails;
    public List<MutableLiveData<WeatherHourlyResponse>> weatherHourlyDetails;
    public List<MutableLiveData<WeatherDailyForecastResponse>> weatherDailyDetails;
    private WeatherUtils weatherUtils;
    private DataStoreRepository dataStoreRepository;

    private MutableLiveData<Integer> permissionData;

    public void init(Context context){
        compositeDisposable = new CompositeDisposable();
        WeatherAPI weatherAPI = WeatherApiClient.getWeatherApiClient();
        weatherRepository = new WeatherRepository(weatherAPI);
        weatherDetails = new MutableLiveData<>();
        permissionData = new MutableLiveData<>();
        weatherUtils = new WeatherUtils(context);
        dataStoreRepository = new DataStoreRepository(context);
        getWeatherInfo();
    }

    /**
     *  get current Weather details
     *  calls to weatherRepository for the weather response details
     * @param input - input city name
     */
    public void getWeatherDetails(String input){
        if(input == null) {
            weatherDetails.postValue(new WeatherResponse());
        } else {
            weatherDetails = weatherRepository.getWeatherDetails(compositeDisposable, input);
        }
    }
    /**
     * Method not implemented at the API level - check WeatherAPI/WeatherRepository/WeatherDataSource for more details
     *
     *  get hourly Weather details
     *  calls to weatherRepository for the hourly  weather response details
     * @param latitude - latitude
     * @param longitude - longitude
     *
     */
    public void getWeatherHourlyDetails(String latitude , String longitude){
        weatherHourlyDetails = weatherRepository.getHourlyWeatherDetails(compositeDisposable,latitude,longitude);
    }

    /**
     * Method not implemented at the API level - check WeatherAPI/WeatherRepository/WeatherDataSource for more details
     *
     *  get daily Weather details
     *  calls to weatherRepository for the hourly  weather response details
     * @param latitude - latitude
     * @param longitude - longitude
     * @param count - number of days
     */
    public void getWeatherDailyDetails(String latitude ,  String longitude ,String count){
        weatherDailyDetails = weatherRepository.getDailyWeatherDetails(compositeDisposable,latitude,longitude,count);
    }

    /**
     *  UI controller calls this method for current weather details
     * @return - MutableLiveData<WeatherResponse> -  current weather response
     */
    public MutableLiveData<WeatherResponse> getWeatherResponseDetails(){
        return weatherDetails;
    }

    /**
     *  UI controller calls this method for hourly weather details
     * @return -List<MutableLiveData<WeatherHourlyResponse>> - list of hourly weather response
     */
    public List<MutableLiveData<WeatherHourlyResponse>> getWeatherHourlyResponseDetails() { return weatherHourlyDetails;}

    /**
     *  UI controller calls this method for daily weather details
     * @return -
     */
    public List<MutableLiveData<WeatherDailyForecastResponse>> getWeatherDailyResponseDetails() { return weatherDailyDetails;}


    /**
     *  First call to request for the weather details
     *  called from viewModel init()
     */
    public void getWeatherInfo(){

        if(weatherUtils.isPermissionGranted()){
            if(getRecentLocation()!=null){
                getWeatherDetails(getRecentLocation());
            }else{
                getWeatherForCurrentLocation();
            }
        }else{
            if(getRecentLocation()!=null){
                getWeatherDetails(getRecentLocation());
            }
        }


    }
    public void getWeatherForCurrentLocation(){
        getWeatherDetails(weatherUtils.getCurrentCity());
    }

    /**
     * check if the input is a valid address
     * @param input - input address
     * @return - true if valid, false otherwise
     */
    public Boolean isValidAddress(String input){
        return weatherUtils.isValidAddress(input);
    }

    /**
     *  get saved location from data store
     * @return - saved location/city
     */
    public String getRecentLocation() {
        return dataStoreRepository.getSavedLocation();
    }

    /**
     *  save the location to the data store
     * @param recentLocation - city/address to save
     */
    public void setRecentLocation(String recentLocation) {
        dataStoreRepository.saveLocation(recentLocation);
    }

    /**
     *  get the web URL to launch the web page for displayed city
     * @return - web url
     */
    public String getWebURL() {
        return weatherUtils.getWebURL();
    }

    /**
     *  updates the web URL with the input city
     * @param city - city name to be appended to base url of the web page
     */
    public void updateWebURL(String city) {
        weatherUtils.updateWebURL(city);
    }

    /**
     *  checks if the URL is valid
     * @param webURL - url
     * @return - true if valid, false otherwise
     */
    public Boolean isValidURL(String webURL){
        return weatherUtils.isValidURL(webURL);
    }
    public void checkForLocationPermissions(){
        weatherUtils.checkForLocationPermission();
    }
    public void postPermissionUpdate(int permission){
        permissionData.postValue(permission);
    }

    public MutableLiveData<Integer> getPermissionData(){
        return permissionData;
    }

    public boolean isPermissionGranted(){
        return weatherUtils.isPermissionGranted();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
