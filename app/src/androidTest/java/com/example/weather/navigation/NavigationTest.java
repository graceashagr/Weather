package com.example.weather.navigation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.NavController;
import androidx.navigation.testing.TestNavHostController;
import androidx.navigation.ui.NavigationUI;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import com.example.weather.R;
import com.example.weather.view.MainActivity;
import com.example.weather.view.WeatherFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class NavigationTest {
    @Rule
    public final ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private static final int[] MENU_CONTENT_ITEM_IDS = {
            R.id.today, R.id.hourly,R.id.tommorow, R.id.ten_days
    };

    private Map<Integer, String> menuStringContent;
    private BottomNavigationView bottomNavigation;

    private NavController navController;
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = activityTestRule.getActivity();
        activity.setTheme(R.style.Theme_Weather);
        bottomNavigation = activity.findViewById(R.id.bottom_navigation);

        final Resources res = activity.getResources();
        menuStringContent = new HashMap<>(MENU_CONTENT_ITEM_IDS.length);
        menuStringContent.put(R.id.today, res.getString(R.string.today));
        menuStringContent.put(R.id.tommorow, res.getString(R.string.tomorrow));
        menuStringContent.put(R.id.ten_days, res.getString(R.string.ten_days));

        navController = new TestNavHostController(
                ApplicationProvider.getApplicationContext()
        );


    }

    @Test
    @SmallTest
    public void testBasics() {
        // Check the contents of the Menu object
        final Menu menu = bottomNavigation.getMenu();
        assertNotNull("Menu should not be null", menu);
        assertEquals("Should have matching number of items", MENU_CONTENT_ITEM_IDS.length, menu.size());
        for (int i = 0; i < MENU_CONTENT_ITEM_IDS.length; i++) {
            final MenuItem currItem = menu.getItem(i);
            assertEquals("ID for Item #" + i, MENU_CONTENT_ITEM_IDS[i], currItem.getItemId());
        }
    }

    @Test
    public void testBottomNavigationItemSelection() throws Throwable {

        ActivityScenario.launch(MainActivity.class);
        FragmentScenario<WeatherFragment> weatherFragmentScenario = FragmentScenario.launchInContainer(WeatherFragment.class,null,R.style.Theme_Weather);
        activityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                navController.setGraph(R.navigation.nav_graph);
                NavigationUI.setupWithNavController(bottomNavigation, navController);
                navController.navigate(R.id.tomorrowWeatherFragment);
                Log.d("WeatherResponse" , " current destination " + navController.getCurrentDestination().getDisplayName());
            }
        });
        Log.d("WeatherResponse" , " view is formed  ");

        assertEquals(navController.getCurrentDestination().getLabel(),activity.getResources().getString(R.string.tomorrow_weather_fragment_label));

    }


}
