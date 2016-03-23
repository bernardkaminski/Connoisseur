package ca.mcgill.ecse428.restoguys.connoisseur;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.HomeScreen;
import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.RestaurantSelection;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.StringStartsWith.startsWith;


@RunWith(AndroidJUnit4.class)
public class EspressoIntentTests {

    @Rule
    public ActivityTestRule<HomeScreen> mActivityRule = new ActivityTestRule<>(HomeScreen.class);

    @Before
    public void closeDisclaimerPage() {
        onView(withText("OK")).perform(click());
        onView(withId(R.id.activity_homescreen_spinner_Distance))
                .perform(click());

        onData(hasToString(startsWith("35 km")))
                .perform(click());
    }

    @Test
    public void testValidateResSelection() throws Exception{

        Intents.init();
        // User action that results in an external "phone" activity being launched.
        onView(withId(R.id.activity_homescreen_searchButton)).perform(click());

        // Using a canned RecordedIntentMatcher to validate that an intent resolving
        // to the "phone" activity has been sent.
        intended(hasComponent(RestaurantSelection.class.getName()));

        Intents.release();
    }

}

