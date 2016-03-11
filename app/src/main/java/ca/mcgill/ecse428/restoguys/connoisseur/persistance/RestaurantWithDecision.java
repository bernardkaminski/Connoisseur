package ca.mcgill.ecse428.restoguys.connoisseur.persistance;

import com.yelp.clientlib.entities.Business;

/**
 * Contains a Yelp 'business' and whether or not it was approved by user.
 */
public class RestaurantWithDecision {

    private Business restaurant;
    private boolean  userDecision;

    public RestaurantWithDecision (Business restaurant, boolean decision) {
        this.restaurant = restaurant;
        this.userDecision = decision;
    }

    public void setRestaurant(Business restaurant) {
        this.restaurant = restaurant;
    }

    public Business getRestaurant() {
        return restaurant;
    }

    public void setUserDecision(boolean userDecision) {
        this.userDecision = userDecision;
    }

    public boolean getUserDecision() {
        return userDecision;
    }


}
