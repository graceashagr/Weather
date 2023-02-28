package com.example.weather.view;


import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.weather.R;
import com.example.weather.data.retrofit.api.Constants;
import com.example.weather.databinding.ActivityMainBinding;
import com.example.weather.viewmodel.WeatherViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;

    private WeatherViewModel weatherViewModel;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = Objects.requireNonNull(navHostFragment).getNavController();

        BottomNavigationView bottomNavigationView= binding.bottomNavigation;

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavOptions navoptions =  new NavOptions.Builder()
                .setPopUpTo(R.id.weatherFragment,false)
                .setLaunchSingleTop(true).build();
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.today:
                    if(item.getItemId() != bottomNavigationView.getSelectedItemId())
                        navController.navigate(R.id.weatherFragment, null, navoptions);
                    return true;
                case R.id.tommorow:
                    navController.navigate(R.id.tomorrowWeatherFragment,null,navoptions);
                    return true;
                case R.id.ten_days:
                    navController.navigate(R.id.tenDaysWeatherFragment,null,navoptions);
                    return true;
                case R.id.hourly:
                    navController.navigate(R.id.hourlyWeatherFragment,null,navoptions);
                    return true;
            }
            return false;
        });
    }


    @Override
    public void onBackPressed() {
        // On back update the UI for the Bottom Navigation
        if (!binding.bottomNavigation.getMenu().getItem(0).isChecked()) {
            binding.bottomNavigation.getMenu().getItem(0).setChecked(true);
        }
        super.onBackPressed();
    }

    /**
     *
     * @param requestCode The request code passed in {@link "requestPermissions(
     * android.app.Activity, String[], int)}
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constants.USER_PERMISSION_CODE){
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                weatherViewModel.postPermissionUpdate(grantResults[0]);
            } else {
                weatherViewModel.postPermissionUpdate(grantResults[0]);
            }
        }
    }
}