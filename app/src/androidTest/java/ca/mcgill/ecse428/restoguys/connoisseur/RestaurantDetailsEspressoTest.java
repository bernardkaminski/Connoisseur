package ca.mcgill.ecse428.restoguys.connoisseur;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.RestaurantDetails;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
        onView(withId(R.id.accept))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkViewsTest5() {
        //Test button to be displayed
        onView(withId(R.id.reject))
                .check(matches(isDisplayed()));
    }




}
