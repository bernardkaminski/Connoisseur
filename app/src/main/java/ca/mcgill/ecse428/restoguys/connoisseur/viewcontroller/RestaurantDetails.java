package ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.Persistance;
import ca.mcgill.ecse428.restoguys.connoisseur.yelpAPI.taskLoadImage;

public class RestaurantDetails extends ActionBarActivity {


	/** Instance Variables: UI */
	private TextView textViewRestaurantName;
	private TextView description;
	private ImageButton restaurantImageView;

	private ImageButton googleMapsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant_details);

		textViewRestaurantName = (TextView) findViewById(R.id.restaurantName);
		description = (TextView) findViewById(R.id.text_description);
		restaurantImageView = (ImageButton) findViewById(R.id.restaurantImage);

		// Get restaurant information and load its name and description
		Intent intent = getIntent();
			textViewRestaurantName.setText(intent.getStringExtra("restaurantName"));
			description.setText(intent.getStringExtra("restaurantDescription"));
			(new taskLoadImage(
					restaurantImageView,
					intent.getStringExtra("restaurantImage")
			)).execute();

	}

	@Override
	protected void onStop() {
		super.onStop();
		// When the activity is removed from top-stack (ie. user stops using it)
		// save all data to file.
		Persistance.saveState(this);
	}

	public void showOnMaps(View view) {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		String businessRegionalAddress = extras.getString("businessRegionalAddress"); // city, province, postal code
		// uri conforms to the maps applications standard for a search query
		String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s", extras.getString("restaurantName") + " " + extras.getString("businessStreetAddress") + " " +businessRegionalAddress);
		Intent openMaps = new Intent(Intent.ACTION_VIEW, Uri.parse(uri)); // Intent.ACTION_VIEW says to use a maps application
		startActivity(openMaps);
	}

}