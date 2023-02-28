package com.example.weather.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.weather.databinding.FragmentWeatherTenDaysBinding;

/**
 *
 *  fragment to show the UI for Daily Weather details
 *   Not implemented - Enhancement - check WeatherAPI class for more details
 */
public class TenDaysWeatherFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentWeatherTenDaysBinding binding = FragmentWeatherTenDaysBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
