package ca.mcgill.ecse428.restoguys.connoisseur.viewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yelp.clientlib.entities.Business;

import java.util.List;

import ca.mcgill.ecse428.restoguys.connoisseur.R;

/**
 * Adapter for displaying a list of businesses in a list view.
 */
public class ListViewAdapterBusinesses extends ArrayAdapter<Business>{

    private final Context context;

    // Default Constructor.
    // Takes context, array of Contacts, and sets the layoutContext to 0 (DEFAULT).
    public ListViewAdapterBusinesses (Context context, List<Business> listBusinesses) {
        super(context, R.layout.listview_business_item, listBusinesses);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflate the individual team row item layout for AlertDialogs.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View listItem = inflater.inflate(R.layout.listview_business_item, parent, false);

        // Retrieve the team name and set it to the text of the textview in the list row.
        ((TextView) listItem.findViewById(R.id.listview_business_item_restaurant_name)).setText(getItem(position).name());

        // Return the contact row view.
        return listItem;

    }

}