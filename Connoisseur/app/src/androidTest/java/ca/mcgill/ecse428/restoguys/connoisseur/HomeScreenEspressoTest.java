package androidTest.java.ca.mcgill.ecse428.restoguys.connoisseur;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeScreenEspressoTest {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<homescreen> mActivityRule = new ActivityTestRule<>(homescreen.class);

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
        onView(withId(R.id.dummy_button))
                .perform(replaceText(mStringToBetyped))
                .check(matches(withText(mStringToBetyped)));

        // Check Options to be displayed.
        onView(withId(R.id.Cost))
                .perform(click())
                .check(matches(isDisplayed()));

        onView(withId(R.id.Distance))
                .perform(click())
                .check(matches(isDisplayed()));

        onView(withId(R.id.Type))
                .perform(click())
                .check(matches(isDisplayed()));
    }
}
