package com.example.weather.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.weather.data.retrofit.api.Constants;
import com.example.weather.view.MainActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 *  Class that holds the utility functions for location, URL
 *
 */
public class WeatherUtils{

    private Context context;
    private final Geocoder geoCoder;

    private String webURL;

    public WeatherUtils(Context context) {

        this.context = context;
        geoCoder = new Geocoder(context, Locale.getDefault());
    }

    /**
     * fetches the current location using the Location Manager
     * @return location - current location
     */
    @SuppressLint("MissingPermission")
    public Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           return null;
        }else{
            return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

    }

    public void checkForLocationPermission(){
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    (MainActivity)context,
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    Constants.USER_PERMISSION_CODE
            );
        }
    }



    public boolean isPermissionGranted(){
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }else{
            return true;
        }
    }

    /**
     *  Method returns the city name given the Latitude , Longitude of a place
     * @param latitude -latitude
     * @param longitude - longitude
     * @return cityName - city name for the latitude and longitude
     */
    private String getCityName(Double latitude, Double longitude){
        String cityName = null ;

        try {
            List<Address> addresses = geoCoder.getFromLocation(latitude,longitude,1);
            for(Address address: addresses){
                if(address!=null){
                    cityName = address.getLocality();
                }else{
                    Toast.makeText(context.getApplicationContext()," City not found" , Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cityName;
    }

    /***
     *
     *  utility method for the view model to get the current city name
     * @return cityName
     */
    public String getCurrentCity(){
        Location location = getCurrentLocation();
        if(location == null){
            return null;
        }else{
            return getCityName(location.getLatitude() ,location.getLongitude());
        }
    }

    /**
     *  Method to check if the input is a valid address
     * @param input - input address
     * @return boolean - true for valid input , false otherwise
     */
    public Boolean isValidAddress(String input){
        try {
            List<Address> address = geoCoder.getFromLocationName(input,1);
            return address != null && address.size() != 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     *  Method to get the Latitude and Longitude of a given city
     * @param input - city name/address
     * @return - String[] of latitude and longitude
     */
    public String[] getLatLongForCity(String input){
        try {
            List<Address> address = geoCoder.getFromLocationName(input,1);
            if(address != null && address.size() != 0){
                return new String[0];
            }else{
                assert address != null;
                Address location = address.get(0);
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();
                return new String[]{String.valueOf(latitude),String.valueOf(longitude)};
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  returns the web URL
     * @return url
     */

    public String getWebURL() {
        return webURL;
    }

    /**
     *  updates the url with  input city
     * @param city - city name
     */

    public void updateWebURL(String city) {
        if(city.isEmpty()){
            this.webURL = Constants.Web_URL;
        }else{
            this.webURL = Constants.Web_URL + city;
        }
    }


    /**
     * checks if the url is valid
     * @param webURL - url for the web page
     * @return - true if valid , false otherwise
     */
    public Boolean isValidURL(String webURL){
        return webURL != null && !webURL.isEmpty() && !webURL.endsWith("/");
    }
}
