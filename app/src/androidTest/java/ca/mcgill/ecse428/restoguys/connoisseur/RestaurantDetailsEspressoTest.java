package ca.mcgill.ecse428.restoguys.connoisseur;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.RestaurantDetails;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RestaurantDetailsEspressoTest {

    @Rule
    public ActivityTestRule<RestaurantDetails> mActivityRule = new ActivityTestRule<>(RestaurantDetails.class);


    @Test
    public void checkViewsTest3() {
        //Test The button  to be displayed
        onView(withId(R.id.googleMaps))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkViewsTest4() {
        //Test The button to be displayed
        onView(withId(R.id.restaurantName))
                .check(matches(isDisplayed()));
    }




}
