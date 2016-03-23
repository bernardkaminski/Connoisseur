package ca.mcgill.ecse428.restoguys.connoisseur;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.RestaurantSelection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RestaurantSelectionEspressoTest {

    @Rule
    public ActivityTestRule<RestaurantSelection> mActivityRule = new ActivityTestRule<>(RestaurantSelection.class);

    @Before
    public void likeRandomRestaurant() {
        onView(withId(R.id.activity_restaurant_selection_buttonAccept))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @Before
    public void dislikeRandomRestaurant() {
        // Test The restaurant name to be displayed.
        onView(withId(R.id.activity_restaurant_selection_buttonReject))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @Test
    public void likeRandomRestaurantTest() {
        onView(withId(R.id.activity_restaurant_selection_buttonAccept))
                .check(matches(isDisplayed()));
    }

    @Test
    public void dislikeRandomRestaurantTest() {
        // Test The restaurant name to be displayed.
        onView(withId(R.id.activity_restaurant_selection_buttonReject))
                .check(matches(isDisplayed()));
    }

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
