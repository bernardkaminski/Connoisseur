package ca.mcgill.ecse428.restoguys.connoisseur;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.RestaurantDetails;
import ca.mcgill.ecse428.restoguys.connoisseur.RestaurantSelection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class HistoryEspressoTest {
    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<History> mActivityRule = new ActivityTestRule<>(History.class);

    @Test
    public void checkViewsTest() {
        // Test the adapter view.
        onData(withId(R.id.text1))
                .check(matches(isDisplayed()));


    }
}

