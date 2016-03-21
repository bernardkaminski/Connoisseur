package ca.mcgill.ecse428.restoguys.connoisseur.viewadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse428.restoguys.connoisseur.R;
import ca.mcgill.ecse428.restoguys.connoisseur.persistance.RestaurantWithDecision;

/**
 * Adapter for displaying a listview of restaurants with delete buttons.
 */
public class ListViewAdapterBusinessesWithDelete extends ArrayAdapter<RestaurantWithDecision>{

    private final Context context;
    private List<RestaurantWithDecision> listRestaurants;

    /**
     * Default Constructor
     * Takes context, array of Contacts, and sets the layoutContext to 0 (DEFAULT).
     * @param context the context passed to it
     * @param listBusinesses The list of bissinesses that will be displayed
     */
    public ListViewAdapterBusinessesWithDelete (Context context, List<RestaurantWithDecision> listBusinesses) {
        super(context, R.layout.listview_business_item_with_delete, listBusinesses);
        this.context = context;
        this.listRestaurants = listBusinesses;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Inflate the individual team row item layout for AlertDialogs.
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View listItem = inflater.inflate(R.layout.listview_business_item_with_delete, parent, false);

        // Retrieve the resto name and set it to the text of the textview in the list row.
        ((TextView) listItem.findViewById(R.id.listview_business_item_with_delete_restaurant_name)).setText(getItem(position).getRestaurant().name());

        // Set up delete button.
        ((Button) listItem.findViewById(R.id.listview_business_item_with_delete_deletebutton)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listRestaurants.remove(position);
                        notifyDataSetChanged();
                    }
                });

        // Return the contact row view.
        return listItem;

    }
}
