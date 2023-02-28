package com.example.weather;


import static org.junit.Assert.assertEquals;

import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.weather.view.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LocationPermissionsTest {

    @Test
    public void testLocationPermissions() {
        // Launch the activity that requires location permissions
        ActivityScenario.launch(MainActivity.class);

        // Wait for the location permissions to be granted
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the permission has been granted
        assertEquals(ContextCompat.checkSelfPermission(
                InstrumentationRegistry.getInstrumentation().getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION),
                PackageManager.PERMISSION_GRANTED);
    }
}
