package ca.mcgill.ecse428.restoguys.connoisseur.persistance;

import com.yelp.clientlib.entities.Business;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a singleton class that stores the user history and search preferences.
 */
public class ApplicationData {

    /** Instance Variables */
    private List<Business> listHistory;
    private List<Business> listCurrentSearch;
    private static ApplicationData applicationData = null;

    /** Constructor */

    private ApplicationData()
    {
        listHistory = new ArrayList<Business>();
        listCurrentSearch = new ArrayList<Business>();
    }

    public static ApplicationData getInstance(){
        if(applicationData == null){
            applicationData = new ApplicationData();
        }
        return applicationData;
    }

    public void setApplicationData (ApplicationData givenAD) {
        applicationData = givenAD;
    }

    public List<Business> getListHistory() {
        return listHistory;
    }

    public void setListHistory(List<Business> listHistory) {
        this.listHistory = listHistory;
    }

    public List<Business> getListCurrentSearch() {
        return listCurrentSearch;
    }

    public void setListCurrentSearch(List<Business> listCurrentSearch) {
        this.listCurrentSearch = listCurrentSearch;
    }
}
