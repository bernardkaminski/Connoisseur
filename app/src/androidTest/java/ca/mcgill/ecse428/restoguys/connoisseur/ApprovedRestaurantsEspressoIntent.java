package ca.mcgill.ecse428.restoguys.connoisseur;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.ApprovedRestaurants;
import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.RestaurantDetails;
import ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.RestaurantSelection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class ApprovedRestaurantsEspressoIntent {

    @Rule
    public ActivityTestRule<RestaurantSelection> mActivityRule = new ActivityTestRule<>(RestaurantSelection.class);

    @Test
    public void testValidateResSelection() throws Exception{

        Intents.init();
        // User action that results in an external "phone" activity being launched.
        onView(withId(R.id.menu_restaurant_selection_approved)).perform(click());


        // Using a canned RecordedIntentMatcher to validate that an intent resolving
        intended(hasComponent(ApprovedRestaurants.class.getName()));

        Intents.release();
    }

}

