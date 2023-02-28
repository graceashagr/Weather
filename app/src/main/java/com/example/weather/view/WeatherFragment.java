package com.example.weather.view;

import static com.example.weather.data.retrofit.api.Constants.ICON_URL;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.weather.R;
import com.example.weather.data.retrofit.model.WeatherResponse;
import com.example.weather.databinding.FragmentWeatherBinding;
import com.example.weather.viewmodel.WeatherViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 *
 *  fragment to show the UI for Current Weather details
 *
 */
public class WeatherFragment extends Fragment {

    private WeatherViewModel weatherViewModel;
    private FragmentWeatherBinding binding;

    private static final String SAVE_INPUT_TEXT = "save_input_text";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater,container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // get the ViewModel Instance
        weatherViewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        // Init on ViewModel
        weatherViewModel.init(getContext());

        if (savedInstanceState != null) {
            String inputText = savedInstanceState.getString(SAVE_INPUT_TEXT);
            binding.inputText.setText(inputText, TextView.BufferType.EDITABLE);
        }

        // listener for searching the input address
        binding.textInputLayout.setStartIconOnClickListener(view1 -> {
            String input = Objects.requireNonNull(binding.inputText.getText()).toString();
            // check if the input address is valid
            if(input.isEmpty() || !weatherViewModel.isValidAddress(input)){
                Toast.makeText(getContext(),getString(R.string.address_not_found) , Toast.LENGTH_SHORT).show();
            }else{
                // update the weather URL to base url
                weatherViewModel.updateWebURL("");
                // get the Weather response details from ViewModel
                weatherViewModel.getWeatherDetails(input);
                // saving the most recent location data Store
                weatherViewModel.setRecentLocation(input);
                addObserver();
            }
            hideKeyBoard();
        });
        //  listener to launch the webpage for the input address
        binding.weatherDetailsLayout.setOnClickListener(view12 -> goToUrl(weatherViewModel.getWebURL()));

        binding.currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(weatherViewModel.isPermissionGranted()){
                    weatherViewModel.getWeatherForCurrentLocation();
                    weatherViewModel.setRecentLocation(null);
                    addObserver();
                }else{
                    Toast.makeText(getContext()," Location permission not granted " , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onStart() {
        weatherViewModel.checkForLocationPermissions();
        super.onStart();
    }

    @Override
    public void onResume() {
        addPermissionObserver();
        addObserver();
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_INPUT_TEXT, Objects.requireNonNull(binding.inputText.getText()).toString());
    }

    /*
       Method to hide the softKeyboard when search button is clicked
     */
    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getRootView().getWindowToken(), 0);
    }

    /**
     * Method to launch the web page for weather details the displayed city
     * @param url - webpage url
     */
    private void goToUrl (String url) {
        if(weatherViewModel.isValidURL(url)){
            Uri uriUrl = Uri.parse(url);
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        }else{
            Toast.makeText(getContext(),"Not a Valid URL!" , Toast.LENGTH_SHORT).show();
        }

    }

    /**
     *  observe the live data for changes and update the UI
     */
    private void addObserver(){
        weatherViewModel.getWeatherResponseDetails().observe(requireActivity(), weatherResponse -> {
            // Handle error response
           if(weatherResponse.isEmpty()){
            Toast.makeText(getContext(),"Loading error.. please search for the city!" , Toast.LENGTH_SHORT).show();
           }else {
               // update the UI
               binding.spinner.setVisibility(View.GONE);
               binding.weatherDetailsLayout.setVisibility(View.VISIBLE);
               updateWeatherUI(weatherResponse);
           }
        });
    }

    private void addPermissionObserver(){
        weatherViewModel.getPermissionData().observe(getViewLifecycleOwner(), permission -> {
            if(permission == PackageManager.PERMISSION_GRANTED){
                weatherViewModel.getWeatherForCurrentLocation();
                weatherViewModel.setRecentLocation(null);
                // Add Observer for the LiveData - Weather Response
                addObserver();
            }else{
                showWeatherDetailsFromLastKnownLocation();
            }
        });
    }

    private void showWeatherDetailsFromLastKnownLocation(){
        binding.spinner.setVisibility(View.GONE);
        if(weatherViewModel.getRecentLocation()==null) {
            Toast.makeText(getContext(), " Location Permission required to show current weather ", Toast.LENGTH_SHORT).show();
        }
        weatherViewModel.getWeatherInfo();
    }

    /**
     * Method to update the UI fields with weather response
     * @param weatherResponse - weather details
     */
    private void updateWeatherUI(WeatherResponse weatherResponse){
        String icon_url = ICON_URL+weatherResponse.weather.get(0).icon + "@4x.png";
        Picasso.get().load(icon_url).into(binding.weatherIcon);
        binding.cityName.setText(weatherResponse.name);
        binding.feelsLikeTemp.setText(String.format("%s%s", weatherResponse.main.feels_like, getString(R.string.celsius_suffix)));
        binding.temperature.setText(String.format("%s%s", weatherResponse.main.temp, getString(R.string.celsius_suffix)));
        binding.weatherCondition.setText(weatherResponse.weather.get(0).description);
        binding.tempMax.setText(String.format("%s %s%s", getString(R.string.temp_max) , weatherResponse.main.temp_max, getString(R.string.celsius_suffix)));
        binding.tempMin.setText(String.format("%s %s%s", getString(R.string.temp_min) , weatherResponse.main.temp_min, getString(R.string.celsius_suffix)));
        binding.humidity.setText(String.format("%s %s%s", getString(R.string.humidity) , weatherResponse.main.humidity, getString(R.string.percentage_suffix)));
        binding.wind.setText(String.format("%s %s%s", getString(R.string.wind) , weatherResponse.wind.speed, getString(R.string.speed_suffix)));
        weatherViewModel.updateWebURL(String.valueOf(weatherResponse.id));
    }

}
