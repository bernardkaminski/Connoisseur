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
    private List<RestaurantWithDecision> listApproved;
    private List<Business> listCurrentSearch;
    private static ApplicationData applicationData = null;
    private boolean instructionsViewed;
    /** Constructor */
    private ApplicationData()
    {
        listHistory = new ArrayList<RestaurantWithDecision>();
        listApproved = new ArrayList<RestaurantWithDecision>();
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

        // Create a new business with decision object for the prospective business.
        RestaurantWithDecision newRestaurantToAdd = new RestaurantWithDecision(
                businessToAdd,
                decision
        );

        // Add it to history

        // Put the given business at index 0 (latest business)
        listHistory.add(0, newRestaurantToAdd);

        // Ensure the list is at 10 items.
        while (listHistory.size() > 10) {
            listHistory.remove(10);
        }

        // Add it to approved list, if it was approved.
        if (decision) {

            // If it's already in the list, don't add it.
            boolean isFoundInApproved = false;
            if (!(listApproved == null) && !(listApproved.size() == 0)) {
                for (RestaurantWithDecision currentRestaurant : listApproved) {
                    if (currentRestaurant.getRestaurant().name().equals(businessToAdd.name())) {
                        isFoundInApproved = true;
                    }
                }
            }

            if (!isFoundInApproved) {
                if(listApproved==null)
                    listApproved = new ArrayList<RestaurantWithDecision>();
                listApproved.add(0, newRestaurantToAdd);
            }

        }

    }

    /**
     * Sets the data for the application
     * @param givenAD The given data
     */
    public static void setApplicationData (ApplicationData givenAD) {
        applicationData = givenAD;
    }

    /**
     * Gets the History list
     * @return A List of RestaurantWithDecision objects that represent restaurants in history
     */
    public List<RestaurantWithDecision> getListHistory() {
        return listHistory;
    }

    /**
     * Gets the approved restaurants list.
     * @return A List of RestaurantWithDecision objects
     */
    public List<RestaurantWithDecision> getListApproved() {
        return listApproved;
    }

    /**
     * Sets the History list
     * @param listHistory List of Restaurants that weill be used as new history list
     */
    public void setListHistory(List<RestaurantWithDecision> listHistory) {
        this.listHistory = listHistory;
    }

    /**
     * Resets restaurant history to empty.
     * @return True if at least one element was cleared. False if else.
     */
    public boolean resetListHistory () {

        boolean isEmpty = (this.listHistory.size() == 0);
        this.listHistory = new ArrayList<RestaurantWithDecision>();

        // If the list was empty, then return false.
        return !(isEmpty);


    }

    /**
     * Gets the current list of restaurants that was returned from search
     * @return list of bisinesses
     */
    public List<Business> getListCurrentSearch() {
        return listCurrentSearch;
    }

    /**
     * sets the list if restaurants returned from search
     * @param listCurrentSearch List of businesses
     */
    public void setListCurrentSearch(List<Business> listCurrentSearch) {
        this.listCurrentSearch = listCurrentSearch;
    }

    public void setViewedInstructionsToTrue(){
        instructionsViewed = true;
    }

    public boolean getViewedInstructions() {
        return instructionsViewed;
    }
}
