package ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
//import ca.mcgill.ecse428.restoguys.connoisseur.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.Persistance;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.ApplicationData;
import ca.mcgill.ecse428.restoguys.connoisseur.yelpAPI.yelpSearchParameters;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeScreen extends ActionBarActivity {

	private Spinner spinnerOptionDistance;
	//private Spinner spinnerOptionCost;
	private Spinner spinnerOptionRestaurantType;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_homescreen);

		populateSpinners();

		checkGeoTurnedOn();
		// Load all save-data.
		Persistance.loadState(this);
		//check to see if user clicked ok when instructions were first displayed
		if(!ApplicationData.getInstance().getViewedInstructions())
			showNewUserInstructions();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// When the activity is removed from top-stack (ie. user stops using it)
		// save all data to file.
		Persistance.saveState(this);
	}

	/**
	 * Populate homescreen spinners.
	 */
	private void populateSpinners () {

		// Grab all the views

		spinnerOptionDistance       = (Spinner) findViewById(R.id.activity_homescreen_spinner_Distance);
		//spinnerOptionCost           = (Spinner) findViewById(R.id.activity_homescreen_spinner_Cost);
		spinnerOptionRestaurantType = (Spinner) findViewById(R.id.activity_homescreen_spinner_RestaurantType);

		// Use default spinner adapters to populate each spinner with array from strings.xml

		ArrayAdapter<CharSequence> adapterSpinnerDistance = ArrayAdapter.createFromResource(
				this,
				R.array.homescreen_array_options_distance, android.R.layout.simple_spinner_item
		);
		adapterSpinnerDistance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerOptionDistance.setAdapter(adapterSpinnerDistance);

//		ArrayAdapter<CharSequence> adapterSpinnerCost = ArrayAdapter.createFromResource(
//				this,
//				R.array.homescreen_array_options_food_cost, android.R.layout.simple_spinner_item
//		);
//		adapterSpinnerCost.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		//spinnerOptionCost.setAdapter(adapterSpinnerCost);

		ArrayAdapter<CharSequence> adapterSpinnerRestaurantType = ArrayAdapter.createFromResource(
				this,
				R.array.homescreen_array_options_restaurant_type, android.R.layout.simple_spinner_item
		);
		adapterSpinnerRestaurantType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerOptionRestaurantType.setAdapter(adapterSpinnerRestaurantType);

	}


	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

	}

	/** Called when the user clicks the history button */
	public void sendHistory(View view) {
		// Do something in response to button
		Intent intent = new Intent(this, History.class);
		String message = "testing history";
		intent.putExtra("main.history", message);
		startActivity(intent);

	}

	/**
	 * Opens Restaurant Selection Screen
	 * @param view
	 */
	public void goToRestaurantSelection(View view) {

		// Grab User Search Parameter: Search Radius
		String selectedDistanceString = spinnerOptionDistance.getSelectedItem().toString();
		Pattern startOfResultXML = Pattern.compile(" km");
        Matcher resultMatcher = startOfResultXML.matcher(selectedDistanceString);
		selectedDistanceString = resultMatcher.replaceAll("");
		int searchRadius = Integer.parseInt(selectedDistanceString) * 1000;

		// Grab User Search Parameter:

		// Grab User Search Parameter: Restaurant Type
		String selectedRestaurantTypeString = spinnerOptionRestaurantType.getSelectedItem().toString();
		String selectedRestaurantType = yelpSearchParameters.generateCategoryFilter(selectedRestaurantTypeString);

		// Create new searchIntent and move to restaurant selection activity.
		Intent searchIntent = new Intent(this, RestaurantSelection.class);
		searchIntent.putExtra("radius",searchRadius);
		searchIntent.putExtra("restauranttype", selectedRestaurantType);
		startActivity(searchIntent);
	}

	private void checkGeoTurnedOn() {
		LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		boolean geoTurnedOn = false;
		boolean networkTurnedOn = false;

		try {
			geoTurnedOn = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch(Exception ex) {Log.d("Test","GPS not turned on");}

		try {
			networkTurnedOn = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch(Exception ex) {Log.d("Test","Data not turned on");}

		if(!geoTurnedOn && !networkTurnedOn) {
			// notify user
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage("Seems like your geolocation is turned off. Would you like to turn it on?");
			//dialog.setMessage(this.getResources().getString(R.string.gps_network_not_enabled));
			dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface paramDialogInterface, int paramInt) {
					// TODO Auto-generated method stub
					Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					HomeScreen.this.startActivity(myIntent);
					//get gps
				}
			});
			dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface paramDialogInterface, int paramInt) {
					// TODO Auto-generated method stub

				}
			});
			dialog.show();
		}

	}

	private void showNewUserInstructions() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("User Instructions");
		dialog.setMessage("Select your preferred radius and type of cuisine then hit 'Search'.\n\nHit 'Yes, Looks Good!' for restaurants that pique your interest and 'No Thanks, Next!' for those that don't. You can keep doing this until no more restaurants are displayed.\n\nYou can always go back to your 'History' to see your recently viewed restaurants or your 'Approved' list to see restaurants you've liked.\n");
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface paramDialogInterface, int paramInt) {
				// TODO Auto-generated method stub
				ApplicationData.getInstance().setViewedInstructionsToTrue();
			}
		});

		dialog.show();
		}
}
