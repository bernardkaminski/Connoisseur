package ca.mcgill.ecse428.restoguys.connoisseur;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.HomeScreen;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.Matchers.containsString;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeScreenEspressoTest {

    @Rule
    public ActivityTestRule<HomeScreen> mActivityRule = new ActivityTestRule<>(HomeScreen.class);

    @Test
    public void checkViewsTest() {
        // Test Title to be displayed.
        onView(withId(R.id.activity_homescreen_app_title))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkViewsTest3() {
                onView(withId(R.id.activity_homescreen_spinner_Distance))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkViewsTest4() {
        onView(withId(R.id.activity_homescreen_spinner_RestaurantType))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkViewsTest5() {
        onView(withId(R.id.activity_homescreen_searchButton))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkViewsTest6() {
        onView(withId(R.id.activity_homescreen_helpButton))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkViewsTest7() {
        onView(withId(R.id.activity_homescreen_spinner_Distance)).check(matches(withSpinnerText(containsString("1 km"))));
    }

    @Test
    public void checkViewsTest8() {
        onView(withId(R.id.activity_homescreen_spinner_RestaurantType)).check(matches(withSpinnerText(containsString("American"))));
    }

}
