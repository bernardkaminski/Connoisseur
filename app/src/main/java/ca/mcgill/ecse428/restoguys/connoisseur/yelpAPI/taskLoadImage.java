package ca.mcgill.ecse428.restoguys.connoisseur.yelpAPI;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * ASyncTask used to load an image (from a URL) to a given ImageButton.
 */
public class taskLoadImage extends AsyncTask<Void,Void,Drawable> {

    private ImageButton imagebutton;
    private String imageURL;

    public taskLoadImage (ImageButton imageButton, String imageURL) {
        this.imagebutton = imageButton;
        this.imageURL = imageURL;
    }

    @Override
    protected Drawable doInBackground(Void...voids)
    {

        try {

            InputStream inputStreamImageAtURL = (InputStream) new URL(imageURL).getContent();
            Drawable imageAtURL = Drawable.createFromStream(inputStreamImageAtURL, null);
            return imageAtURL;

        } catch (IOException e) {

            e.printStackTrace();
            Log.d("debug", "Ran into exception");
            return null;

        }

    }

    protected void onPostExecute(Drawable restaurantImage) {

        imagebutton.setBackground(restaurantImage);

    }

}