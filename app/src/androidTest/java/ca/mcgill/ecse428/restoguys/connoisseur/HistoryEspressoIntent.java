package ca.mcgill.ecse428.restoguys.connoisseur;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.History;
import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.RestaurantDetails;
import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.RestaurantSelection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class HistoryEspressoIntent {

    @Rule
    public ActivityTestRule<RestaurantSelection> mActivityRule = new ActivityTestRule<>(RestaurantSelection.class);



    @Test
    public void testValidateHistory() throws Exception {

        Intents.init();
        // User action that results in an external "phone" activity being launched.
        onView(withId(R.id.menu_restaurant_selection_history)).perform(click());

        // Using a canned RecordedIntentMatcher to validate that an intent resolving
        // to the "phone" activity has been sent.
        intended(hasComponent(History.class.getName()));

        Intents.release();
    }

    @Test
    public void testValidateRestaurantDetails() throws Exception {

        Intents.init();
        // User action that results in an external "phone" activity being launched.
        onView(withId(R.id.activity_restaurant_selection_current_restaurant_imagebutton)).perform(click());

        // Using a canned RecordedIntentMatcher to validate that an intent resolving
        // to the "phone" activity has been sent.
        intended(hasComponent(RestaurantDetails.class.getName()));

        Intents.release();
    }
}

