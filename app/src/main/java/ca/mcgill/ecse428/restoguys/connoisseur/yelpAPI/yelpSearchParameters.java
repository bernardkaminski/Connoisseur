package ca.mcgill.ecse428.restoguys.connoisseur.yelpAPI;

/**
 * Contains static methods for generating yelp API search parameters from application variables.
 */
public class yelpSearchParameters {

    public static String generateCategoryFilter (String restaurantType) {

        String returnString;

        switch (restaurantType) {

            case "American":
                returnString = "newamerican,tradamerican";
                break;

            case "African": // good
                returnString = "moroccan";
                break;

            case "Italian": // good
                returnString = "italian,pizza";
                break;

            case "French": // good
                returnString = "creperies,french";
                break;

            case "Middle-Eastern":
                returnString = "halal,mideastern";
                break;

            case "Chinese":
                returnString = "chinese";
                break;

            case "Japanese":
                returnString = "japanese";
                break;

            case "South American":
                returnString = "latin,mexican";
                break;

            case "Vegetarian": // good
                returnString = "vegetarian";
                break;

            case "Vegan": // good
                returnString = "vegan";
                break;

            default:
                returnString = "restaurants";
                break;

        }

        return returnString;

    }

}
