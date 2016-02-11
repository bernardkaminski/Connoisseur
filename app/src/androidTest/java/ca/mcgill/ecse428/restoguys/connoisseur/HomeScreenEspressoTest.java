package ca.mcgill.ecse428.restoguys.connoisseur;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.test.suitebuilder.annotation.LargeTest;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeScreenEspressoTest {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<HomeScreen> mActivityRule = new ActivityTestRule<>(HomeScreen.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "testText";
    }

    @Test
    public void checkViewsTest() {
        // Test Title to be displayed.
        onView(withId(R.id.Title))
                .perform(replaceText(mStringToBetyped))
                .check(matches(withText(mStringToBetyped)));

        //Test Search Button to be displayed
        onView(withId(R.id.activity_homescreen_layout_buttons))
                .perform(replaceText(mStringToBetyped))
                .check(matches(withText(mStringToBetyped)));

        // Check Options to be displayed.
        onView(withId(R.id.activity_homescreen_spinner_Cost))
                .perform(click())
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_homescreen_spinner_Distance))
                .perform(click())
                .check(matches(isDisplayed()));

        onView(withId(R.id.activity_homescreen_spinner_RestaurantType))
                .perform(click())
                .check(matches(isDisplayed()));
    }
}
