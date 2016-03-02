package ca.mcgill.ecse428.restoguys.connoisseur.yelpAPI;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import ca.mcgill.ecse428.restoguys.connoisseur.R;
import retrofit.Call;

public class Yelper extends AsyncTask<Void,Void,ArrayList<Business>> {

	/**
	 * Constants: YelpSearch API Credentials
	 */
	private static final String CONSUMER_KEY = "3Jc_WfUzhDWVbafvCJ_pHg";
	private static final String CONSUMER_SECRET = "qUHC2DJBzA0kFB8KsWxXnmSGR-A";
	private static final String TOKEN = "ouLis4eSzttX528_69cn3_yZu41q2hVH";
	private static final String TOKEN_SECRET = "mc1Uuqrp3DhK11SvEgdq3k3v6mc";

	/**
	 * Instance Variables
	 */
	private ListView listview;
	private Context context;

	/**
	 * Constructor, bitch.
	 * @param givenListView ListView to populate.
	 * @param context Method call context.
	 */
	public Yelper (ListView givenListView, Context context) {
		this.listview = givenListView;
		this.context = context;
	}

	@Override
	protected ArrayList doInBackground(Void...voids) {

		YelpAPIFactory apiFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
		YelpAPI yelpAPI = apiFactory.createAPI();
		Map<String, String> params = new HashMap<>();
		ArrayList<Business> businesses = new ArrayList<Business>();
		String businessName = "";

		// general params
		params.put("term", "food");
		params.put("limit", "11");

		// locale params
		params.put("lang", "fr");

		Call<SearchResponse> call = yelpAPI.search("Montreal", params);
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
		List<Business> businessList = businesses;
		ArrayList<String> businessNames = new ArrayList ();
		for(Business business : businessList) {
			businessNames.add(business.name());
		}
		// This is the array adapter, it takes the context of the activity as a
		// first parameter, the type of list view as a second parameter and your
		// array as a third parameter.
//		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//				context,
//				R.layout.activity_listview,
//				businessNames);
//
//		listview.setAdapter(arrayAdapter);
	}
}