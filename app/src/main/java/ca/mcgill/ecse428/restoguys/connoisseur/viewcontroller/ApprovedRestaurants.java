package ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.ApplicationData;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.Persistance;
import ca.mcgill.ecse428.restoguys.connoisseur.viewadapter.ListViewAdapterBusinesses;

public class ApprovedRestaurants extends ActionBarActivity {

    /** Instance Variables */
    private ListView listview;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_restaurants);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        android:parentActivityName=".viewcontroller.RestaurantSelection">
//        <meta-data
//        android:name="android.support.PARENT_ACTIVITY"
//        android:value="ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller.RestaurantSelection"/>

        // Grab reference to screen elements
        listview = (ListView) findViewById(R.id.activity_approved_restaurants_listview_restaurants);
        errorText = (TextView) findViewById(R.id.activity_approved_restaurants_error_message);

        // Populate listview.
        populateListView();

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
            listview.setAdapter(new ListViewAdapterBusinesses(
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
