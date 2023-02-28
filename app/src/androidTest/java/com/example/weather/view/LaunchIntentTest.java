package com.example.weather.view;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.weather.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LaunchIntentTest {
    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void testLaunchIntent(){
        String input = "Sunnyvale";
        // find view to enter text
        onView(withId(R.id.input_text)).perform(typeText(input));
        // click the search icon
        ViewInteraction startIcon = onView(withContentDescription(R.string.search_icon))
                .perform(ViewActions.click());
        // find view that displays results
        onView(withId(R.id.weather_details_layout)).perform(click());
        // Verify that the correct intent was sent
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse("https://openweathermap.org/city/5400075"))
        ));
        // Return to the original activity
        intending(hasComponent(MainActivity.class.getName())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

}
