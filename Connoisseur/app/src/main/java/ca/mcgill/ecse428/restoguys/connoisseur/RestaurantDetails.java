package ca.mcgill.ecse428.restoguys.connoisseur;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;

public class RestaurantDetails extends ActionBarActivity {
	private static final String CONSUMER_KEY = "3Jc_WfUzhDWVbafvCJ_pHg";
	private static final String CONSUMER_SECRET = "qUHC2DJBzA0kFB8KsWxXnmSGR-A";
	private static final String TOKEN = "ouLis4eSzttX528_69cn3_yZu41q2hVH";
	private static final String TOKEN_SECRET = "mc1Uuqrp3DhK11SvEgdq3k3v6mc";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_details);
		Yelper yelp = new Yelper();
		yelp.execute();
	}

	private class Yelper extends AsyncTask <Void,Void,ArrayList<Business>>{
		ListView lv = (ListView) findViewById(R.id.listView2);
		@Override
		protected ArrayList doInBackground(Void...voids) {

			YelpAPIFactory apiFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
			YelpAPI yelpAPI = apiFactory.createAPI();
			Map<String, String> params = new HashMap<>();
			ArrayList<Business> businesses = new ArrayList<Business>();
			String businessName = "";
// general params
			params.put("term", "food");
			params.put("limit", "3");

// locale params
			params.put("lang", "fr");

			Call<SearchResponse> call = yelpAPI.search("New York", params);
			try {
				SearchResponse response = call.execute().body();
				businesses = response.businesses();
				businessName = businesses.get(0).name();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return businesses;
		}


		protected void onPostExecute(ArrayList<Business>businesses) {
			List<Business> businessList = (List)businesses;
			// This is the array adapter, it takes the context of the activity as a
			// first parameter, the type of list view as a second parameter and your
			// array as a third parameter.
			ArrayAdapter<Business> arrayAdapter = new ArrayAdapter<Business>(
					RestaurantDetails.this,
					android.R.layout.simple_list_item_1,
					businessList);

			lv.setAdapter(arrayAdapter);
		}
	}
}
