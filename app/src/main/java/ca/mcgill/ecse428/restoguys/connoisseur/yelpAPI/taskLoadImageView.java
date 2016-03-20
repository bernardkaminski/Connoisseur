package ca.mcgill.ecse428.restoguys.connoisseur.yelpAPI;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * ASyncTask used to load an image (from a URL) to a given ImageButton.
 */
public class taskLoadImageView extends AsyncTask<Void,Void,Drawable> {

    private ImageView imagebutton;
    private String imageURL;

    public taskLoadImageView (ImageView imageButton, String imageURL) {
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

        try {
            imagebutton.setImageBitmap(((BitmapDrawable)restaurantImage).getBitmap());
        }
        catch (NullPointerException e) {
            Log.d("debug", "No image.");
        }

    }

}