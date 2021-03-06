package ca.mcgill.ecse428.restoguys.connoisseur.viewcontroller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yelp.clientlib.entities.Business;

import java.util.List;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.ApplicationData;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.Persistance;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.RestaurantWithDecision;
import ca.mcgill.ecse428.restoguys.connoisseur.viewadapter.ListViewAdapterBusinesses;

/**
 * Class that controls logic for history screen
 */
public class History extends ActionBarActivity {

    /** Instance Variables */
    private ListView listview;

    /**
     * Method that is called on creation of screen, it populate the list view
     * @param savedInstanceState
     * state passed to screen
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set instance variables
        listview = (ListView) findViewById(R.id.listView2);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get current restaurant being displayed
                Intent intent = new Intent(getApplicationContext(),RestaurantDetails.class);

                RestaurantWithDecision restaurantWithDecision = (RestaurantWithDecision)parent.getItemAtPosition(position);
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

        // Populate required screen elements
        populateListView();

    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is removed from top-stack (ie. user stops using it)
        // save all data to file.
        Persistance.saveState(this);
    }

    /**
     * Populates listview on screen with items in ApplicationData history.
     */
    private void populateListView () {

        listview.setAdapter(new ListViewAdapterBusinesses(
                this,
                ApplicationData.getInstance().getListHistory()
        ));

    }

    /**
     * function called when an item is clicked in the list
     * @param view
     * The list item clicked
     *
     */
    public void openRestoinfo(View view) {
        TextView t = (TextView) view;
        CharSequence resto = t.getText();
        new AlertDialog.Builder(this)
                .setTitle("Resto Info")
                .setMessage(resto)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with something
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

}
