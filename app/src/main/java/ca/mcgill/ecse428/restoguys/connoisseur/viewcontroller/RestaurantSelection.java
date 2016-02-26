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
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.yelpAPI.Yelp;

public class RestaurantSelection extends ActionBarActivity implements
		GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
	private GoogleApiClient mGoogleApiClient;
	private Location mLastLocation;
	private LocationRequest mLocationRequest;
	private int radius;

	private ArrayList<Business> businesses = new ArrayList<Business>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_selection);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		createGoogleAPIClient();

		// Create the LocationRequest object
		mLocationRequest = LocationRequest.create()
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
				.setInterval(10 * 1000)        // 10 seconds, in milliseconds
				.setFastestInterval(1 * 1000); // 1 second, in milliseconds

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
//		businesses = Yelp.search("11",mLastLocation,radius);
//		TextView name = (TextView)findViewById(R.id.activity_restaurant_selection_current_restaurant_name);
//		name.setText(businesses.get(0).name());
//	}
	@Override
	protected void onResume() {
		super.onResume();
		TextView name = (TextView)findViewById(R.id.activity_restaurant_selection_current_restaurant_name);
		name.setText("Loading");
		Intent intent = getIntent();
		radius = intent.getIntExtra("radius",10000);
		mGoogleApiClient.connect();


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
		mGoogleApiClient.disconnect();
		super.onStop();
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
		Intent intent = new Intent(this, RestaurantDetails.class);
		startActivity(intent);
	}

	public void onAccept(View view)
	{

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
			Yelp yelp= new Yelp("11",mLastLocation,radius);
			try {

				businesses = yelp.execute().get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			if(businesses.size()!=0)
			{
				TextView name = (TextView)findViewById(R.id.activity_restaurant_selection_current_restaurant_name);
				name.setText(businesses.get(0).name());
			}

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
