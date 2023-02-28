package com.example.weather.repository;

import android.content.Context;

import com.example.weather.data.retrofit.datastore.LocationDataStore;

/***
 *
 * Class that handles the calls to DataStore
 */

public class DataStoreRepository {

    private final LocationDataStore dataStore;

    public DataStoreRepository(Context context){
        dataStore = new LocationDataStore(context);
    }

    /**
     * calls the LocationDataStore to save the String vale
     * @param cityName - city name to be saved
     */

    public void saveLocation(String cityName){
        dataStore.setMyValue(cityName);
    }

    /**
     * gets the stored value from LocationDataStore
     * @return storedValue
     */

    public String getSavedLocation(){
        return dataStore.getMyValue();
    }
}
