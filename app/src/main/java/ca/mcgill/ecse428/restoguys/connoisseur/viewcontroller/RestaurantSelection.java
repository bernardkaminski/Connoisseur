package ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ca.mcgill.ecse428.restoguys.connoisseur.R;

public class RestaurantSelection extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_selection);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_restaurant_selection, menu);
		return true;
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
}
