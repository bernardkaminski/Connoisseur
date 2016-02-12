package ca.mcgill.ecse428.restoguys.connoisseur;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.RestaurantDetails;
import ca.mcgill.ecse428.restoguys.connoisseur.RestaurantSelection;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RestaurantSelectionEspressoTest {

    @Rule
    public ActivityTestRule<RestaurantSelection> mActivityRule = new ActivityTestRule<>(RestaurantSelection.class);


    @Test
    public void checkViewsTest() {
        // Test The restaurant name to be displayed.
        onView(withId(R.id.activity_restaurant_selection_current_restaurant_name))
                .check(matches(isDisplayed()));
    }


    @Test
    public void checkViewsTest2() {
        //Test The restaurant description text change
        onView(withId(R.id.activity_restaurant_selection_current_restaurant_distance))
                .check(matches(isDisplayed()));
    }

}
