package ca.mcgill.ecse428.restoguys.connoisseur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.Business;
import com.yelp.clientlib.entities.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;

/**
 * Class that controls logic for history screen
 */
public class History extends ActionBarActivity {
    private static final String CONSUMER_KEY = "3Jc_WfUzhDWVbafvCJ_pHg";
    private static final String CONSUMER_SECRET = "qUHC2DJBzA0kFB8KsWxXnmSGR-A";
    private static final String TOKEN = "ouLis4eSzttX528_69cn3_yZu41q2hVH";
    private static final String TOKEN_SECRET = "mc1Uuqrp3DhK11SvEgdq3k3v6mc";

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
        Intent intent = getIntent();
        Yelper yelp = new Yelper();
        yelp.execute();
        //ListView listView = (ListView) findViewById(R.id.listView);
        //have list here for now just to display something , later will be retrieved from db or file
//        String[] restaurantArray = {"Resto1","Resto2","Resto3"};
//        ArrayAdapter adapter = new ArrayAdapter<String>(this,  R.layout.activity_listview, restaurantArray);
//        listView.setAdapter(adapter);


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

    private class Yelper extends AsyncTask<Void,Void,ArrayList<Business>> {
        ListView lv = (ListView) findViewById(R.id.listView2);
        @Override
        protected ArrayList doInBackground(Void...voids) {

            YelpAPIFactory apiFactory = new YelpAPIFactory(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
            YelpAPI yelpAPI = apiFactory.createAPI();
            Map<String, String> params = new HashMap<>();
            ArrayList<Business> businesses = new ArrayList<Business>();
            String businessName = "";
// general params
            params.put("term", "food");
            params.put("limit", "11");

// locale params
            params.put("lang", "fr");

            Call<SearchResponse> call = yelpAPI.search("Montreal", params);
            try {
                SearchResponse response = call.execute().body();
                businesses = response.businesses();
                //businessName = businesses.get(0).name();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return businesses;
        }


        protected void onPostExecute(ArrayList<Business>businesses) {
            List<Business> businessList = businesses;
            ArrayList<String> businessNames = new ArrayList ();
            for(Business business : businessList) {
                businessNames.add(business.name());
            }
            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    History.this,
                    R.layout.activity_listview,
                    businessNames);

            lv.setAdapter(arrayAdapter);
        }
    }

}
