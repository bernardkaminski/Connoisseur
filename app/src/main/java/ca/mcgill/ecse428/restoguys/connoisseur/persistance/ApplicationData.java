package ca.mcgill.ecse428.restoguys.connoisseur.persistance;

import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a singleton class that stores the user history and search preferences.
 */
public class ApplicationData {

    /** Instance Variables */
    private List<RestaurantWithDecision> listHistory;
    private List<Business> listCurrentSearch;
    private static ApplicationData applicationData = null;

    /** Constructor */

    private ApplicationData()
    {
        listHistory = new ArrayList<RestaurantWithDecision>();
        listCurrentSearch = new ArrayList<Business>();
    }

    public static ApplicationData getInstance(){
        if(applicationData == null){
            applicationData = new ApplicationData();
        }
        return applicationData;
    }

    /**
     * Adds a business to the business history.
     * @param businessToAdd Business to be added.
     */
    public void addBusinessToHistory (Business businessToAdd, boolean decision) {

        RestaurantWithDecision newRestaurantToAdd = new RestaurantWithDecision(
                businessToAdd,
                decision
        );

        // Put the given business at index 0 (latest business)
        listHistory.add(0, newRestaurantToAdd);

        // Ensure the list is at 10 items.
        while (listHistory.size() > 10) {
            listHistory.remove(10);
        }

    }

    public void setApplicationData (ApplicationData givenAD) {
        applicationData = givenAD;
    }

    public List<RestaurantWithDecision> getListHistory() {
        return listHistory;
    }

    public void setListHistory(List<RestaurantWithDecision> listHistory) {
        this.listHistory = listHistory;
    }

    public List<Business> getListCurrentSearch() {
        return listCurrentSearch;
    }

    public void setListCurrentSearch(List<Business> listCurrentSearch) {
        this.listCurrentSearch = listCurrentSearch;
    }

}
