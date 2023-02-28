package com.example.weather.data.retrofit.datastore;

import android.content.Context;
import android.content.SharedPreferences;

public class LocationDataStore {

    private static final String PREF_NAME = "location_pref";
    private static final String KEY_MY_VALUE = "recent_location";

    private final SharedPreferences preferences;

    public LocationDataStore(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     *  method to store the string vale to Shared Preference
     * @param value - location/city name
     */
    public void setMyValue(String value) {
        preferences.edit().putString(KEY_MY_VALUE, value).apply();
    }

    /***
     *  method to get the store value from Shared Preference
     * @return value for the key
     */
    public String getMyValue() {
        return preferences.getString(KEY_MY_VALUE, null);
    }
}
