package ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.ApplicationData;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.Persistance;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.RestaurantWithDecision;
import ca.mcgill.ecse428.restoguys.connoisseur.yelpAPI.YelpSearch;
import ca.mcgill.ecse428.restoguys.connoisseur.yelpAPI.taskLoadImage;

public class RestaurantSelection extends ActionBarActivity implements
		GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

	/** Instance Variables: UI */
	private TextView textViewRestaurantName;
	private TextView textViewRestaurantDistance;
	private ImageButton imageButtonRestaurantImage;
	private TextView textViewNoMoreResults;
	private Button buttonAcceptRestaurant;
	private Button buttonRejectRestaurant;

	/** Instance Variables: */
	private GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;
	private LocationRequest mLocationRequest;
	private int radius;
	private String restaurantType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_selection);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		createGoogleAPIClient();

		// Set the UI instance variables
		textViewRestaurantName = (TextView) findViewById(R.id.activity_restaurant_selection_current_restaurant_name);
		textViewRestaurantDistance = (TextView) findViewById(R.id.activity_restaurant_selection_current_restaurant_distance);
		imageButtonRestaurantImage = (ImageButton) findViewById(R.id.activity_restaurant_selection_current_restaurant_imagebutton);
		textViewNoMoreResults = (TextView) findViewById(R.id.activity_restaurant_selection_promptNoMoreSearchResults);
		buttonAcceptRestaurant = (Button) findViewById(R.id.activity_restaurant_selection_buttonAccept);
		buttonRejectRestaurant = (Button) findViewById(R.id.activity_restaurant_selection_buttonReject);

		// Set the UI to visible.
		setViewToUserSwiping();

		// Create the LocationRequest object
		mLocationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(10 * 1000)        // 10 seconds, in milliseconds
				.setFastestInterval(1 * 1000); // 1 second, in milliseconds

		// Load search results
		Intent intent = getIntent();
		radius = intent.getIntExtra("radius", 10000);
		restaurantType = intent.getStringExtra("restauranttype");
		mGoogleApiClient.connect();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_restaurant_selection, menu);
		return true;
	}
