package ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.Persistance;

public class RestaurantDetails extends ActionBarActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_details);

	}

	@Override
	protected void onStop() {
		super.onStop();
		// When the activity is removed from top-stack (ie. user stops using it)
		// save all data to file.
		Persistance.saveState(this);
	}

}