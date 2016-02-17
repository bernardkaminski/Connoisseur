package ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import ca.mcgill.ecse428.restoguys.connoisseur.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeScreen extends ActionBarActivity {

	private Spinner spinnerOptionDistance;
	private Spinner spinnerOptionCost;
	private Spinner spinnerOptionRestaurantType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_homescreen);

		populateSpinners();

	}

	/**
	 * Populate homescreen spinners.
	 */
	private void populateSpinners () {

		// Grab all the views

		spinnerOptionDistance       = (Spinner) findViewById(R.id.activity_homescreen_spinner_Distance);
		spinnerOptionCost           = (Spinner) findViewById(R.id.activity_homescreen_spinner_Cost);
		spinnerOptionRestaurantType = (Spinner) findViewById(R.id.activity_homescreen_spinner_RestaurantType);

		// Use default spinner adapters to populate each spinner with array from strings.xml

		ArrayAdapter<CharSequence> adapterSpinnerDistance = ArrayAdapter.createFromResource(
				this,
				R.array.homescreen_array_options_distance, android.R.layout.simple_spinner_item
		);
		adapterSpinnerDistance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerOptionDistance.setAdapter(adapterSpinnerDistance);

		ArrayAdapter<CharSequence> adapterSpinnerCost = ArrayAdapter.createFromResource(
				this,
				R.array.homescreen_array_options_food_cost, android.R.layout.simple_spinner_item
		);
		adapterSpinnerCost.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerOptionCost.setAdapter(adapterSpinnerCost);

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
		Intent intent = new Intent(this, RestaurantSelection.class);
		startActivity(intent);
	}
}