//	@Override
//	protected void onStart() {
//		super.onStart();
//		Intent intent = getIntent();
//		int radius = intent.getIntExtra("radius",10000);
//		businesses = YelpSearch.search("11",mLastLocation,radius);
//		TextView name = (TextView)findViewById(R.id.activity_restaurant_selection_current_restaurant_name);
//		name.setText(businesses.get(0).name());
//	}
	@Override
	protected void onResume() {
		super.onResume();

		// Note from Nicolas:
		// To whoever made it reload results every time the activity resumed:
		// Don't do that. onResume() encompasses a lot of stuff, so every time the user
		// goes to check history and comes back, it reloads! We don't want that. We want it to only
		// reload/load when it comes from the HomeScreen activity. That is: when it is created!

	}
	@Override
	protected void onPause() {
		super.onPause();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		mGoogleApiClient.disconnect();
		Persistance.saveState(this);
	}
	private void createGoogleAPIClient()
	{
		if (mGoogleApiClient == null) {
			mGoogleApiClient = new GoogleApiClient.Builder(this)
					.addConnectionCallbacks(this)
					.addOnConnectionFailedListener(this)
					.addApi(LocationServices.API)
					.build();
		}

	}

	/**
	 * Launches history view.
	 * @param item the menu item that called it
	 */
	public void goToHistory(MenuItem item) {
		Intent intent = new Intent(this, History.class);
		startActivity(intent);
	}

	/**
	 * Launches restaurant details view.
	 * @param view
	 */
	public void goToRestaurantDetails(View view) {
		// Get current restaurant being displayed
		Intent intent = new Intent(this, RestaurantDetails.class);
		List<Business> currentSearchList = ApplicationData.getInstance().getListCurrentSearch();

		// Add restaurant information to the restaurant selection screen
		intent.putExtra("restaurantName", currentSearchList.get(0).name());
		intent.putExtra("businessStreetAddress", currentSearchList.get(0).location().displayAddress().get(0)); // example street address: 235 Boulevard St-Jean
		intent.putExtra("businessRegionalAddress", currentSearchList.get(0).location().displayAddress().get(2)); // example regional address: Pointe-Claire, QC H9R 3J1
		intent.putExtra("restaurantDescription", currentSearchList.get(0).snippetText());
		intent.putExtra("restaurantImage", currentSearchList.get(0).imageUrl());

		// Transition to restaurant details screen
		startActivity(intent);
	}

	public void onAccept(View view)
	{

		// Grab the list of items from the latest search (list starts with business currently displayed).
		List<Business> currentSearchList = ApplicationData.getInstance().getListCurrentSearch();

		// Add the current business to the history.
		ApplicationData.getInstance().addBusinessToHistory(
				currentSearchList.get(0),
				true								// true since accepted
		);

		// Now remove the index 0 item from the current search list and set the new top item to currently viewable
		// restaurant in restaurant selection
		currentSearchList.remove(0);
		if (currentSearchList.size() == 0) {
			setViewToNoMoreResults("No more search results.");
			return;
		}
		Business newTop = currentSearchList.get(0);
		textViewRestaurantName.setText(newTop.name());
		textViewRestaurantDistance.setText("" + (Math.round(newTop.distance())));
		(new taskLoadImage(
				imageButtonRestaurantImage,
				newTop.imageUrl()
		)).execute();

		// TODO change to immediately show restaurant?

	}

	/**
	 * User selects to reject the current restaurant.
	 */
	public void onReject (View view) {

		// Grab the list of items from the latest search (list starts with business currently displayed).
		List<Business> currentSearchList = ApplicationData.getInstance().getListCurrentSearch();

		// Add the current business to the history.
		ApplicationData.getInstance().addBusinessToHistory(
				currentSearchList.get(0),
				false								// false since rejected
		);

		// Now remove the index 0 item from the current search list and set the new top item to currently viewable
		// restaurant in restaurant selection
		currentSearchList.remove(0);
		if (currentSearchList.size() == 0) {
			setViewToNoMoreResults("No more search results.");
			return;
		}
		Business newTop = currentSearchList.get(0);
		textViewRestaurantName.setText(newTop.name());
		textViewRestaurantDistance.setText("" + (Math.round(newTop.distance())));
		(new taskLoadImage(
				imageButtonRestaurantImage,
				newTop.imageUrl()
		)).execute();

	}

	/**
	 * Sets the UI elements for swiping to visible.
	 */
	private void setViewToUserSwiping () {
		textViewRestaurantName.setVisibility(View.VISIBLE);
		textViewRestaurantDistance.setVisibility(View.VISIBLE);
		imageButtonRestaurantImage.setVisibility(View.VISIBLE);
		buttonAcceptRestaurant.setVisibility(View.VISIBLE);
		buttonRejectRestaurant.setVisibility(View.VISIBLE);
		textViewNoMoreResults.setVisibility(View.INVISIBLE);
	}

	/**
	 * Sets the UI elements for swiping to invisible and shows prompt stating
	 * no more search results.
	 */
	private void setViewToNoMoreResults (String messageToDisplay) {
		textViewRestaurantName.setVisibility(View.INVISIBLE);
		textViewRestaurantDistance.setVisibility(View.INVISIBLE);
		imageButtonRestaurantImage.setVisibility(View.INVISIBLE);
		buttonAcceptRestaurant.setVisibility(View.INVISIBLE);
		buttonRejectRestaurant.setVisibility(View.INVISIBLE);

		textViewNoMoreResults.setText(messageToDisplay);
		textViewNoMoreResults.setVisibility(View.VISIBLE);
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
				mGoogleApiClient);
		if (mLastLocation == null) {

			LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,mLocationRequest,this);
		}
		if(mLastLocation != null)
		{
			YelpSearch yelpSearch = new YelpSearch(
					textViewRestaurantName, textViewRestaurantDistance, imageButtonRestaurantImage,
					mLastLocation,radius, restaurantType
			);

			yelpSearch.execute();

		}
	}
	@Override
	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}

	@Override
	public void onLocationChanged(Location location) {
		mLastLocation = location;
	}
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this, 9000);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
			Log.i("LOCATION", "Location services connection failed with code " + connectionResult.getErrorCode());
		}
	}
}
