package com.example.weather.view;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.pm.ActivityInfo;
import android.os.IBinder;
import android.view.View;
import android.view.WindowManager;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Root;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.weather.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class WeatherFragmentTest {
    private View decorView;
    private MainActivity activity;
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
         activity = activityRule.getActivity();
         decorView = activity.getWindow().getDecorView();
//        activityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
//            @Override
//            public void perform(MainActivity activity) {
//                decorView = activity.getWindow().getDecorView();
//            }
//        });
    }

    @Test
    public void checkIfSearchStringMatchesResponseCityName(){
        String input = "Sunnyvale";
        onView(withId(R.id.input_text)).perform(typeText(input));
        ViewInteraction textInputLayout = onView(withId(R.id.textInputLayout));
        //       Find the startIcon within the TextInputLayout and click it
        ViewInteraction startIcon = onView(withContentDescription(R.string.search_icon))
                .perform(ViewActions.click());

        // assert if the displayed city name matched with the input
        onView(withId(R.id.city_name)).check(matches(withText(input)));
    }

    @Test
    public void clearButtonTest(){
        String input = "Sunnyvale";
        onView(withId(R.id.input_text)).perform(typeText(input));
        ViewInteraction textInputEditText = onView(withId(R.id.input_text));
        //       Find the startIcon within the TextInputLayout and click it
        ViewInteraction clearIcon = onView(withContentDescription(R.string.clear))
                .perform(ViewActions.click());
        textInputEditText.check(matches(withText("")));

    }

    @Test
    public void testConfigurationChanges()
    {
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);
        String input = "Sunnyvale";
        onView(withId(R.id.input_text)).perform(typeText(input));
        ViewInteraction textInputEditText = onView(withId(R.id.input_text));
        scenario.onActivity(activity -> {
            // Change screen orientation
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });
        textInputEditText.check(matches(isDisplayed()));
    }

    @Test
    public void validateToastMessage(){

        String input = "dsdj,kdhaskj";
        onView(withId(R.id.input_text)).perform(typeText(input));

        ViewInteraction startIcon = onView(withContentDescription(R.string.search_icon))
                .perform(ViewActions.click());

        // Find the toast message by its text
        String toastText = activity.getResources().getString(R.string.address_not_found);
        Matcher<Root> toastMatcher = isToast();
//        onView(withText(toastText)).inRoot(toastMatcher).withDecorView(
//                Matchers.is(activity.getWindow().getDecorView())).check(matches(isDisplayed()));
//        assertEquals(toastText, ShadowToast.getTextOfLatestToast());
    //    onView(withText(toastText)).inRoot(RootMatchers.withDecorView(is(decorView))).check(matches(isDisplayed()));
    }




    public Matcher<Root> isToast() {
        return new TypeSafeMatcher<Root>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("is toast");
            }

            @Override
            public boolean matchesSafely(Root root) {
                int type = root.getWindowLayoutParams().get().type;
                if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                    IBinder windowToken = root.getDecorView().getWindowToken();
                    IBinder appToken = root.getDecorView().getApplicationWindowToken();
                    if (windowToken == appToken) {
                        // This means the window isn't contained by any other windows.
                        return true;
                    }
                }
                return false;
            }
        };
    }

    //Test if the Toast Message is Displayed
//    @Test
//    public void testValidToast() {
//
//
//        String input = "Sunnyvale";
//        onView(withId(R.id.input_text)).perform(typeText(input));
//        ViewInteraction textInputLayout = onView(withId(R.id.textInputLayout));

//       Find the startIcon within the TextInputLayout and click it
//        ViewInteraction startIcon = onView(withContentDescription(R.string.search_icon))
//                .perform(ViewActions.click());
//
//        // assert if the displayed city name matched with the input
//       onView(withId(R.id.city_name)).check(matches(withText(input)));

//        onView(withText(R.string.address_not_found))
//                .inRoot(withDecorView(Matchers.is(decorView))).
//                check(matches(isDisplayed()));

//        onView(withText("Address not Found. Please enter a valid Address!"))
//                .inRoot(withDecorView(Matchers.not(decorView)))// Here we use decorView
//                .check(matches(isDisplayed()));
//    }
    //Test if the Toast Message is not Displayed
//    @Test
//    public void testToastNotDisplayed() {
//        // Given that no Plants are added to the user's garden
//        // When the "Add Plant" button is clicked(Yellow Button)
//        onView(withId(R.id.add_plant)).perform(click());
//        //Verify Toast is Displayed After clicking the Plant List Fragment
//        onView(withText("Your Toast"))
//                .inRoot(withDecorView(Matchers.not(decorView)))// Here we use decorView
//                .check(matches(not(isDisplayed())));
//    }

}
