package ca.mcgill.ecse428.restoguys.connoisseur;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.History;
import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.RestaurantDetails;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;


@RunWith(AndroidJUnit4.class)
public class RestaurantDetailsEspressoIntent {

    @Rule
    public ActivityTestRule<History> mActivityRule = new ActivityTestRule<>(History.class);

    @Test
    public void testValidateResSelection() throws Exception{

        Intents.init();
        // User action that results in an external "phone" activity being launched.
        onData(anything()).inAdapterView(withId(R.id.listView2)).atPosition(0).perform(click());


        // Using a canned RecordedIntentMatcher to validate that an intent resolving
        intended(hasComponent(RestaurantDetails.class.getName()));

        Intents.release();
    }

}

