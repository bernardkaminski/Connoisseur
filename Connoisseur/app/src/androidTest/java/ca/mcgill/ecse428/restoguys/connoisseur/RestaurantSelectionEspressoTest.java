package androidTest.java.ca.mcgill.ecse428.restoguys.connoisseur;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.RestaurantDetails;
import ca.mcgill.ecse428.restoguys.connoisseur.RestaurantSelection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RestaurantSelectionEspressoTest {
    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<RestaurantSelection> mActivityRule = new ActivityTestRule<>(RestaurantDetails.class);

    @Before
    public void initValidString() {
        // A dummy test string.
        mStringToBetyped = "testText";
    }

    @Test
    public void checkViewsTest() {
        // Test The restaurant name to be displayed.
        onView(withId(R.id.restaurantName))
                .perform(replaceText(mStringToBetyped))
                .check(matches(withText(mStringToBetyped)));

        //Test The restaurant image  to be displayed
        onView(withId(R.id.restaurantImage))
                .check(matches(isDisplayed()));

        //Test The restaurant description text change
        onView(withId(R.id.text_description))
                .perform(replaceText(mStringToBetyped))
                .check(matches(withText(mStringToBetyped)));

        //Test The button  to be displayed
        onView(withId(R.id.googleMaps))
                .check(matches(isDisplayed()));

        //Test The button to be displayed
        onView(withId(R.id.accept))
                .check(matches(isDisplayed()));

        //Test button to be displayed
        onView(withId(R.id.reject))
                .check(matches(isDisplayed()));
    }
}
