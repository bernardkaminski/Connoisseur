package ca.mcgill.ecse428.restoguys.connoisseur.yelpAPI;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;
import com.yelp.clientlib.entities.options.CoordinateOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import retrofit.Call;

/**
 * Created by Bernie on 24/02/2016.
 */
public class Yelp extends AsyncTask<Void,Void,ArrayList<Business>> {
    private static final String CONSUMER_KEY = "3Jc_WfUzhDWVbafvCJ_pHg";
    private static final String CONSUMER_SECRET = "qUHC2DJBzA0kFB8KsWxXnmSGR-A";
    private static final String TOKEN = "ouLis4eSzttX528_69cn3_yZu41q2hVH";
    private static final String TOKEN_SECRET = "mc1Uuqrp3DhK11SvEgdq3k3v6mc";
    private String limit;
    private Location location;
    private int searchRadius;



    public Yelp(String limit, Location location, int searchRadius) {
        this.limit=limit;
        this.location=location;
        this.searchRadius = searchRadius;
    }

    @Override
    protected ArrayList doInBackground(Void...voids)
    {

        double lat = location.getLatitude();
        double log = location.getLongitude();
        CoordinateOptions coordinate = CoordinateOptions.builder()
                .latitude(lat)
                .longitude(log).build();
        YelpAPIFactory apiFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
        YelpAPI yelpAPI = apiFactory.createAPI();
        Map<String, String> params = new HashMap<>();
        String businessName = "";

        // general params
        params.put("term", "food");
        params.put("limit", "11");
        params.put("radius_filter", Integer.toString(searchRadius));

        // locale params
        params.put("lang", "fr");
        ArrayList<Business> businesses = new ArrayList<Business>();
        Call<SearchResponse> call = yelpAPI.search(coordinate, params);
        try {
            SearchResponse response = call.execute().body();
            businesses = response.businesses();
            //businessName = businesses.get(0).name();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return businesses;

    }

    protected void onPostExecute(ArrayList<Business> businesses) {


    }
}
