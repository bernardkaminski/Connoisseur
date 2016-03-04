package ca.mcgill.ecse428.restoguys.connoisseur.yelpAPI;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.ApplicationData;
import retrofit.Call;

/**
 * ASyncTask used to generate and execute a call to Yelp Server to retrieve list of restaurants
 * based on user's inputted search parameters. Saves list to ApplicationData.listCurrentSearch
 * and attempts to update relevant screen elements.
 */
public class YelpSearch extends AsyncTask<Void,Void,ArrayList<Business>> {

    private static final String CONSUMER_KEY = "3Jc_WfUzhDWVbafvCJ_pHg";
    private static final String CONSUMER_SECRET = "qUHC2DJBzA0kFB8KsWxXnmSGR-A";
    private static final String TOKEN = "ouLis4eSzttX528_69cn3_yZu41q2hVH";
    private static final String TOKEN_SECRET = "mc1Uuqrp3DhK11SvEgdq3k3v6mc";

    /** Instance Variables: UI Elements needing immediate population */
    private TextView textViewFirstRestaurantName;
    private TextView textViewFirstRestaurantDistance;
    private ImageButton imageButtonFirstRestaurantImageButton;

    /** Instances Variables: Search Parameters */
    private int searchRadius;
    private String restaurantType;
    private Location location;

    /** Constructor */
    public YelpSearch(TextView tvFirstRestaurantName, TextView tvFirstRestaurantDistance, ImageButton ibFirstRestaurantImageButton, Location location, int searchRadius, String restaurantType) {
        this.textViewFirstRestaurantName = tvFirstRestaurantName;
        this.textViewFirstRestaurantDistance = tvFirstRestaurantDistance;
        this.imageButtonFirstRestaurantImageButton = ibFirstRestaurantImageButton;
        this.location=location;
        this.searchRadius = searchRadius;
        this.restaurantType = restaurantType;
    }

    @Override
    protected ArrayList doInBackground(Void...voids)
    {

        // Create yelp search
        YelpAPIFactory yelpAPIFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
        YelpAPI yelpSearch = yelpAPIFactory.createAPI();
        Map<String, String> params = new HashMap<>();

        // Search Parameters: Non-User-Defined
        params.put("lang", "en");                                       // Set language to english (who needs french?)
        params.put("term", "food");                                     // Searching for restaurants
        params.put("limit", "20");                                      // Taking 20 restaurants (maximum by Yelp API)
        params.put("sort", "1");                                        // Sort returned results by distance.

        // Search Parameters: User-Defined
        params.put("radius_filter", Integer.toString(searchRadius));    // Parameter sent is in METERS.
        params.put("category_filter", restaurantType);                  // Parameter for restaurant type.
        // TODO: add third search parameter.

        // Grab user coordinates
        CoordinateOptions userCoordinates = CoordinateOptions.builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();

        // Send search out to server
        Call<SearchResponse> call = yelpSearch.search(userCoordinates, params);

        try {

            // If everything works out, return the list of businesses.
            SearchResponse response = call.execute().body();
            ArrayList<Business> businesses = new ArrayList<Business>();
            businesses = response.businesses();
            return businesses;

        } catch (IOException e) {

            // If something fucked up, return a null object.
            e.printStackTrace();
            return null;

        }

    }

    @SuppressLint("SetTextI18n")
    protected void onPostExecute(ArrayList<Business> businesses) {

        // If there are no businesses returned (due to error or something)
        // then reset the current data and return.
        if (businesses == null || businesses.size() == 0) {
            ApplicationData.getInstance().setListCurrentSearch(new ArrayList<Business>());
            return;
        }

        // Otherwise, set the current search list to this new one.
        ApplicationData.getInstance().setListCurrentSearch(businesses);

        // DEBUGGING
//        for (Business business : ApplicationData.getInstance().getListCurrentSearch()) {
//            Log.d("debug", "Restaurant: " + business.name() + "|| Distance: " + business.distance());
//        }

        // Populate the top stack restaurant parameters in RestaurantDetails
        Business topBusiness = ApplicationData.getInstance().getListCurrentSearch().get(0);
        textViewFirstRestaurantName.setText(topBusiness.name());
        textViewFirstRestaurantDistance.setText("" + (Math.round(topBusiness.distance())));

        taskLoadImage loadRestaurantImage = new taskLoadImage(
                imageButtonFirstRestaurantImageButton,
                topBusiness.imageUrl()
        );
        loadRestaurantImage.execute();

    }
}
