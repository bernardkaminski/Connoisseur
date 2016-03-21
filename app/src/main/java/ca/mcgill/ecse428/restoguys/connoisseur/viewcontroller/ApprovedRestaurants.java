package ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.ApplicationData;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.Persistance;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.RestaurantWithDecision;
import ca.mcgill.ecse428.restoguys.connoisseur.viewadapter.ListViewAdapterBusinesses;
import ca.mcgill.ecse428.restoguys.connoisseur.viewadapter.ListViewAdapterBusinessesWithDelete;

public class ApprovedRestaurants extends ActionBarActivity {

    /** Instance Variables */
    private ListView listview;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_restaurants);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Grab reference to screen elements
        listview = (ListView) findViewById(R.id.activity_approved_restaurants_listview_restaurants);
        errorText = (TextView) findViewById(R.id.activity_approved_restaurants_error_message);

        // Populate listview.
        populateListView();

        // Set onItemClickListener for listview
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Get current restaurant being displayed
                Intent intent = new Intent(getApplicationContext(),RestaurantDetails.class);

                RestaurantWithDecision restaurantWithDecision = (RestaurantWithDecision) parent.getItemAtPosition(position);
                // Pass restaurant information to the restaurant details activity
                intent.putExtra("restaurantName", restaurantWithDecision.getRestaurant().name());
                intent.putExtra("businessStreetAddress", restaurantWithDecision.getRestaurant().location().displayAddress().get(0)); // example street address: 235 Boulevard St-Jean
                intent.putExtra("businessRegionalAddress", restaurantWithDecision.getRestaurant().location().displayAddress().get(2)); // example regional address: Pointe-Claire, QC H9R 3J1
                intent.putExtra("restaurantDescription", restaurantWithDecision.getRestaurant().snippetText());
                intent.putExtra("restaurantImage", restaurantWithDecision.getRestaurant().imageUrl());

                // Transition to restaurant details screen
                startActivity(intent);
            }
        });

    }

    /**
     * creates the options menu
     * @param menu
     * @return true or false on weather action was successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_approved_restaurants, menu);
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
     * Populates listview on screen with items in ApplicationData approved list.
     */
    private void populateListView () {

        if (((ApplicationData.getInstance().getListApproved() == null) || (ApplicationData.getInstance().getListApproved().size() == 0))) {
            errorText.setText("You have no approved restaurants.");
            errorText.setVisibility(View.VISIBLE);
        }
        else {
            errorText.setVisibility(View.INVISIBLE);
            listview.setAdapter(new ListViewAdapterBusinessesWithDelete(
                    this,
                    ApplicationData.getInstance().getListApproved()
            ));
        }



    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is removed from top-stack (ie. user stops using it)
        // save all data to file.
        Persistance.saveState(this);
    }


}
