package ca.mcgill.ecse428.restoguys.connoisseur;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeScreen extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_homescreen);

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
